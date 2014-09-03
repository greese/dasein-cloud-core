package org.dasein.cloud.platform.support.model.options;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
public class TicketGetOptions {

    String ticketId;

    Boolean includeCommunications = true;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Boolean getIncludeCommunications() {
        return includeCommunications;
    }

    public void setIncludeCommunications(Boolean includeCommunications) {
        this.includeCommunications = includeCommunications;
    }
}
