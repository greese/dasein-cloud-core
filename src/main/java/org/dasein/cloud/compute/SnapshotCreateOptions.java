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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Options for creating/copying snapshots.
 * <p>Created by George Reese: 2/4/13 2:14 PM</p>
 * @author George Reese
 * @since 2013.04
 * @version 2013.04 initial version
 */
public class SnapshotCreateOptions {
    /**
     * Constructs an options object that supports the creation of a new snapshot in the current region sourced from
     * the snapshot in the specified region.
     * @param fromRegionId the region of the source snapshot that will be copied
     * @param ofSnapshotId the unique ID of the snapshot to be copied
     * @param name the name of the new snapshot
     * @param description a description of the new snapshot
     * @return an options object that will support the copy of the specified snapshot
     */
    static public SnapshotCreateOptions getInstanceForCopy(@Nonnull String fromRegionId, @Nonnull String ofSnapshotId, @Nonnull String name, @Nonnull String description) {
        SnapshotCreateOptions options = new SnapshotCreateOptions();

        options.name = name;
        options.description = description;
        options.regionId = fromRegionId;
        options.snapshotId = ofSnapshotId;
        return options;
    }

    /**
     * Constructs an options object that supports the creation of a new snapshot from the specified volume.
     * @param volumeId the volume to be snapshotted
     * @param name the name of the new snapshot
     * @param description a description of the snapshot
     * @return an options object that will support the creation of a snapshot from the specified volume
     */
    static public SnapshotCreateOptions getInstanceForCreate(@Nonnull String volumeId, @Nonnull String name, @Nonnull String description) {
        SnapshotCreateOptions options = new SnapshotCreateOptions();

        options.name = name;
        options.description = description;
        options.volumeId = volumeId;
        return options;
    }

    private String             description;
    private Map<String,String> metaData;
    private String             name;
    private String             regionId;
    private String             snapshotId;
    private String             volumeId;

    private SnapshotCreateOptions() { }

    /**
     * Creates a snapshot in the specified cloud based on the contents of this options object.
     * @param provider the provider object for the cloud in which the snapshot should be created
     * @return the ID of the newly created snapshot or <code>null</code> if no snapshot was created because no changes have occurred
     * @throws CloudException an error occurred with the cloud provider while making the snapshot
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws OperationNotSupportedException snapshots are not supported in this cloud
     */
    public @Nullable String build(@Nonnull CloudProvider provider) throws CloudException, InternalException {
        ComputeServices services = provider.getComputeServices();

        if( services == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support compute services");
        }
        SnapshotSupport support = services.getSnapshotSupport();

        if( support == null ) {
            throw new OperationNotSupportedException(provider.getCloudName() + " does not support snapshots");
        }
        return support.createSnapshot(this);
    }

    /**
     * @return a description of the snapshot
     */
    public @Nonnull String getDescription() {
        return description;
    }

    /**
     * @return any extra meta-data to assign to the snapshot
     */
    public @Nonnull Map<String,String> getMetaData() {
        return (metaData == null ? new HashMap<String, String>() : metaData);
    }

    /**
     * @return a name for the snapshot
     */
    public @Nonnull String getName() {
        return name;
    }

    /**
     * @return the region ID where the source snapshot sits (for copy operations)
     */
    public @Nullable String getRegionId() {
        return regionId;
    }

    /**
     * @return the unique ID of the snapshot being copied (for copy operations)
     */
    public @Nullable String getSnapshotId() {
        return snapshotId;
    }

    /**
     * @return the unique ID of the volume being snapshotted (for create operations)
     */
    public @Nullable String getVolumeId() {
        return volumeId;
    }

    /**
     * Specifies any meta-data to be associated with the snapshot when created. This method is
     * accretive, meaning that it adds to any existing meta-data (or replaces an existing key).
     * @param key the key of the meta-data entry
     * @param value the value for the meta-data entry
     * @return this
     */
    public @Nonnull SnapshotCreateOptions withMetaData(@Nonnull String key, @Nonnull String value) {
        if( metaData == null ) {
            metaData = new HashMap<String, String>();
        }
        metaData.put(key, value);
        return this;
    }

    /**
     * Specifies meta-data to add onto any existing meta-data being associated with this snapshot when created.
     * This method is accretive, meaning that it adds to any existing meta-data (or replaces an existing keys).
     * @param metaData the meta-data to be set for the new firewall
     * @return this
     */
    public @Nonnull SnapshotCreateOptions withMetaData(@Nonnull Map<String,String> metaData) {
        if( this.metaData == null ) {
            this.metaData = new HashMap<String, String>();
        }
        this.metaData.putAll(metaData);
        return this;
    }

    @Override
    public @Nonnull String toString() {
        if( volumeId == null ) {
            return ("[copy -> " + regionId + "/" + snapshotId + ": " + name + "]");
        }
        else {
            return ("[create -> " + volumeId  + ": " + name + "]");
        }
    }

}
