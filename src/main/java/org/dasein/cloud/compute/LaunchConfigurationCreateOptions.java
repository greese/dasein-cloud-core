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

package org.dasein.cloud.compute;

/**
 * @author Chris Kelner (http://github.com/ckelner)
 * @since 2014-03-01
 */
public class LaunchConfigurationCreateOptions {

    private String                name;
    private String                imageId;
    private VirtualMachineProduct size;
    private String                keyPairName;
    private String                userData;
    private String                providerRoleId;
    private Boolean               detailedMonitoring;
    private String[]              firewallIds;
    private Boolean               associatePublicIPAddress;
    private Boolean               ioOptimized;
    private String                virtualMachineIdToClone;
    private VolumeAttachment[]    volumeAttachment;

    public LaunchConfigurationCreateOptions(final String name, final String imageId, final VirtualMachineProduct size, final String keyPairName, final String userData, final String providerRoleId, final Boolean detailedMonitoring, final String[] firewallIds) {
        this.name = name;
        this.imageId = imageId;
        this.size = size;
        this.keyPairName = keyPairName;
        this.userData = userData;
        this.providerRoleId = providerRoleId;
        this.detailedMonitoring = detailedMonitoring;
        this.firewallIds = firewallIds;
    }

    public LaunchConfigurationCreateOptions(final String name, final String imageId, final VirtualMachineProduct size, final String keyPairName, final String userData, final String providerRoleId, final Boolean detailedMonitoring, final String[] firewallIds, final Boolean associatePublicIPAddress, final Boolean ioOptimized, final String virtualMachineIdToClone, final VolumeAttachment[] volumeAttachment) {
        this.name = name;
        this.imageId = imageId;
        this.size = size;
        this.keyPairName = keyPairName;
        this.userData = userData;
        this.providerRoleId = providerRoleId;
        this.detailedMonitoring = detailedMonitoring;
        this.firewallIds = firewallIds;
        this.associatePublicIPAddress = associatePublicIPAddress;
        this.ioOptimized = ioOptimized;
        this.virtualMachineIdToClone = virtualMachineIdToClone;
        this.volumeAttachment = volumeAttachment;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }

    public VirtualMachineProduct getSize() {
        return size;
    }

    public String getKeypairName() {
        return keyPairName;
    }

    public String getUserData() {
        return userData;
    }

    public String getProviderRoleId() {
        return providerRoleId;
    }

    public Boolean getDetailedMonitoring() {
        return detailedMonitoring;
    }

    public String[] getFirewallIds() {
        return firewallIds;
    }

    public Boolean getAssociatePublicIPAddress() {
        return associatePublicIPAddress;
    }

    public Boolean getIOOptimized() {
        return ioOptimized;
    }

    public String getVirtualMachineIdToClone() {
        return virtualMachineIdToClone;
    }

    public VolumeAttachment[] getVolumeAttachment() {
        return volumeAttachment;
    }

    public LaunchConfigurationCreateOptions withImageId(final String imageId) {
        this.imageId = imageId;
        return this;
    }

    public LaunchConfigurationCreateOptions withSize(final VirtualMachineProduct size) {
        this.size = size;
        return this;
    }

    public LaunchConfigurationCreateOptions withKeyPairname(final String keyPairName) {
        this.keyPairName = keyPairName;
        return this;
    }

    public LaunchConfigurationCreateOptions withUserData(final String userData) {
        this.userData = userData;
        return this;
    }

    public LaunchConfigurationCreateOptions withProviderRoleId(final String providerRoleId) {
        this.providerRoleId = providerRoleId;
        return this;
    }

    public LaunchConfigurationCreateOptions withDetailedMonitoring(final Boolean detailedMonitoring) {
        this.detailedMonitoring = detailedMonitoring;
        return this;
    }

    public LaunchConfigurationCreateOptions withFirewallIds(final String[] firewallIds) {
        this.firewallIds = firewallIds;
        return this;
    }

    public LaunchConfigurationCreateOptions withPublicIPAddress(final Boolean value) {
        this.associatePublicIPAddress = value;
        return this;
    }

    public LaunchConfigurationCreateOptions withIOOptimization(final Boolean value) {
        this.ioOptimized = value;
        return this;
    }

    public LaunchConfigurationCreateOptions withVirtualMachineIdToClone(final String virtualMachineIdToClone) {
        this.virtualMachineIdToClone = virtualMachineIdToClone;
        return this;
    }

    public LaunchConfigurationCreateOptions withVolumeAttachments(final VolumeAttachment[] volumeAttachments) {
        this.volumeAttachment = volumeAttachments;
        return this;
    }

}
