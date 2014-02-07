/*
 * Copyright (C) 2014 enStratus Networks Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud.platform.bigdata;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;

/**
 * Implements support for bigdata/data warehousing services like Amazon's Redshift.
 * <p>Created by George Reese: 2/7/14 12:06 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version (issue #100)
 */
public interface DataWarehouseSupport {
    public @Nonnull Iterable<DataCluster> listClusters() throws CloudException, InternalException;
}
