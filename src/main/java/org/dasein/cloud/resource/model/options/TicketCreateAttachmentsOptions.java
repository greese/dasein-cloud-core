package org.dasein.cloud.resource.model.options;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
public class TicketCreateAttachmentsOptions {

    private String ticketId;
    private List<TicketAttachmentData> attachments = new ArrayList<TicketAttachmentData>();

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public List<TicketAttachmentData> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TicketAttachmentData> attachments) {
        this.attachments = attachments;
    }

}
