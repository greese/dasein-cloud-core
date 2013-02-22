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

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace( String namespace ) {
    this.namespace = namespace;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata( Map<String, String> newMetadata ) {
    this.metadata = newMetadata;
  }

  public void addMetadata( String name, String value ) {
    if ( this.metadata == null ) {
      this.metadata = new HashMap<String, String>();
    }
    this.metadata.put( name, value );
  }

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
