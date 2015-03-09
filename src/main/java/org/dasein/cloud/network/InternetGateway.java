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

package org.dasein.cloud.network;

import org.dasein.cloud.Taggable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * An AWS specific component used to make a private subnet into a public subnet.  Private subnet traffic to be
 * forwarded to a NAT that routes traffic to the Internet Gateway in the public subnet allowing internet communications.
 * <p>Created by Chris Kelner: 7/11/13 5:07 PM</p>
 * @author Chris Kelner (chris.kelner@weather.com)
 */
public class InternetGateway implements Taggable {

    private String                            providerInternetGatewayId;
    private String                            providerVlanId;
    private InternetGatewayAttachmentState    attachmentState;
    private String                            providerOwnerId;
    private String                            providerRegionId;
    private Map<String, String>               tags;

    public InternetGateway() { }

    public String getProviderInternetGatewayId() {
        return providerInternetGatewayId;
    }

    public void setProviderInternetGatewayId(String id) {
      this.providerInternetGatewayId = id;
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

    @Override
    public @Nonnull Map<String,String> getTags() {
        if( tags == null ) {
            tags = new HashMap<String, String>();
        }
        return tags;
    }

    @Override
    public void setTag(@Nonnull String key, @Nonnull String value) {
        if( tags == null ) {
            tags = new HashMap<String,String>();
        }
        tags.put(key, value);
    }

    /**
     * Sets the meta-data tags and overwrites any existing values.
     * @param tags the tags to be set
     */
    public void setTags(@Nonnull Map<String,String> tags) {
        this.tags = tags;
    }

}
