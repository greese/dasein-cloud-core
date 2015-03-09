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
 * @since 2014-03-04
 */
public class VolumeAttachment {

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getExistingVolumeId() {
    return existingVolumeId;
  }

  public void setExistingVolumeId(String existingVolumeId) {
    this.existingVolumeId = existingVolumeId;
  }

  public boolean isRootVolume() {
    return rootVolume;
  }

  public void setRootVolume(boolean rootVolume) {
    this.rootVolume = rootVolume;
  }

  public VolumeCreateOptions getVolumeToCreate() {
    return volumeToCreate;
  }

  public void setVolumeToCreate(VolumeCreateOptions volumeToCreate) {
    this.volumeToCreate = volumeToCreate;
  }

  public String deviceId;
  public String existingVolumeId;
  public boolean rootVolume;
  public VolumeCreateOptions volumeToCreate;

}
