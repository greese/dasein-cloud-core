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

package org.dasein.cloud.test;

import org.dasein.cloud.*;
import org.dasein.cloud.dc.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * Cloud provider implementation for testing basic functionality not specifically dependent on cloud operations.
 * <p>Created by George Reese: 2/28/14 7:23 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #123)
 * @since 2014.03
 */
public class TestNewCloudProvider extends AbstractCloud {
    @Override
    public @Nonnull String getCloudName() {
        ProviderContext ctx = getContext();
        Cloud cloud = (ctx == null ? null : ctx.getCloud());
        String name = (cloud == null ? null : cloud.getCloudName());

        if( name == null ) {
            return "Test Cloud";
        }
        return name;
    }

    @Override
    public @Nonnull ContextRequirements getContextRequirements() {
        return new ContextRequirements(
                new ContextRequirements.Field("apiKeys", ContextRequirements.FieldType.KEYPAIR),
                new ContextRequirements.Field("x509", ContextRequirements.FieldType.KEYPAIR),
                new ContextRequirements.Field("version", ContextRequirements.FieldType.TEXT)
        );
    }

    @Override
    public @Nonnull DataCenterServices getDataCenterServices() {
        return new DataCenterServices() {
            @Nonnull
            @Override
            public DataCenterCapabilities getCapabilities() throws InternalException, CloudException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public DataCenter getDataCenter(String providerDataCenterId) throws InternalException, CloudException {
                return null;
            }

            @Override
            public String getProviderTermForDataCenter(Locale locale) {
                return "data center";
            }

            @Override
            public String getProviderTermForRegion(Locale locale) {
                return "region";
            }

            @Override
            public Region getRegion(String providerRegionId) throws InternalException, CloudException {
                return null;
            }

            @Override
            public Collection<DataCenter> listDataCenters(String providerRegionId) throws InternalException, CloudException {
                return Collections.emptyList();
            }

            @Override
            public Collection<Region> listRegions() throws InternalException, CloudException {
                return Collections.emptyList();
            }

            @Override
            public Collection<ResourcePool> listResourcePools(String providerDataCenterId) throws InternalException, CloudException {
                return Collections.emptyList();
            }

            @Override
            public Collection<StoragePool> listStoragePools() throws InternalException, CloudException{
                return Collections.emptyList();
            }

            @Override
            public ResourcePool getResourcePool(String providerResourcePoolId) throws InternalException, CloudException {
                return null;
            }

            @Nonnull
            @Override
            public StoragePool getStoragePool(String providerStoragePoolId) throws InternalException, CloudException {
                return null;
            }

            @Nonnull
            @Override
            public Collection<Folder> listVMFolders() throws InternalException, CloudException {
                return Collections.emptyList();
            }

            @Nonnull
            @Override
            public Folder getVMFolder(String providerVMFolderId) throws InternalException, CloudException {
                return null;
            }
        };
    }

    @Override
    public @Nonnull String getProviderName() {
        ProviderContext ctx = getContext();
        Cloud cloud = (ctx == null ? null : ctx.getCloud());
        String name = (cloud == null ? null : cloud.getProviderName());

        if( name == null ) {
            return "Test Provider";
        }
        return name;
    }
}
