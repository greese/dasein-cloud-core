package org.dasein.cloud.platform.support.model.options;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
public class TicketCreateAttachmentsOptions {

    private String ticketId;
    private List<TicketAttachmentDataOptions> attachments = new ArrayList<TicketAttachmentDataOptions>();

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId( String ticketId ) {
        this.ticketId = ticketId;
    }

    public List<TicketAttachmentDataOptions> getAttachments() {
        return attachments;
    }

    public void setAttachments( List<TicketAttachmentDataOptions> attachments ) {
        this.attachments = attachments;
    }

}
