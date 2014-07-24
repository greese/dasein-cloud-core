package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Drew Lyall: 11/07/14 12:08
 * @author Drew Lyall
 */
public class AffinityGroupCreateOptions{
    private String name;
    private String description;
    private String dataCenterId;

    public static @Nonnull AffinityGroupCreateOptions getInstance(@Nonnull String name, @Nullable String description, @Nonnull String dataCenterId){
        return new AffinityGroupCreateOptions(name, description, dataCenterId);
    }

    private AffinityGroupCreateOptions(@Nonnull String name, @Nullable String description, @Nonnull String dataCenterId){
        this.name = name;
        this.description = description;
        this.dataCenterId = dataCenterId;
    }

    public @Nonnull String getName(){
        return this.name;
    }

    public @Nonnull String getDescription(){
        return this.description;
    }

    public @Nonnull String getDataCenterId(){
        return this.dataCenterId;
    }

    public @Nonnull String build(@Nonnull CloudProvider provider) throws CloudException, InternalException{
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support compute services.");
        }
        AffinityGroupSupport support = services.getAffinityGroupSupport();
        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not have affinity group support");
        }
        return support.create(this).getAffinityGroupId();
    }
}
