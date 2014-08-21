package org.dasein.cloud.platform.support.model;

/**
 * @author Eugene Yaroslavtsev
 * @since 14.08.2014
 */
public class TicketAttachment {

    private String attachmentId;
    private String fileName;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
