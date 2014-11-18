package org.dasein.cloud.quotas;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import java.util.Collection;
import java.util.Collections;

/**
 * User: mgulimonov
 * Date: 23.07.2014
 */
public abstract class AbstractQuotaSupport implements QuotaSupport {

    @Override
    public Collection<QuotaDescriptor> getQuotas(String regionId) throws CloudException, InternalException{
        return Collections.emptyList();
    }
}
