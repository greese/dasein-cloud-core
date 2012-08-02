/**
 * Copyright (C) 2009-2012 enStratus Networks Inc.
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
 * [Class Documentation]
 * <p>Created by greese: 6/26/12 10:43 AM</p>
 *
 * @author greese
 * @version [CURRENT_VERSION] (bugzid: [FOGBUGZID])
 * @since [CURRENT_RELEASE]
 */
public interface Taggable {
    public @Nonnull Map<String,String> getTags();

    public void setTag(@Nonnull String key, @Nonnull String value);
}
