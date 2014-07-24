package org.dasein.cloud.quotas;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import java.util.Collection;

/**
 * User: mgulimonov
 * Date: 23.07.2014
 */
public interface QuotaSupport {

    public Collection<QuotaDescriptor> getQuotas() throws CloudException, InternalException;

}
