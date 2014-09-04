package org.dasein.cloud.platform.support.model.options;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
public class TicketCreateReplyOptions {

    private String attachmentSetId;
    private String caseId;
    private String[] ccEmailAddresses;
    private String communicationBody;

    public String getAttachmentSetId() {
        return attachmentSetId;
    }

    public void setAttachmentSetId( String attachmentSetId ) {
        this.attachmentSetId = attachmentSetId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId( String caseId ) {
        this.caseId = caseId;
    }

    public String[] getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses( String[] ccEmailAddresses ) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public String getCommunicationBody() {
        return communicationBody;
    }

    public void setCommunicationBody( String communicationBody ) {
        this.communicationBody = communicationBody;
    }

}
