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
