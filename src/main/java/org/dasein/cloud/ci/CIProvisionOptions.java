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

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;

/**
 * Options for provisioning infrastructure from topologies.
 * <p>Created by George Reese: 5/31/13 9:30 AM</p>
 * @author George Reese
 * @version 2013.07 initial version
 * @since 2013.07
 */
public class CIProvisionOptions {
    private String baseInstanceName;
    private String instanceTemplate;
    private String description;
    private String name;
    private String zone;
    private int size;

    private CIProvisionOptions() { }

    static public @Nonnull CIProvisionOptions getInstance(@Nonnull String name, @Nonnull String description, @Nonnull String zone, @Nonnull int size, @Nonnull String instanceTemplate) {
        CIProvisionOptions options = new CIProvisionOptions();

        options.instanceTemplate = instanceTemplate;
        options.description = description;
        options.zone = zone;
        options.size = size;

        options.name = name;
        options.baseInstanceName = name;
        return options;
    }

    public String getZone() {
        return zone;
    }

    public Integer getSize() {
        return size;
    }

    public @Nonnull CIProvisionOptions withBaseInstanceName(@Nonnull String baseInstanceName) {
        this.baseInstanceName = baseInstanceName;
        return this;
    }

    public String getBaseInstanceName() {
        return baseInstanceName;
    }

    public String getDescription() {
        return description;
    }

    public String getInstanceTemplate() {
        return instanceTemplate;
    }

    public String getName() {
        return name;
    }

}
