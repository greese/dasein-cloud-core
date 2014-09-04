package org.dasein.cloud.platform.support.model.options;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
public class TicketListRepliesOptions {

    private String afterTime;
    private String beforeTime;
    private String ticketId;

    public String getAfterTime() {
        return afterTime;
    }

    public void setAfterTime( String afterTime ) {
        this.afterTime = afterTime;
    }

    public String getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime( String beforeTime ) {
        this.beforeTime = beforeTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId( String ticketId ) {
        this.ticketId = ticketId;
    }

}
