
package org.dasein.cloud.resource.model;

import java.util.List;

public class Reply {

    private List<Attachment> attachmentSet;
    private String body;
    private String ticketId;
    private String submittedBy;
    private String timeCreated;

    public List<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    public void setAttachmentSet(List<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

}
