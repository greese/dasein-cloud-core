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

/**
 * <p>
 * The various possible status a server can have. Implementors should do their best to
 * map cloud-specific states to these states.
 * </p>
 */
public enum VmStatus {
  /**
   * The server is functioning as expected - set by provider.
   */
  OK,
  /**
   * The server is not functioning as expected - set by provider.
   */
  IMPAIRED,
  /**
   * Not enough data to detect status - set by provider.
   */
  INSUFFICIENT_DATA,
  /**
   * VM status cannot be applied to this type of server - set by provider.
   */
  NOT_APPLICABLE,
  /**
   * Could not get status from provider.  Set by dasein.  Should be used in cases of null or empty
   * values from provider
   */
  STATUS_UNAVAILABLE
}
