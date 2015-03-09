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
 * <p>
 * Host and Server status for a given Virtual Machine
 * </p>
 */
public class VirtualMachineStatus {
    private String                providerVirtualMachineId;
    private VmStatus              providerHostStatus;
    private VmStatus              providerVmStatus;

  public VirtualMachineStatus() { }

    public String getProviderVirtualMachineId() {
        return providerVirtualMachineId;
    }

    public void setProviderVirtualMachineId(String providerVirtualMachineId) {
        this.providerVirtualMachineId = providerVirtualMachineId;
    }

    public VmStatus getProviderVmStatus() {
      return providerVmStatus;
    }

    public void setProviderVmStatus(VmStatus vmStatus) {
      this.providerVmStatus = vmStatus;
    }

    public VmStatus getProviderHostStatus() {
      return providerHostStatus;
    }

    public void setProviderHostStatus(VmStatus vmStatus) {
      this.providerHostStatus = vmStatus;
    }

}
