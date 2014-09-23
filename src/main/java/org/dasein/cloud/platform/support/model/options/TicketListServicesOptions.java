package org.dasein.cloud.platform.support.model.options;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
public class TicketListServicesOptions {

    String language;
    String[] serviceCodeList;

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

    public String[] getServiceCodeList() {
        return serviceCodeList;
    }

    public void setServiceCodeList( String[] serviceCodeList ) {
        this.serviceCodeList = serviceCodeList;
    }

}
