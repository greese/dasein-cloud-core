package org.dasein.cloud.quotas;

import javax.annotation.Nullable;

/**
 * User: mgulimonov
 * Date: 25.07.2014
 */
public interface QuotaServices {

    @Nullable QuotaSupport getQuotaSupport();

}
