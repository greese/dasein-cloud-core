/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud;

import org.dasein.cloud.admin.AdminServices;
import org.dasein.cloud.ci.CIServices;
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.dc.DataCenterServices;
import org.dasein.cloud.identity.IdentityServices;
import org.dasein.cloud.network.NetworkServices;
import org.dasein.cloud.platform.PlatformServices;
import org.dasein.cloud.storage.StorageServices;
import org.dasein.cloud.util.NamingConstraints;
import org.dasein.cloud.util.ResourceNamespace;
import org.dasein.util.CalendarWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * <p>
 * Represents a provider of cloud services. The cloud provider API prescribes a
 * number of services which may or may not be implemented for a given cloud. In addition,
 * the cloud provider API describes the data center structure of the underlying cloud through
 * the concept of regions. Each provider must have at least one region, which in turn has
 * at least one zone or data center.
 * </p>
 * <p>
 * This API specifies a number of services that a given cloud provider may implement, but many
 * cloud providers will not actually implement all of them. If a given service is not implemented,
 * the API call for gaining access to that services should return <code>null</code>. An application
 * should therefore test whether a service is <code>null</code> before trying to trigger operations
 * in that cloud.
 * </p>
 * <p>
 * When implementing a given service for a particular provider, that provider may not support
 * some of the operations of the service. Such methods should throw an
 * {@link org.dasein.cloud.OperationNotSupportedException} to flag the lack of support.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 * @version 2014.03 added findUniqueName() based on logic by Stas (issue #134)
 * @since 2010.08
 */
public abstract class CloudProvider {

    @SuppressWarnings("UnusedDeclaration")
    static private @Nonnull String getLastItem(@Nonnull String name) {
        int idx = name.lastIndexOf('.');

        if (idx < 0) {
            return name;
        }
        else if (idx == (name.length() - 1)) {
            return "";
        }
        return name.substring(idx + 1);
    }

    static public boolean matchesTags(@Nonnull Map<String, ?> currentValues, @Nonnull String name, @Nonnull String description, @Nullable Map<String, String> valuesToMatch) {
        if (valuesToMatch != null && !valuesToMatch.isEmpty()) {
            name = name.toLowerCase();
            description = description.toLowerCase();
            for (Map.Entry<String, String> entry : valuesToMatch.entrySet()) {
                String v = (entry.getValue() == null ? null : entry.getValue().toLowerCase());
                Object t = currentValues.get(entry.getKey());

                if (entry.getKey().equals("Name")) {

                    if (v != null) {
                        String n = name.toLowerCase();

                        if (n.contains(v) || (t != null && t.toString().toLowerCase().contains(v))) {
                            continue;
                        }
                    }
                    return false;
                }
                if (entry.getKey().equals("Description")) {
                    if (v != null) {
                        String d = description.toLowerCase();

                        if (d.contains(v) || (t != null && t.toString().toLowerCase().contains(v))) {
                            continue;
                        }
                    }
                    return false;
                }
                if (t == null && v == null) {
                    continue;
                }
                if (t == null) {
                    return false;
                }
                if (v == null) {
                    return false;
                }
                if (!t.toString().contains(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private CloudProvider computeCloudProvider;
    private ProviderContext context;
    private CloudProvider storageCloudProvider;
    private boolean debug;

    private transient int holdCount = 0;

    /**
     * Base contructor for a cloud provider.
     */
    public CloudProvider() { }

    /**
     * Empties out all credentials and removes any other context information from the cloud provider
     * implementation.
     */
    public void close() {
        int h;

        synchronized (this) {
            h = holdCount;
        }
        if (h > 0) {
            Thread t = new Thread() {
                public void run() {
                    waitForHold();
                }
            };

            t.setName("Close Hold for " + this);
            t.setDaemon(true);
            t.start();
        }
        else {
            waitForHold();
        }
    }

    /**
     * Called to initialize a cloud provider with an operational context. The operational context
     * includes authentication information, the regional context, and any cloud-specific
     * context. Prior to initializing itself, this method will close out any existing state.
     * @param context the context for services calls using this provider instance
     * @deprecated use {@link org.dasein.cloud.ProviderContext#connect()}
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public final void connect(@Nonnull ProviderContext context) {
        connect(context, null);
    }

    /**
     * Called to initialize a cloud provider with an operational context. The operational context
     * includes authentication information, the regional context, and any cloud-specific
     * context. Prior to initializing itself, this method will close out any existing state.
     * @param context         the context for services calls using this provider instance
     * @param computeProvider the compute context if this is a storage-only cloud (the compute context controls the connection)
     * @deprecated use {@link org.dasein.cloud.ProviderContext#connect(CloudProvider)}
     */
    @Deprecated
    public final void connect(@Nonnull ProviderContext context, @Nullable CloudProvider computeProvider) {
        try {
            connect(context, computeProvider, null);
            context.configureForDeprecatedConnect(this);
        }
        catch (CloudException e) {
            throw new RuntimeException(e); // can't change the signature for backwards compat reasons
        }
        catch (InternalException e) {
            throw new RuntimeException(e); // can't change the signature for backwards compat reasons
        }
    }

    /**
     * Establishes a connected state between the Dasein Cloud implementation and the target cloud. This method should
     * not be directly triggered by Dasein Cloud clients, but instead within the Dasein Cloud {@link ProviderContext#connect(CloudProvider)}
     * method or the backwards compatible {@link #connect(ProviderContext, CloudProvider)} method. Clients should always
     * call {@link org.dasein.cloud.ProviderContext#connect()} or {@link ProviderContext#connect(CloudProvider)}.
     * @param context         the provider context representing the context for the connection
     * @param computeProvider if this connection is to a storage cloud being virtually bound to a compute cloud, the connected provider for the associated compute cloud
     * @param myCloud         if known, the cloud object behind this provider
     * @throws CloudException    an error occurred communicating with the target cloud
     * @throws InternalException an error occurred within Dasein Cloud while setting up the connection state
     */
    @SuppressWarnings("deprecation")
    protected void connect(@Nonnull ProviderContext context, @Nullable CloudProvider computeProvider, @Nullable Cloud myCloud) throws CloudException, InternalException {
        close();
        this.context = context;

        if (myCloud == null) {
            context.setCloud(this);
        }
        this.computeCloudProvider = computeProvider;
        if (computeProvider != null) {
            computeProvider.storageCloudProvider = this;
            ProviderContext computeContext = computeProvider.getContext();

            if (computeContext != null) {
                context.setEffectiveAccountNumber(computeContext.getAccountNumber());
            }
        }
    }

    /**
     * General purpose method for finding a unique name based upon a desired base name. The name resulting from this
     * method is guaranteed to be both valid for objects of its type and unique among objects of those type across
     * the appropriate namespace. Unless, of course, the result is <code>null</code>. A <code>null</code> value
     * means that no permutation of the base name could result in a valid unique name for these kinds of objects
     * in this cloud.
     * @param baseName    the name that the user would ideally desire for an object to be created
     * @param constraints the naming constraints that govern the naming of this kind of object
     * @param namespace   an implementation of an interface responsible for searching efficiently for the availability of a given name
     * @return a valid, unique name based on the desired base name or <code>null</code> if no unique permutation was achievable
     * @throws CloudException    an error occurred interacting with the cloud provider to find the unique name
     * @throws InternalException an internal error occurred calculating a unique name
     */
    public @Nullable String findUniqueName(@Nonnull String baseName, @Nonnull NamingConstraints constraints, @Nonnull ResourceNamespace namespace) throws CloudException, InternalException {
        if (!constraints.isValidName(baseName)) {
            baseName = constraints.convertToValidName(baseName, Locale.getDefault());
            if (baseName == null) {
                return null;
            }
        }
        if (!namespace.hasNamedItem(baseName)) {
            return baseName;
        }
        String name = baseName;
        int i = 1;

        while (namespace.hasNamedItem(name)) {
            name = constraints.incrementName(baseName, i++);
            if (name == null) {
                return null;
            }
        }
        return name;
    }

    public abstract @Nullable AdminServices getAdminServices();

    /**
     * If this is a pure storage implementation that is being paired with a compute implementation,
     * the compute implementation holds precedence. This value references the compute provider
     * behind this storage provider
     * @return the compute provider (if any) behind this storage provider
     */
    public final CloudProvider getComputeCloud() {
        return computeCloudProvider;
    }

    /**
     * This value will be null unless {@link #connect(ProviderContext)} has been called.
     * @return the operational context for this instance of this provider implementation
     */
    public final @Nullable ProviderContext getContext() {
        return context;
    }

    /**
     * @return an object containing the fields required for connecting Dasein to the cloud provider
     */
    public abstract @Nonnull ContextRequirements getContextRequirements();

    /**
     * This value can be the same as {@link #getProviderName()} if it is not a multi-cloud provider.
     * @return the name of the cloud
     */
    public abstract @Nonnull String getCloudName();

    /**
     * Provides access to the data center services that describe the physical structure of the underlying cloud provider.
     * @return an implementation of the {@link org.dasein.cloud.dc.DataCenterServices} API
     */
    public abstract @Nonnull DataCenterServices getDataCenterServices();

    /**
     * Provides access to support for complex topologies managed through converged infrastructure as a cloudy environment.
     * @return the services representing converged infrastructure, if any
     */
    public abstract @Nullable CIServices getCIServices();

    public abstract @Nullable ComputeServices getComputeServices();

    public abstract @Nullable IdentityServices getIdentityServices();

    public abstract @Nullable NetworkServices getNetworkServices();

    public abstract @Nullable PlatformServices getPlatformServices();

    /**
     * @return the name of this cloud provider
     */
    public abstract @Nonnull String getProviderName();

    /**
     * Provides access to the cloud storage services supported by this cloud provider.
     * @return an implementation of the {@link org.dasein.cloud.storage.StorageServices} API
     */
    @SuppressWarnings("deprecation")
    public synchronized @Nullable StorageServices getStorageServices() {
        if (storageCloudProvider != null) {
            return storageCloudProvider.getStorageServices();
        }
        ProviderContext computeContext = getContext();

        if (computeContext == null) {
            return null;
        }
        String storage = computeContext.getStorage();

        if (storage == null) {
            return null;
        }
        try {
            CloudProvider p = (CloudProvider) Class.forName(storage).newInstance();
            ProviderContext ctx = new ProviderContext();
            Properties props = computeContext.getStorageCustomProperties();

            ctx.setRegionId(computeContext.getRegionId());
            ctx.setCloudName(computeContext.getCloudName());
            ctx.setProviderName(computeContext.getProviderName());
            ctx.setEndpoint(computeContext.getStorageEndpoint());
            ctx.setAccountNumber(computeContext.getStorageAccountNumber());
            ctx.setAccessKeys(computeContext.getStoragePublic(), computeContext.getStoragePrivate());
            ctx.setX509Cert(computeContext.getStorageX509Cert());
            ctx.setX509Key(computeContext.getStorageX509Key());
            ctx.setCustomProperties(props == null ? new Properties() : props);
            p.connect(ctx, this);
            storageCloudProvider = p;
            return p.getStorageServices();
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public boolean hasAdminServices() {
        return (getAdminServices() != null);
    }

    public boolean hasCIServices() {
        return (getCIServices() != null);
    }

    public boolean hasComputeServices() {
        return (getComputeServices() != null);
    }

    public boolean hasIdentityServices() {
        return (getIdentityServices() != null);
    }

    public boolean hasNetworkServices() {
        return (getNetworkServices() != null);
    }

    public boolean hasPlatformServices() {
        return (getPlatformServices() != null);
    }

    public boolean hasStorageServices() {
        return (getStorageServices() != null);
    }

    public void hold() {
        if (computeCloudProvider != null) {
            computeCloudProvider.hold();
        }
        else {
            synchronized (this) {
                holdCount++;
            }
        }
    }

    public synchronized boolean isConnected() {
        return (context != null);
    }

    public void release() {
        if (computeCloudProvider != null) {
            computeCloudProvider.release();
        }
        else {
            synchronized (this) {
                holdCount--;
                notifyAll();
            }
        }
    }

    /**
     * Tests the validity of the current context and returns the true account identifier for this context.
     * In general, this value will make some kind of connection to the cloud provider using the established
     * context to verify the credentials are right and then return {@link ProviderContext#getAccountNumber()}.
     * In some cases, however, the actual provider account number may differ from the one visible to the
     * user. For those scenarios, the return value will return the true account number and not the one the
     * user thinks it is. If the connection fails for any reason, this method should return <code>null</code>
     * to indicate the failure.
     * @return On success, the true account number for the account. On failure, <code>null</code>.
     */
    public String testContext() {
        return null;
    }

    private void waitForHold() {
        long timeout = System.currentTimeMillis() + (CalendarWrapper.MINUTE * 20L);

        while (timeout > System.currentTimeMillis()) {
            synchronized (this) {
                if (holdCount < 1) {
                    break;
                }
            }
            try { Thread.sleep(1000L); }
            catch (InterruptedException ignore) { /* ignore this */ }
        }
        if (context != null) {
            context.clear();
            context = null;
        }
        if (storageCloudProvider != null) {
            storageCloudProvider.close();
            storageCloudProvider = null;
        }
    }
}
