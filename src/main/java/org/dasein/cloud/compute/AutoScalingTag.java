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

import org.dasein.cloud.Tag;

/**
 * A sub-class of {@link Tag} with customizations for auto-scaling groups.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-11-18
 */
public class AutoScalingTag extends Tag {

  /**
   * Specifies whether the new tag will be applied to instances launched after the tag is created.
   */
  private Boolean propagateAtLaunch;

  public AutoScalingTag(String key, String value, Boolean propagateAtLaunch) {
    super(key, value);
    this.propagateAtLaunch = propagateAtLaunch;
  }

  public Boolean isPropagateAtLaunch() {
    return propagateAtLaunch;
  }

}
