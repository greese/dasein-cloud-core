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

import javax.annotation.Nonnull;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 3/4/14 9:13 AM</p>
 *
 * @author George Reese
 */
public interface NamedItemLoader {
    public boolean hasNamedItem(@Nonnull String withName);

    public @Nonnull String increment(@Nonnull String baseName);

    /**
     * Verifies that the proposed base name is a valid name for the current cloud. If not, it will attempt to correct
     * the name so that it matches the naming requirements for the resource type in question. It does not
     * concern itself with uniqueness.
     * @param baseName the requested base name
     * @return a base name that is valid for resources of this object's type in this cloud
     */
    public @Nonnull String validateBaseName(@Nonnull String baseName);
}
