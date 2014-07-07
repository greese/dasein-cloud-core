package org.dasein.cloud.dc;

import org.dasein.cloud.Capabilities;

import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * User: daniellemayne
 * Date: 04/07/2014
 * Time: 16:21
 */
public interface DataCenterCapabilities extends Capabilities {

    /**
     * Provides the cloud-specific term for a data center (e.g. "availability zone").
     * @param locale the locale into which the term should be translated
     * @return the term for a data center
     */
    public String getProviderTermForDataCenter(Locale locale);

    /**
     * Provides the cloud-specific term for a region.
     * @param locale the locale into which the term should be translated
     * @return the term for a region
     */
    public String getProviderTermForRegion(Locale locale);

    /**
     * Specifies whether the given cloud supports the concept of resource pools
     */
    public boolean supportsResourcePools();
}
