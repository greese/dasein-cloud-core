/**
 * Copyright (C) 2009-2013 enstratius, Inc.
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

package org.dasein.cloud.network;

/**
 * An AWS specific component used to make a private subnet into a public subnet.  Private subnet traffic to be
 * forwarded to a NAT that routes traffic to the Internet Gateway in the public subnet allowing internet communications.
 * <p>Created by Chris Kelner: 7/11/13 5:07 PM</p>
 * @author Chris Kelner (chris.kelner@weather.com)
 */
public class InternetGateway {

    private String                            gatewayId;
    private String                            providerVlanId;
    private InternetGatewayAttachmentState    attachmentState;
    private String                            providerOwnerId;
    private String                            providerRegionId;

    public InternetGateway() { }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
      this.gatewayId = gatewayId;
    }

    public String getProviderVlanId() {
      return providerVlanId;
    }

    public void setProviderVlanId(String providerVlanId) {
      this.providerVlanId = providerVlanId;
    }

    public void setAttachmentState(InternetGatewayAttachmentState attachmentState) {
      this.attachmentState = attachmentState;
    }

    public InternetGatewayAttachmentState getAttachmentState() {
      return attachmentState;
    }

    public String getProviderOwnerId() {
      return providerOwnerId;
    }

    public void setProviderOwnerId(String providerOwnerId) {
      this.providerOwnerId = providerOwnerId;
    }

    public String getProviderRegionId() {
      return providerRegionId;
    }

    public void setProviderRegionId(String providerRegionId) {
      this.providerRegionId = providerRegionId;
    }

}
