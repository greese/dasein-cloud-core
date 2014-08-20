package org.dasein.cloud.resource.model;

import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
public class ReplyWithCcEmails {

    private List<Reply> reply;
    private List<String> ccEmailAddresses;

    public ReplyWithCcEmails(List<Reply> reply, List<String> ccEmailAddresses) {
        this.reply = reply;
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public List<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses(List<String> ccEmailAddresses) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

}
