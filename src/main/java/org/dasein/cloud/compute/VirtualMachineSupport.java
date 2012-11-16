/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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

package org.dasein.cloud.compute;

import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.ServiceAction;

/**
 * <p>
 * Core interface for the server services. Implements all operations critical to managing virtual
 * machines in a cloud environment.
 * </p>
 * @author George Reese @ enStratus (http://www.enstratus.com)
 * @version 2012-07 Added new launch method with {@link VMLaunchOptions} as well as better meta-data
 * @version 2013.01 Added meta-data for defining kernel and ramdisk image requirements (Issue #7)
 * @version 2013.01 Added status listing (Issue #4)
 * @since unknown
 */
@SuppressWarnings("UnusedDeclaration")
public interface VirtualMachineSupport extends AccessControlledService {
    static public final ServiceAction ANY               = new ServiceAction("VM:ANY");

    static public final ServiceAction BOOT              = new ServiceAction("VM:BOOT");
    static public final ServiceAction CLONE             = new ServiceAction("VM:CLONE");
    static public final ServiceAction CREATE_VM         = new ServiceAction("VM:CREATE_VM");
    static public final ServiceAction GET_VM            = new ServiceAction("VM:GET_VM");
    static public final ServiceAction LIST_VM           = new ServiceAction("VM:LIST_VM");
    static public final ServiceAction PAUSE             = new ServiceAction("VM:PAUSE");
    static public final ServiceAction REBOOT            = new ServiceAction("VM:REBOOT");
    static public final ServiceAction REMOVE_VM         = new ServiceAction("VM:REMOVE_VM");
    static public final ServiceAction TOGGLE_ANALYTICS  = new ServiceAction("VM:TOGGLE_ANALYTICS");
    static public final ServiceAction VIEW_ANALYTICS    = new ServiceAction("VM:VIEW_ANALYTICS");
    static public final ServiceAction VIEW_CONSOLE      = new ServiceAction("VM:VIEW_CONSOLE");
    
    /**
     * Clones an existing virtual machine into a new copy.
     * @param vmId the ID of the server to be cloned
     * @param intoDcId the ID of the data center in which the new server will operate
     * @param name the name of the new server
     * @param description a description for the new server
     * @param powerOn power on the new server
     * @param firewallIds a list of firewall IDs to protect the new server
     * @return a newly deployed server cloned from the original
     * @throws InternalException an internal error occurred processing the request
     * @throws CloudException an error occurred in the cloud processing the request
     */
    public abstract @Nonnull VirtualMachine clone(@Nonnull String vmId, @Nonnull String intoDcId, @Nonnull String name, @Nonnull String description, boolean powerOn, @Nullable String ... firewallIds) throws InternalException, CloudException;               
    
    /**
     * Turns extended analytics off for the target server. If the underlying cloud does not support
     * hypervisor monitoring, this method will be a NO-OP.
     * @param vmId the provider ID for the server to stop monitoring
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public void disableAnalytics(String vmId) throws InternalException, CloudException; 
    
    /**
     * Turns extended hypervisor analytics for the target server. If the underlying cloud does not support
     * extended analytics, this method will be a NO-OP.
     * @param vmId the provider ID for the server to start monitoring
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public void enableAnalytics(String vmId) throws InternalException, CloudException;
    
    /**
     * Provides all output from the console of the target server since the specified Unix time.
     * @param vmId the unique ID of the target server
     * @return the current output from the server console
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract @Nonnull String getConsoleOutput(@Nonnull String vmId) throws InternalException, CloudException;

    /**
     * Provides the maximum number of virtual machines that may be launched in this region for the current account.
     * @return the maximum number of launchable VMs or -1 for unlimited or -2 for unknown
     * @throws CloudException an error occurred fetching the limits from the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limits
     */
    public abstract int getMaximumVirtualMachineCount() throws CloudException, InternalException;

    /**
     * Fetches the VM product associated with a specific product ID.
     * @param productId the virtual machine product ID (flavor, size, etc.)
     * @return the product represented by the specified product ID
     * @throws InternalException an error occurred within the Dasein Cloud implementation fetching the product
     * @throws CloudException an error occurred fetching the product from the cloud
     */
    public abstract @Nullable VirtualMachineProduct getProduct(@Nonnull String productId) throws InternalException, CloudException;
    
    /**
     * Assists UIs by providing the cloud-specific term for a virtual server in the cloud.
     * @param locale the locale for which the term should be translated
     * @return the provider-specific term for a virtual server
     */
    public abstract @Nonnull String getProviderTermForServer(@Nonnull Locale locale);
    
    /**
     * Provides the data from a specific virtual machine.
     * @param vmId the provider ID for the desired server
     * @return the data behind the target server
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract @Nullable VirtualMachine getVirtualMachine(@Nonnull String vmId) throws InternalException, CloudException;

    /**
     * Provides hypervisor statistics for the specified server that fit within the defined time range.
     * For clouds that do not provide hypervisor statistics, this method should return an empty
     * {@link VmStatistics} object and NOT <code>null</code>.
     * @param vmId the unique ID for the target server 
     * @param from the beginning of the timeframe for which you want statistics
     * @param to the end of the timeframe for which you want statistics
     * @return the statistics for the target server
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public VmStatistics getVMStatistics(String vmId, long from, long to) throws InternalException, CloudException;
    
    /**
     * Provides hypervisor statistics for the specified server that fit within the defined time range.
     * For clouds that do not provide hypervisor statistics, this method should return an empty
     * list.
     * @param vmId the unique ID for the target server 
     * @param from the beginning of the timeframe for which you want statistics
     * @param to the end of the timeframe for which you want statistics
     * @return a collection of discreet server statistics over the specified period
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract @Nonnull Iterable<VmStatistics> getVMStatisticsForPeriod(@Nonnull String vmId, @Nonnegative long from, @Nonnegative long to) throws InternalException, CloudException;

    /**
     * Identifies whether images of the specified image class are required for launching a VM. This method should
     * always return {@link Requirement#REQUIRED} when the image class chosen is {@link ImageClass#MACHINE}.
     * @param cls the desired image class
     * @return the requirements level of support for this image class
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyImageRequirement(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Indicates the degree to which specifying a user name and password at launch is required.
     * @return the requirements level for specifying a user name and password at launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyPasswordRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether or not a root volume product must be specified when launching a virtual machine.
     * @return the requirements level for a root volume product
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyRootVolumeRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which specifying a shell key at launch is required.
     * @return the requirements level for shell key support at launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyShellKeyRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which static IP addresses are required when launching a VM.
     * @return the requirements level for static IP on launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyStaticIPRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether or not specifying a VLAN in your VM launch options is required or optional.
     * @return the requirements level for a VLAN during launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public abstract @Nonnull Requirement identifyVlanRequirement() throws CloudException, InternalException;

    /**
     * Indicates that the ability to terminate the VM via API can be disabled.
     * @return true if the cloud supports the ability to prevent API termination
     * @throws CloudException an error occurred in the cloud while determining this capability
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining this capability
     */
    public abstract boolean isAPITerminationPreventable() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud provider supports basic analytics. Basic analytics are analytics
     * that are being gathered for every virtual machine without any intervention necessary to enable them. Extended
     * analytics implies basic analytics, so this method should always be true if {@link #isExtendedAnalyticsSupported()} 
     * is true (even if there are, in fact, only extended analytics).
     * @return true if the cloud provider supports the gathering of extended analytics
     * @throws CloudException an error occurred in the cloud provider determining extended analytics support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining extended analytics support
     */
    public abstract boolean isBasicAnalyticsSupported() throws CloudException, InternalException;
    
    /**
     * Indicates whether or not this cloud provider supports extended analytics. Extended analytics are analytics
     * that must be specifically enabled above and beyond any basic analytics the cloud provider is gathering.
     * @return true if the cloud provider supports the gathering of extended analytics
     * @throws CloudException an error occurred in the cloud provider determining extended analytics support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining extended analytics support
     */
    public abstract boolean isExtendedAnalyticsSupported() throws CloudException, InternalException;
    
    /**
     * Indicates whether this account is subscribed to using virtual machines.
     * @return true if the subscription is valid for using virtual machines
     * @throws CloudException an error occurred querying the cloud for subscription info
     * @throws InternalException an error occurred within the implementation determining subscription state
     */
    public abstract boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Indicates whether or not the cloud allows bootstrapping with user data.
     * @return true of user-data bootstrapping is supported
     * @throws CloudException an error occurred querying the cloud for this kind of support
     * @throws InternalException an error inside the Dasein Cloud implementation occurred determining support
     */
    public abstract boolean isUserDataSupported() throws CloudException, InternalException;

    /**
     * Preferred mechanism for launching a virtual machine in the cloud. This method accepts a rich set of launch
     * configuration options that define what the virtual machine should look like once launched. These options may
     * include things that behave very differently in some clouds. It is expected that the method will return 
     * immediately once Dasein Cloud as a trackable server ID, even if it has to spawn off a background thread
     * to complete follow on tasks (such as provisioning and attaching volumes).
     * @param withLaunchOptions the launch options to use in creating a new virtual machine
     * @return the newly created virtual machine
     * @throws CloudException the cloud provider errored out when launching the virtual machine
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public @Nonnull VirtualMachine launch(@Nonnull VMLaunchOptions withLaunchOptions) throws CloudException, InternalException;
    
    /**
     * Launches a virtual machine in the cloud. If the cloud supports persistent servers, this method will
     * first define a server and then boot it. The end result of this operation should be a server
     * that is in the middle of booting up.
     * @param fromMachineImageId the provider ID of the image from which the server should be built
     * @param product the product being provisioned against
     * @param dataCenterId the provider ID for the data center into which the server will be launched
     * @param name the name of the new server
     * @param description a user-friendly description of the new virtual machine
     * @param withKeypairId the name of the keypair to use for root authentication or null if no keypair
     * @param inVlanId the ID of the VLAN into which the server should be launched, or null if not specifying (or not supported by the cloud)
     * @param withAnalytics whether or not hypervisor analytics should be enabled for the virtual machine
     * @param asSandbox for clouds that require sandboxes for image building, this launches the VM in a sandbox context
     * @param firewallIds the firewalls to protect the new server
     * @return the newly launched server
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     * @deprecated use {@link #launch(VMLaunchOptions)}
     */
    public @Nonnull VirtualMachine launch(@Nonnull String fromMachineImageId, @Nonnull VirtualMachineProduct product, @Nonnull String dataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String withKeypairId, @Nullable String inVlanId, boolean withAnalytics, boolean asSandbox, @Nullable String ... firewallIds) throws InternalException, CloudException;

    /**
     * Launches a virtual machine in the cloud. If the cloud supports persistent servers, this method will
     * first define a server and then boot it. The end result of this operation should be a server
     * that is in the middle of booting up.
     * @param fromMachineImageId the provider ID of the image from which the server should be built
     * @param product the product being provisioned against
     * @param dataCenterId the provider ID for the data center into which the server will be launched
     * @param name the name of the new server
     * @param description a user-friendly description of the new virtual machine
     * @param withKeypairId the name of the keypair to use for root authentication or null if no keypair
     * @param inVlanId the ID of the VLAN into which the server should be launched, or null if not specifying (or not supported by the cloud)
     * @param withAnalytics whether or not hypervisor analytics should be enabled for the virtual machine
     * @param asSandbox for clouds that require sandboxes for image building, this launches the VM in a sandbox context
     * @param firewallIds the firewalls to protect the new server
     * @param tags a list of meta data to pass to the cloud provider
     * @return the newly launched server
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     * @deprecated use {@link #launch(VMLaunchOptions)}
     */
    public @Nonnull VirtualMachine launch(@Nonnull String fromMachineImageId, @Nonnull VirtualMachineProduct product, @Nonnull String dataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String withKeypairId, @Nullable String inVlanId, boolean withAnalytics, boolean asSandbox, @Nullable String[] firewallIds, @Nullable Tag ... tags) throws InternalException, CloudException;
    
    /**
     * Provides a list of firewalls protecting the specified server. If firewalls are not supported
     * in this cloud, the list will be empty.
     * @param vmId the server ID whose firewall list is being sought
     * @return the list of firewalls protecting the target server
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull Iterable<String> listFirewalls(@Nonnull String vmId) throws InternalException, CloudException;
    
    /**
     * Provides a list of instance types, service offerings, or server sizes (however the underlying cloud
     * might describe it) for a particular architecture
     * @param architecture the desired architecture size offerings
     * @return the list of server sizes available for the specified architecture
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public Iterable<VirtualMachineProduct> listProducts(Architecture architecture) throws InternalException, CloudException;

    /**
     * Identifies what architectures are supported in this cloud.
     * @return a list of supported architectures
     * @throws InternalException an error occurred within the Dasein Cloud implementation calculating supported architectures
     * @throws CloudException an error occurred fetching the list of supported architectures from the cloud
     */
    public Iterable<Architecture> listSupportedArchitectures() throws InternalException, CloudException;

    /**
     * Lists the status for all virtual machines in the current region.
     * @return the status for all virtual machines in the current region
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public @Nonnull Iterable<ResourceStatus> listVirtualMachineStatus() throws InternalException, CloudException;

    /**
     * Lists all virtual machines belonging to the account owner currently in the cloud.
     * @return all servers belonging to the account owner
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public @Nonnull Iterable<VirtualMachine> listVirtualMachines() throws InternalException, CloudException;

    /**
     * Executes a hypervisor pause that essentially removes the virtual machine from the hypervisor scheduler.
     * The virtual machine is considered active and volatile at this point, but it won't actually do anything
     * from  CPU-perspective until it is {@link #unpause(String)}'ed.
     * @param vmId the provider ID for the server to pause
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     * @throws OperationNotSupportedException pausing is not supported for the specified virtual machine
     * @see #unpause(String)
     */
    public abstract void pause(@Nonnull String vmId) throws InternalException, CloudException;
    
    /**
     * Executes a virtual machine reboot for the target virtual machine.
     * @param vmId the provider ID for the server to reboot
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract void reboot(@Nonnull String vmId) throws CloudException, InternalException;

    /**
     * Resumes a previously suspended virtual machine and returns it to an operational state ({@link VmState#RUNNING}).
     * @param vmId the virtual machine ID to be resumed
     * @throws CloudException an error occurred with the cloud provider in attempting to resume the virtual machine
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws OperationNotSupportedException the target virtual machine cannot be suspended/resumed
     * @see #suspend(String)
     */
    public abstract void resume(@Nonnull String vmId) throws CloudException, InternalException;

    /**
     * Starts up a virtual machine that was previously stopped (or a VM that is created in a {@link VmState#STOPPED} state).
     * @param vmId the virtual machine to boot up
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     * @throws OperationNotSupportedException starting/stopping is not supported for this virtual machine
     * @see #stop(String)
     */
    public abstract void start(@Nonnull String vmId) throws InternalException, CloudException;

    /**
     * Shuts down a virtual machine with the capacity to boot it back up at a later time. The contents of volumes
     * associated with this virtual machine are preserved, but the memory is not.
     * @param vmId the virtual machine to be shut down
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     * @throws OperationNotSupportedException starting/stopping is not supported for this virtual machine
     * @see #start(String)
     */
    public abstract void stop(@Nonnull String vmId) throws InternalException, CloudException;

    /**
     * Identifies whether or not this cloud supports hypervisor-based analytics around usage and performance.
     * @return true if this cloud supports hypervisor-based analytics
     * @throws CloudException an error occurred with the cloud provider determining analytics support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining analytics support
     */
    public abstract boolean supportsAnalytics() throws CloudException, InternalException;

    /**
     * Indicates whether the ability to pause/unpause a virtual machine is supported for the specified VM.
     * @param vm the virtual machine to verify
     * @return true if pause/unpause is supported for this virtual machine
     * @see #pause(String)
     * @see #unpause(String)
     * @see VmState#PAUSING
     * @see VmState#PAUSED
     */
    public abstract boolean supportsPauseUnpause(@Nonnull VirtualMachine vm);

    /**
     * Indicates whether the ability to start/stop a virtual machine is supported for the specified VM.
     * @param vm the virtual machine to verify
     * @return true if start/stop operations are supported for this virtual machine
     * @see #start(String)
     * @see #stop(String)
     * @see VmState#RUNNING
     * @see VmState#STOPPING
     * @see VmState#STOPPED
     */
    public abstract boolean supportsStartStop(@Nonnull VirtualMachine vm);

    /**
     * Indicates whether the ability to suspend/resume a virtual machine is supported for the specified VM.
     * @param vm the virtual machine to verify
     * @return true if suspend/resume operations are supported for this virtual machine
     * @see #suspend(String)
     * @see #resume(String)
     * @see VmState#SUSPENDING
     * @see VmState#SUSPENDED
     */
    public abstract boolean supportsSuspendResume(@Nonnull VirtualMachine vm);

    /**
     * Suspends a running virtual machine so that the memory is flushed to some kind of persistent storage for
     * the purpose of later resuming the virtual machine in the exact same state.
     * @param vmId the unique ID of the virtual machine to be suspended
     * @throws CloudException an error occurred with the cloud provider suspending the virtual machine
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws OperationNotSupportedException suspending is not supported for this virtual machine
     * @see #resume(String)
     */
    public abstract void suspend(@Nonnull String vmId) throws CloudException, InternalException;

    /**
     * TERMINATES AND DESTROYS the specified virtual machine. If it is running, it will be stopped. Once it is
     * stopped, all of its data will be destroyed and it will no longer be usable. This is a very 
     * dangerous operation, especially in clouds with persistent servers.
     * @param vmId the provider ID of the server to be destroyed
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    public abstract void terminate(@Nonnull String vmId) throws InternalException, CloudException;


    /**
     * Executes a hypervisor unpause operation on a currently paused virtual machine, adding it back into the
     * hypervisor scheduler.
     * @param vmId the unique ID of the virtual machine to be unpaused
     * @throws CloudException an error occurred within the cloud provider while unpausing
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws OperationNotSupportedException pausing/unpausing is not supported for the specified virtual machine
     * @see #pause(String)
     */
    public abstract void unpause(@Nonnull String vmId) throws CloudException, InternalException;
}
