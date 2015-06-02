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

package org.dasein.cloud.ci;

import javax.annotation.Nonnull;
import org.dasein.cloud.Capabilities;
import org.dasein.cloud.util.NamingConstraints;

public interface ConvergedInfrastructureCapabilities extends Capabilities {
    public boolean supportsHttpTraffic();

    public boolean supportsHttpsTraffic();

    public boolean supportsMetadata();

    public boolean supportsSshKeys();

    public boolean supportsTags();

    public boolean supportsSsdDisk();

    public boolean supportsStandardDisk();

    public boolean supportsDeleteDiskOnTerminate();

    public boolean supportsReadOnlySharedDisks();

    public boolean supportsVmAutomaticRestart();

    public boolean supportsMigrateVmOnMaintenance();

    public boolean supportsTemplates();

    public boolean supportsRegions();

    public boolean supportsCreateFromInstance();

    public boolean supportsAutoScaling();

    /**
     * Identifies the naming conventions that constrain how converged infrastructure (replica pools) may be named (friendly name) in this cloud.
     * @return naming conventions that constrain converged infrastructure naming
     */
    public @Nonnull NamingConstraints getConvergedInfrastructureNamingConstraints();
}
