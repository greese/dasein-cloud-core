/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

package org.dasein.cloud.compute;

import org.dasein.cloud.network.FirewallCreateOptions;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for a variety of adjustments to an existing virtual machine.
 * <p>Created by Drew Lyall: 09/29/14</p>
 * @author Drew Lyall
 * @version 2015.01
 * @version 2015.01
 * @since 2015.01
 */
public class AlterVirtualMachineOptions {
    public enum AlterType{
        ADD, EDIT, DELETE;
    }

    private String                   newProductId;
    private int                      newCPUCount = 0;
    private int                      newRAMInMBCount = 0;
    private AlterVMFirewallOptions[] firewallOptions;
    private AlterVMVolumeOptions[]   volumeOptions;

    private AlterVirtualMachineOptions(){}

    /**
     * Provides an object for describing a variety of changes to an existing VM.
     * This method takes no parameters because each component part can be modified individually without affecting the others
     * @return an AlterVirtualMachineOptions object with empty configurations
     */
    public static AlterVirtualMachineOptions getInstance(){
        return new AlterVirtualMachineOptions();
    }

    /**
     * @return the new productID or null if it is to remain the same or custom values are being used or there will be no product changes
     */
    public @Nullable String getNewProductId(){
        return newProductId;
    }

    /**
     * @return the new CPU Count or 0 if there will be no CPU changes
     */
    public int getNewCPUCount(){
        return newCPUCount;
    }

    /**
     * @return the new RAM size in MB or 0 if there will be no RAM changes
     */
    public int getNewRAMInMBCount(){
        return newRAMInMBCount;
    }

    /**
     * @return an array containing the options defining volumes associated with the VM or null if there will be no volume changes
     */
    public @Nullable AlterVMVolumeOptions[] getVolumeOptions(){
        return volumeOptions;
    }

    /**
     * @return an array containing the options defining firewalls to which the VM belongs or null if there will be no firewall changes
     */
    public @Nullable AlterVMFirewallOptions[] getFirewallOptions(){
        return firewallOptions;
    }

    /**
     * Provides a new product ID. Dasein will attempt to swap the existing product with this one.
     * This should only be used for clouds with distinct "named" products such as AWS (m1.medium etc).
     * @param newProductId the providerProductId for the new product.
     * @return this
     */
    public @Nonnull AlterVirtualMachineOptions withNewProductId(@Nonnull String newProductId){
        this.newProductId = newProductId;
        return this;
    }

    /**
     * Provides a new CPU count. Dasein will attempt to modify the VM to have the new number of CPUs.
     * This should only be used for clouds with non-distinct (contiguous) product values such as terremark
     * @param newCPUCount the new number of CPUs
     * @return this
     */
    public @Nonnull AlterVirtualMachineOptions withNewCPUCount(int newCPUCount){
        this.newCPUCount = newCPUCount;
        return this;
    }

    /**
     * Provides a new RAM size. Dasein will attempt to modify the VM to have the new amount of RAM.
     * This should only be used for clouds with non-distinct (contiguous) product values such as terremark
     * @param newRAMInMBCount the new RAM size in MB
     * @return this
     */
    public @Nonnull AlterVirtualMachineOptions withNewRAMInMBCount(int newRAMInMBCount){
        this.newRAMInMBCount = newRAMInMBCount;
        return this;
    }

    /**
     * Specifies options for managing volumes associated with the VM
     * @param volumeOptions an array containing specifications for handling the volumes associated with the VM
     * @return this
     */
    public @Nonnull AlterVirtualMachineOptions withVolumes(@Nonnull AlterVMVolumeOptions[] volumeOptions){
        this.volumeOptions = volumeOptions;
        return this;
    }

    /**
     * Specifies options for managing firewalls associated with the VM
     * @param firewallOptions an array containing specifications for handling the firewalls associated with the VM
     * @return this
     */
    public @Nonnull AlterVirtualMachineOptions withFirewalls(@Nonnull AlterVMFirewallOptions[] firewallOptions){
        this.firewallOptions = firewallOptions;
        return this;
    }

    public class AlterVMVolumeOptions{
        private String              providerVolumeID;
        private AlterType           alterType;
        private VolumeCreateOptions options;
        private Storage<Gigabyte>   size;

        private AlterVMVolumeOptions(){}

        /**
         * Provides an object for describing changes to volumes associated with an existing VM
         * @param providerVolumeID the ID of the volume being modified or null if creating a new volume
         * @param alterType the type of alteration occurring. Can be any one of add, edit or delete
         * @param volumeCreateOptions the options object for creating a new volume. Or null if modifying an existing volume
         * @param size the size to which the modified volume should be made or null if creating or deleting the volume
         * @return an AlterVMVolumeOptions object representing changes to volumes associated with an existing VM
         */
        public @Nonnull AlterVMVolumeOptions getInstance(@Nullable String providerVolumeID, @Nonnull AlterType alterType, @Nullable VolumeCreateOptions volumeCreateOptions, @Nullable Storage<Gigabyte> size){
            AlterVMVolumeOptions options = new AlterVMVolumeOptions();
            options.providerVolumeID = providerVolumeID;
            options.alterType = alterType;
            options.options = volumeCreateOptions;
            options.size = size;
            return options;
        }

        /**
         * @return the ID of the volume being modified or null if creating a new one
         */
        public @Nullable String getProviderVolumeID(){
            return this.providerVolumeID;
        }

        /**
         * @return the alterType representing exactly what underlying modification is requested
         */
        public @Nonnull AlterType getAlterType(){
            return this.alterType;
        }

        /**
         * @return the options for creating a new volume or null if the volume is being modified rather than created
         */
        public @Nullable VolumeCreateOptions getVolumeCreateOptions(){
            return this.options;
        }

        /**
         * @return the new size for a given volume or null if a volume is being created/deleted
         */
        public @Nullable Storage<Gigabyte> getSize(){
            return this.size;
        }

        /**
         * Specifies the provider volume ID in the case where an existing volume is being modified or removed
         * @param providerVolumeID the ID of the existing volume
         * @return this
         */
        public @Nonnull AlterVMVolumeOptions withProviderVolumeID(@Nonnull String providerVolumeID){
            this.providerVolumeID = providerVolumeID;
            return this;
        }

        /**
         * Specifies the type of alteration being performed
         * @param alterType the type
         * @return this
         */
        public @Nonnull AlterVMVolumeOptions setAlterType(@Nonnull AlterType alterType){
            this.alterType = alterType;
            return this;
        }

        /**
         * Specifies the options for creating a new volume
         * @param options the options for creating a new volume
         * @return this
         */
        public @Nonnull AlterVMVolumeOptions withVolumeCreateOptions(@Nonnull VolumeCreateOptions options){
            this.options = options;
            return this;
        }

        /**
         * When modifying an existing volume this specifies the size
         * @param size the new size of the volume
         * @return this
         */
        public @Nonnull AlterVMVolumeOptions withSize(@Nonnull Storage<Gigabyte> size){
            this.size = size;
            return this;
        }
    }

    public class AlterVMFirewallOptions{
        private String                providerFirewallID;
        private AlterType             alterType;
        private FirewallCreateOptions options;

        private AlterVMFirewallOptions(){}

        /**
         * Provides an object for describing changes to firewalls associated with an existing VM
         * @param providerFirewallID the ID of the firewall being modified or null if a new firewall is being created
         * @param alterType the type of alteration occurring. Can be any one of add or delete
         * @param firewallCreateOptions the options for creating a new firewall or null if deleting
         * @return an AlterVMFirewallOptions object describing changes to firewalls associated with an existing VM
         */
        public @Nonnull AlterVMFirewallOptions getInstance(@Nullable String providerFirewallID, @Nonnull AlterType alterType, @Nullable FirewallCreateOptions firewallCreateOptions){
            AlterVMFirewallOptions options = new AlterVMFirewallOptions();
            options.providerFirewallID = providerFirewallID;
            options.alterType = alterType;
            options.options = firewallCreateOptions;
            return options;
        }

        /**
         * @return the ID of the firewall being changed against the VM
         */
        public @Nullable String getProviderFirewallID(){
            return this.providerFirewallID;
        }

        /**
         * @return the alterType representing exactly what underlying modification is requested
         */
        public @Nonnull AlterType getAlterType(){
            return this.alterType;
        }

        /**
         * @return the options for the creation of a new firewall to be associated with the VM
         */
        public @Nullable FirewallCreateOptions getFirewallCreateOptions(){
            return this.options;
        }

        /**
         * Specifies a firewall ID that will either be associated with or dissociated from the VM
         * @param providerFirewallID the firewall ID
         * @return this
         */
        public @Nonnull AlterVMFirewallOptions withProviderFirewallID(@Nonnull String providerFirewallID){
            this.providerFirewallID = providerFirewallID;
            return this;
        }

        /**
         * Specifies the type of alteration being performed
         * @param alterType the type
         * @return this
         */
        public @Nonnull AlterVMFirewallOptions setAlterType(@Nonnull AlterType alterType){
            this.alterType = alterType;
            return this;
        }

        /**
         * Specifies options for the creation of a new firewall to be associated with the VM
         * @param options for describing the new firewall
         * @return this
         */
        public @Nonnull AlterVMFirewallOptions withFirewallCreateOptions(@Nonnull FirewallCreateOptions options){
            this.options = options;
            return this;
        }
    }
}
