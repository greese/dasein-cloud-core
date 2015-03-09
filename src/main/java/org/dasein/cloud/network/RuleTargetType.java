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

package org.dasein.cloud.network;

/**
 * Describes a destination for a firewall rule.
 * <p>Created by George Reese: 11/18/12 9:55 AM</p>
 * @author George Reese
 * @version 2013.01 initial version (Issue #11)
 * @since 2013.01
 */
public enum RuleTargetType {
    GLOBAL, VLAN, VM, CIDR
}
