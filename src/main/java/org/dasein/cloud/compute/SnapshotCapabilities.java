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

import org.dasein.cloud.*;
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface SnapshotCapabilities extends Capabilities{
    /**
     * Specifies the provider's term for snapshot in the specified locale.
     * @param locale the locale for which the snapshot term should be translated
     * @return the term for snapshot in the target cloud
     */
    public @Nonnull String getProviderTermForSnapshot(@Nonnull Locale locale);

    /**
     * Returns the visible scope of the Snapshot or null if not applicable for the specific cloud
     * @return the Visible Scope of the Snapshot
     */
    public @Nullable VisibleScope getSnapshotVisibleScope();

    /**
     * Indicates whether or not the cloud requires a volume to be attached when performing a snapshot. {@link org.dasein.cloud.Requirement#REQUIRED}
     * means that a volume must be attached; {@link org.dasein.cloud.Requirement#OPTIONAL} means that it may be attached; {@link org.dasein.cloud.Requirement#NONE}
     * means that the volume must be detached.
     * @return the requirement associated with volume attachment in creating a snapshot
     * @throws org.dasein.cloud.InternalException an error occurred within the Dasein Cloud implementation
     * @throws org.dasein.cloud.CloudException an error occurred with the cloud provide
     */
    public @Nonnull Requirement identifyAttachmentRequirement() throws InternalException, CloudException;

    /**
     * Indicates whether or not you can copy existing snapshots from other regions.
     * @return true if snapshot copying is supported
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsSnapshotCopying() throws CloudException, InternalException;

    /**
     * Indicates whether or not you can snapshot volumes in this region.
     * @return true if volume snapshotting is supported
     * @throws CloudException an error occurred with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    public boolean supportsSnapshotCreation() throws CloudException, InternalException;

    /**
     * Indicates whether or not a snapshot owner can explicitly share a snapshot with another account.
     * @return true if snapshot sharing is supported
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public boolean supportsSnapshotSharing() throws InternalException, CloudException;

    /**
     * Indicates whether or not a snapshot owner can share a snapshot with the general public.
     * @return true if public snapshot sharing is supported
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     * @throws CloudException an error occurred with the cloud provider
     */
    public boolean supportsSnapshotSharingWithPublic() throws InternalException, CloudException;

    /**
     * Identifies the naming conventions that constrain how snapshots may be named (friendly name) in this cloud.
     * @return naming conventions that constrain snapshot naming
     * @throws CloudException an error occurred querying the cloud for naming constraints
     * @throws InternalException an error occurred assembling the naming constraints object
     */
    public @Nonnull NamingConstraints getSnapshotNamingConstraints() throws CloudException, InternalException;
}
