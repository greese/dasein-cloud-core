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

import java.io.Serializable;

public class LaunchConfiguration implements Serializable {
    private static final long serialVersionUID = -1158496494385174436L;
    
    private long     creationTimestamp;
    private String   name;
    private String[] providerFirewallIds;
    private String   id;
    private String   providerKeypairName;
    private String   userData;
    private String   providerImageId;
    private String   providerLaunchConfigurationId;
    private String   serverSizeId;
    private String   providerRoleId;
    private boolean  detailedMonitoring;
    private Boolean  associatePublicIPAddress;
    private VolumeAttachment[] volumeAttachment;
    private boolean ioOptimized;
    private String imageId;
    
    public LaunchConfiguration() { }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getProviderFirewallIds() {
        return providerFirewallIds;
    }

    public void setProviderFirewallIds(String[] providerFirewallIds) {
        this.providerFirewallIds = providerFirewallIds;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getId(){
      return this.id;
    }

    public void setProviderKeypairName(String keyPairName){
      this.providerKeypairName = keyPairName;
    }

    public String getProviderKeypairName(){
      return this.providerKeypairName;
    }

    public void setUserData(String userData){
      this.userData = userData;
    }

    public String getUserData(){
      return this.userData;
    }

    public String getProviderImageId() {
        return providerImageId;
    }

    public void setProviderImageId(String providerImageId) {
        this.providerImageId = providerImageId;
    }

    public String getProviderLaunchConfigurationId() {
        return providerLaunchConfigurationId;
    }

    public void setProviderLaunchConfigurationId(String providerLaunchConfigurationId) {
        this.providerLaunchConfigurationId = providerLaunchConfigurationId;
    }

    public String getServerSizeId() {
        return serverSizeId;
    }

    public void setServerSizeId(String serverSizeId) {
        this.serverSizeId = serverSizeId;
    }

    public String getProviderRoleId() {
      return providerRoleId;
    }

    public void setProviderRoleId(String providerRoleId) {
      this.providerRoleId = providerRoleId;
    }

    public boolean getDetailedMonitoring() {
      return detailedMonitoring;
    }

    public void setDetailedMonitoring(boolean detailedMonitoring) {
      this.detailedMonitoring = detailedMonitoring;
    }

    public Boolean getAssociatePublicIPAddress() {
      return associatePublicIPAddress;
    }

    public void setAssociatePublicIPAddress(Boolean associatePublicIPAddress) {
      this.associatePublicIPAddress = associatePublicIPAddress;
    }

    public VolumeAttachment[] getVolumeAttachment() {
      return volumeAttachment;
    }

    public void setVolumeAttachment(VolumeAttachment[] volumeAttachments) {
      this.volumeAttachment = volumeAttachments;
    }

    public boolean getIoOptimized() {
      return ioOptimized;
    }

    public void setIoOptimized(boolean ioOptimized) {
      this.ioOptimized = ioOptimized;
    }

    public String getImageId() {
      return imageId;
    }

    public void setImageId(String imageId) {
      this.imageId = imageId;
    }
    
}
