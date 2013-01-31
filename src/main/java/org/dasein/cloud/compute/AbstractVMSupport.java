package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.Tag;
import org.dasein.cloud.identity.IdentityServices;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.util.Cache;
import org.dasein.cloud.util.CacheLevel;
import org.dasein.util.CalendarWrapper;
import org.dasein.util.uom.time.TimePeriod;
import org.dasein.util.uom.time.Week;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of virtual machine support for clouds with very little support.
 * <p>Created by George Reese: 1/29/13 6:11 PM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public abstract class AbstractVMSupport implements VirtualMachineSupport {
    private CloudProvider provider;

    public AbstractVMSupport(CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    public VirtualMachine alterVirtualMachine(@Nonnull String vmId, @Nonnull VMScalingOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException("VM alternations are not currently supported for " + getProvider().getCloudName());
    }

    @Override
    public @Nonnull VirtualMachine clone(@Nonnull String vmId, @Nonnull String intoDcId, @Nonnull String name, @Nonnull String description, boolean powerOn, @Nullable String... firewallIds) throws InternalException, CloudException {
        throw new OperationNotSupportedException("VM cloning is not currently supported for " + getProvider().getCloudName());
    }

    @Override
    public @Nullable VMScalingCapabilities describeVerticalScalingCapabilities() throws CloudException, InternalException {
        return null;
    }

    @Override
    public void disableAnalytics(@Nonnull String vmId) throws InternalException, CloudException {
        // NO-OP
    }

    @Override
    public void enableAnalytics(@Nonnull String vmId) throws InternalException, CloudException {
        // NO-OP
    }

    /**
     * Provides the current provider context for the request in progress.
     * @return the current provider context
     * @throws CloudException no context was defined before making this call
     */
    protected final @Nonnull ProviderContext getContext() throws CloudException {
        ProviderContext ctx = getProvider().getContext();

        if( ctx == null ) {
            throw new CloudException("No context was defined for this request");
        }
        return ctx;
    }

    @Override
    public @Nonnull String getConsoleOutput(@Nonnull String vmId) throws InternalException, CloudException {
        return "";
    }

    @Override
    public int getCostFactor(@Nonnull VmState state) throws InternalException, CloudException {
        return 100;
    }

    @Override
    public int getMaximumVirtualMachineCount() throws CloudException, InternalException {
        return -2;
    }

    @Override
    public @Nullable VirtualMachineProduct getProduct(@Nonnull String productId) throws InternalException, CloudException {
        for( Architecture architecture : Architecture.values() ) {
            for( VirtualMachineProduct prd : listProducts(architecture) ) {
                if( productId.equals(prd.getProviderProductId()) ) {
                    return prd;
                }
            }
        }
        return null;
    }

    /**
     * @return the current provider governing any operations against this cloud in this support instance
     */
    protected final @Nonnull CloudProvider getProvider() {
        return provider;
    }

    @Override
    public @Nullable VirtualMachine getVirtualMachine(@Nonnull String vmId) throws InternalException, CloudException {
        for( VirtualMachine vm : listVirtualMachines(null) ) {
            if( vm.getProviderVirtualMachineId().equals(vmId) ) {
                return vm;
            }
        }
        return null;
    }

    @Override
    public @Nonnull VmStatistics getVMStatistics(@Nonnull String vmId, @Nonnegative long from, @Nonnegative long to) throws InternalException, CloudException {
        return new VmStatistics();
    }

    @Override
    public @Nonnull Iterable<VmStatistics> getVMStatisticsForPeriod(@Nonnull String vmId, @Nonnegative long from, @Nonnegative long to) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Requirement identifyImageRequirement(@Nonnull ImageClass cls) throws CloudException, InternalException {
        return (cls.equals(ImageClass.MACHINE) ? Requirement.REQUIRED : Requirement.NONE);
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyPasswordRequirement() throws CloudException, InternalException {
        return identifyPasswordRequirement(Platform.UNKNOWN);
    }

    @Override
    public @Nonnull Requirement identifyPasswordRequirement(Platform platform) throws CloudException, InternalException {
        return Requirement.NONE;
    }

    @Override
    public @Nonnull Requirement identifyRootVolumeRequirement() throws CloudException, InternalException {
        return Requirement.NONE;
    }

    @Override
    @Deprecated
    public @Nonnull Requirement identifyShellKeyRequirement() throws CloudException, InternalException {
        return identifyShellKeyRequirement(Platform.UNKNOWN);
    }

    @Override
    public @Nonnull Requirement identifyShellKeyRequirement(Platform platform) throws CloudException, InternalException {
        IdentityServices services = getProvider().getIdentityServices();

        if( services == null ) {
            return Requirement.NONE;
        }
        if( services.hasShellKeySupport() ) {
            return Requirement.OPTIONAL;
        }
        return Requirement.NONE;
    }

    @Override
    public @Nonnull Requirement identifyStaticIPRequirement() throws CloudException, InternalException {
        return Requirement.NONE;
    }

    @Override
    public @Nonnull Requirement identifyVlanRequirement() throws CloudException, InternalException {
        return Requirement.NONE;
    }

    @Override
    public boolean isAPITerminationPreventable() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isBasicAnalyticsSupported() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isExtendedAnalyticsSupported() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isUserDataSupported() throws CloudException, InternalException {
        return false;
    }

    @Override
    @Deprecated
    public @Nonnull VirtualMachine launch(@Nonnull String fromMachineImageId, @Nonnull VirtualMachineProduct product, @Nonnull String dataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String withKeypairId, @Nullable String inVlanId, boolean withAnalytics, boolean asSandbox, @Nullable String... firewallIds) throws InternalException, CloudException {
        VMLaunchOptions options = VMLaunchOptions.getInstance(product.getProviderProductId(), fromMachineImageId, name, name, description);

        options.inDataCenter(dataCenterId);
        if( withKeypairId != null ) {
            options.withBoostrapKey(withKeypairId);
        }
        if( inVlanId != null ) {
            options.inVlan(null, dataCenterId, inVlanId);
        }
        if( withAnalytics ) {
            options.withExtendedAnalytics();
        }
        if( firewallIds != null ) {
            options.behindFirewalls(firewallIds);
        }
        return launch(options);
    }

    @Override
    @Deprecated
    public @Nonnull VirtualMachine launch(@Nonnull String fromMachineImageId, @Nonnull VirtualMachineProduct product, @Nonnull String dataCenterId, @Nonnull String name, @Nonnull String description, @Nullable String withKeypairId, @Nullable String inVlanId, boolean withAnalytics, boolean asSandbox, @Nullable String[] firewallIds, @Nullable Tag... tags) throws InternalException, CloudException {
        VMLaunchOptions options = VMLaunchOptions.getInstance(product.getProviderProductId(), fromMachineImageId, name, name, description);

        options.inDataCenter(dataCenterId);
        if( withKeypairId != null ) {
            options.withBoostrapKey(withKeypairId);
        }
        if( inVlanId != null ) {
            options.inVlan(null, dataCenterId, inVlanId);
        }
        if( withAnalytics ) {
            options.withExtendedAnalytics();
        }
        if( firewallIds != null ) {
            options.behindFirewalls(firewallIds);
        }
        if( tags != null ) {
            HashMap<String,Object> metaData = new HashMap<String, Object>();

            for( Tag tag : tags ) {
                metaData.put(tag.getKey(), tag.getValue());
            }
            options.withMetaData(metaData);
        }
        return launch(options);
    }

    @Override
    public @Nonnull Iterable<String> listFirewalls(@Nonnull String vmId) throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public Iterable<Architecture> listSupportedArchitectures() throws InternalException, CloudException {
        Cache<Architecture> cache = Cache.getInstance(getProvider(), "architectures", Architecture.class, CacheLevel.REGION_ACCOUNT, new TimePeriod<Week>(1, TimePeriod.WEEK));
        Iterable<Architecture> architectures = cache.get(getContext());

        if( architectures == null ) {
            ArrayList<Architecture> list = new ArrayList<Architecture>();

            Collections.addAll(list, Architecture.values());
            architectures = list;
            cache.put(getContext(), architectures);
        }
        return architectures;
    }

    @Override
    public @Nonnull Iterable<ResourceStatus> listVirtualMachineStatus() throws InternalException, CloudException {
        ArrayList<ResourceStatus> status = new ArrayList<ResourceStatus>();

        for( VirtualMachine vm : listVirtualMachines() ) {
            status.add(new ResourceStatus(vm.getProviderVirtualMachineId(), vm.getCurrentState()));
        }
        return status;
    }

    @Override
    public @Nonnull Iterable<VirtualMachine> listVirtualMachines() throws InternalException, CloudException {
        return Collections.emptyList();
    }

    @Override
    public @Nonnull Iterable<VirtualMachine> listVirtualMachines(@Nullable VMFilterOptions options) throws InternalException, CloudException {
        if( options == null ) {
            return listVirtualMachines();
        }
        Map<String,String> tags = options.getTags();

        if( tags == null ) {
            return listVirtualMachines();
        }
        ArrayList<VirtualMachine> vms = new ArrayList<VirtualMachine>();

        for( VirtualMachine vm : listVirtualMachines() ) {
            if( getProvider().matchesTags(vm.getTags(), vm.getName(), vm.getDescription(), tags) ) {
                vms.add(vm);
            }
        }
        return vms;
    }

    @Override
    public void pause(@Nonnull String vmId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Pause/unpause is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void reboot(@Nonnull String vmId) throws CloudException, InternalException {
        VirtualMachine vm = getVirtualMachine(vmId);

        if( vm == null ) {
            throw new CloudException("No such virtual machine: " + vmId);
        }
        stop(vmId);
        long timeout = System.currentTimeMillis() + (CalendarWrapper.MINUTE * 5L);

        while( timeout > System.currentTimeMillis() ) {
            try { vm = getVirtualMachine(vmId); }
            catch( Throwable ignore ) { }
            if( vm == null ) {
                return;
            }
            if( vm.getCurrentState().equals(VmState.STOPPED) ) {
                start(vmId);
                return;
            }
        }
    }

    @Override
    public void resume(@Nonnull String vmId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Resume/suspend is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void start(@Nonnull String vmId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Start/stop is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public final void stop(@Nonnull String vmId) throws InternalException, CloudException {
        stop(vmId, false);

        long timeout = System.currentTimeMillis() + (CalendarWrapper.MINUTE * 10L);

        while( timeout > System.currentTimeMillis() ) {
            try { Thread.sleep(15000L); }
            catch( InterruptedException ignore ) { }
            try {
                VirtualMachine vm = getVirtualMachine(vmId);

                if( vm == null || VmState.TERMINATED.equals(vm.getCurrentState()) || VmState.STOPPED.equals(vm.getCurrentState()) ) {
                    return;
                }
            }
            catch( Throwable ignore ) {
                // ignore
            }
        }

        stop(vmId, true);
    }

    @Override
    public void stop(@Nonnull String vmId, boolean force) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Start/stop is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public final boolean supportsAnalytics() throws CloudException, InternalException {
        return (isBasicAnalyticsSupported() || isExtendedAnalyticsSupported());
    }

    @Override
    public boolean supportsPauseUnpause(@Nonnull VirtualMachine vm) {
        return false;
    }

    @Override
    public boolean supportsStartStop(@Nonnull VirtualMachine vm) {
        return false;
    }

    @Override
    public boolean supportsSuspendResume(@Nonnull VirtualMachine vm) {
        return false;
    }

    @Override
    public void suspend(@Nonnull String vmId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Resume/suspend is not currently implemented for " + getProvider().getCloudName());

    }

    @Override
    public void unpause(@Nonnull String vmId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Pause/unpause is not currently implemented for " + getProvider().getCloudName());
    }

    @Override
    public void updateTags(@Nonnull String vmId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void updateTags(@Nonnull String[] vmIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String vmId, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public void removeTags(@Nonnull String[] vmIds, @Nonnull Tag... tags) throws CloudException, InternalException {
        // NO-OP
    }

    @Override
    public @Nonnull String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }
}
