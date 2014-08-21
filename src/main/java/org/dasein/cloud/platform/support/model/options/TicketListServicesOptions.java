package org.dasein.cloud.platform.support.model.options;

import java.util.Collection;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
public class TicketListServicesOptions {

    String language;
    Collection<String> serviceCodeList;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Collection<String> getServiceCodeList() {
        return serviceCodeList;
    }

    public void setServiceCodeList(Collection<String> serviceCodeList) {
        this.serviceCodeList = serviceCodeList;
    }

}
