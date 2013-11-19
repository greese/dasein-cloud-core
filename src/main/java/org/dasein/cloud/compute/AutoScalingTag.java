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
