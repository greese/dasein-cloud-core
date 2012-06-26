/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
 * ====================================================================
 */
package org.dasein.cloud;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * [Class Documentation]
 * <p>Created by greese: 6/26/12 10:43 AM</p>
 *
 * @author greese
 * @version [CURRENT_VERSION] (bugzid: [FOGBUGZID])
 * @since [CURRENT_RELEASE]
 */
public interface Taggable {
    public @Nonnull Map<String,String> getTags();

    public void setTag(@Nonnull String key, @Nonnull String value);
}
