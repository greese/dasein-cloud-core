/*
 * Copyright (C) 2014 Dell, Inc.
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
package org.dasein.cloud.util;

/**
 * Any cloud resource that is identified by a friendly name that, in some cases, may be unique.
 * <p>Created by George Reese: 3/4/14 9:07 AM (based on thinking from Stas Maksimov)</p>
 * @author George Reese
 * @version 2014.03 (initial version)
 * @since 2014.03
 */
public interface NamedItem {
    public String getName();
}
