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

package org.dasein.cloud;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Identifies a resource that may be tagged with meta-data.
 * <p>Created by George Reese: 6/26/12 10:43 AM</p>
 * @author greese
 * @version 2012.09
 * @since 2012.09
 */
public interface Taggable {
    /**
     * @return the meta-data associated with the taggable resource
     */
    public @Nonnull Map<String,String> getTags();

    /**
     * Sets a specific key/vaue pair in the taggable resource.
     * @param key the key to be set
     * @param value the value to be associated with the key
     */
    public void setTag(@Nonnull String key, @Nonnull String value);
}
