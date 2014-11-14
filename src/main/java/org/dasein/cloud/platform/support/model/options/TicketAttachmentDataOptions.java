package org.dasein.cloud.platform.support.model.options;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
public class TicketAttachmentDataOptions {

    private byte[] data;
    private String fileName;

    public byte[] getData() {
        return data;
    }

    public void setData( byte[] data ) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }

}
