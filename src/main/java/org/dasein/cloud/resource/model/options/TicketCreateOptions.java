package org.dasein.cloud.resource.model.options;

import java.util.Collection;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
public class TicketCreateOptions {

    String attachmentSetId;
    String categoryCode;
    Collection<String> ccEmailAddresses;
    String communicationBody;
    String issueType;
    String language;
    String serviceCode;
    String severityCode;
    String subject;

    public String getAttachmentSetId() {
        return attachmentSetId;
    }

    public void setAttachmentSetId(String attachmentSetId) {
        this.attachmentSetId = attachmentSetId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Collection<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses(Collection<String> ccEmailAddresses) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public String getCommunicationBody() {
        return communicationBody;
    }

    public void setCommunicationBody(String communicationBody) {
        this.communicationBody = communicationBody;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSeverityCode() {
        return severityCode;
    }

    public void setSeverityCode(String severityCode) {
        this.severityCode = severityCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
