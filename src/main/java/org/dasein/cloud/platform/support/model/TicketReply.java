package org.dasein.cloud.platform.support.model;

public class TicketReply {

    private TicketAttachment[] ticketAttachmentSet;
    private String body;
    private String ticketId;
    private String submittedBy;
    private String timeCreated;

    public TicketAttachment[] getTicketAttachmentSet() {
        return ticketAttachmentSet;
    }

    public void setTicketAttachmentSet( TicketAttachment[] ticketAttachmentSet ) {
        this.ticketAttachmentSet = ticketAttachmentSet;
    }

    public String getBody() {
        return body;
    }

    public void setBody( String body ) {
        this.body = body;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId( String ticketId ) {
        this.ticketId = ticketId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy( String submittedBy ) {
        this.submittedBy = submittedBy;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated( String timeCreated ) {
        this.timeCreated = timeCreated;
    }

}