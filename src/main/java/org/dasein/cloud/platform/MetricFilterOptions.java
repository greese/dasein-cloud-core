package org.dasein.cloud.platform;

import java.util.Map;

/**
 * Options for filtering metrics based on specific criteria.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public class MetricFilterOptions {

  public static MetricFilterOptions getInstance() {
    return new MetricFilterOptions();
  }

  private String metricName;
  private String metricNamespace;
  private Map<String, String> metricDimensions;

  private MetricFilterOptions() {
  }

  public String getMetricName() {
    return metricName;
  }

  public String getMetricNamespace() {
    return metricNamespace;
  }

  public Map<String, String> getMetricDimensions() {
    return metricDimensions;
  }

  public MetricFilterOptions withMetricName( String metricName ) {
    this.metricName = metricName;
    return this;
  }

  public MetricFilterOptions withMetricNamespace( String metricNamespace ) {
    this.metricNamespace = metricNamespace;
    return this;
  }

  public MetricFilterOptions withMetricDimensions( Map<String, String> metricDimensions ) {
    this.metricDimensions = metricDimensions;
    return this;
  }

}
