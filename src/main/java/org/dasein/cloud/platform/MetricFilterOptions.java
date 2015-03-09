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

import java.util.Map;

/**
 * Options for filtering metrics based on specific criteria.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public class MetricFilterOptions {

  /**
   * Factory method for creating a MetricFilterOptions.
   *
   * @return a new and empty MetricFilterOptions
   */
  public static MetricFilterOptions getInstance() {
    return new MetricFilterOptions();
  }

  private String metricName;
  private String metricNamespace;
  private Map<String, String> metricMetadata;

  private MetricFilterOptions() {
  }

  /**
   * @return the metric name
   */
  public String getMetricName() {
    return metricName;
  }

  /**
   * @return the metric namespace, if namespaces are supported by the provider
   */
  public String getMetricNamespace() {
    return metricNamespace;
  }

  /**
   * @return associated metadata (or dimensions) for the metric
   */
  public Map<String, String> getMetricMetadata() {
    return metricMetadata;
  }

  /**
   * Sets the metric name to filter by.
   *
   * @param metricName the metric name to filter by
   * @return this
   */
  public MetricFilterOptions withMetricName( String metricName ) {
    this.metricName = metricName;
    return this;
  }

  /**
   * Sets the metric namespace to filter by. Not all providers support namespaces.
   *
   * @param metricNamespace the metric namespace to filter by
   * @return this
   */
  public MetricFilterOptions withMetricNamespace( String metricNamespace ) {
    this.metricNamespace = metricNamespace;
    return this;
  }

  /**
   * Sets metric metadata (or dimensions) to filter by. Not all providers support metadata.
   *
   * @param metricMetadata metric metadata (or dimensions) to filter by
   * @return this
   */
  public MetricFilterOptions withMetricMetadata( Map<String, String> metricMetadata ) {
    this.metricMetadata = metricMetadata;
    return this;
  }

}
