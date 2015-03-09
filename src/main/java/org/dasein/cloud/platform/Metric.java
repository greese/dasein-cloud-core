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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a metric associated with a monitoring service.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-02-18
 */
public class Metric implements Serializable {

  private static final long serialVersionUID = 1532589463237342269L;

  private String name;
  private String namespace;
  private Map<String, String> metadata;

  public Metric() {
  }

  /**
   * @return the metric name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the metric name.
   *
   * @param name the metric name
   */
  public void setName( String name ) {
    this.name = name;
  }

  /**
   * @return the metric namespace, if namespaces are supported by the provider
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Sets the metric namespace.
   *
   * @param namespace the metric namespace
   */
  public void setNamespace( String namespace ) {
    this.namespace = namespace;
  }

  /**
   * @return associated metadata (or dimensions) for the metric
   */
  public Map<String, String> getMetadata() {
    return metadata;
  }

  /**
   * Sets metadata for the metric. Wipes all existing metadata.
   *
   * @param newMetadata metadata for the metric
   */
  public void setMetadata( Map<String, String> newMetadata ) {
    this.metadata = newMetadata;
  }

  /**
   * Adds a single metadata value, overwriting any existing value.
   *
   * @param name  name of the metadata
   * @param value value of the metadata
   */
  public void addMetadata( String name, String value ) {
    if ( this.metadata == null ) {
      this.metadata = new HashMap<String, String>();
    }
    this.metadata.put( name, value );
  }

  /**
   * Adds metadata, overwriting existing values if they already exist, leaving other existing metadata intact.
   *
   * @param newMetadata new metadata to add
   */
  public void addMetadata( Map<String, String> newMetadata ) {
    if ( this.metadata == null ) {
      this.metadata = new HashMap<String, String>();
    }
    this.metadata.putAll( newMetadata );
  }

  @Override
  public String toString() {
    return name;
  }

}
