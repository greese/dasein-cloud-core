package org.dasein.cloud.resource.model;

import java.util.Collection;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
public class ReplyWithCcEmails {

    private Collection<Reply> reply;
    private Collection<String> ccEmailAddresses;

    public ReplyWithCcEmails(Collection<Reply> reply, Collection<String> ccEmailAddresses) {
        this.reply = reply;
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public Collection<Reply> getReply() {
        return reply;
    }

    public void setReply(Collection<Reply> reply) {
        this.reply = reply;
    }
    
    public Collection<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses(Collection<String> ccEmailAddresses) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

}
