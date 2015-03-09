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

package org.dasein.cloud.platform;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of a KeyValue Database within a cloud for a specific account.
 * @author Drew Lyall
 * @version 2014.08
 * @since 2014.08
 */

public interface KeyValueDatabaseCapabilities extends Capabilities{
    /**
     * Specifies the provider term for a KeyValue Database.
     * @param locale the locale into which the term should be translated
     * @return a localized term for a KeyValue Database using the cloud provider's terminology
     */
    public @Nonnull String getProviderTermForDatabase(Locale locale);

    /**
     * Specifies whether the region represented by the current context supports key value databases. If the
     * context has no region, this method will answer the question in general (should be true).
     * @return true if this region supports key/value databases
     * @throws CloudException an error occurred talking with the cloud provider
     * @throws InternalException an error occurred inside the implementation of Dasein Cloud
     */
    public boolean isSupportsKeyValueDatabases() throws CloudException, InternalException;
}
