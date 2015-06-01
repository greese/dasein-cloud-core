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

import javax.annotation.Nullable;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 6/3/13 3:27 PM</p>
 *
 * @author George Reese
 */
public interface CIServices {
    public boolean hasConvergedInfrastructureSupport(); // replica pools

    public @Nullable ConvergedInfrastructureSupport getConvergedInfrastructureSupport();

    public boolean hasConvergedHttpLoadBalancerSupport();

    public @Nullable ConvergedHttpLoadBalancerSupport getConvergedHttpLoadBalancerSupport();

    /**
     * @return indicates whether or not the cloud provider supports complex resource topologies
     */
    public boolean hasTopologySupport(); // templates

    public @Nullable TopologySupport getTopologySupport();
}
