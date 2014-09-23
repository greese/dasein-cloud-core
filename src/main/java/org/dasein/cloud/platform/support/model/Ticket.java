package org.dasein.cloud.platform.support.model;

import org.dasein.cloud.platform.support.TicketSeverity;
import org.dasein.cloud.platform.support.TicketStatus;

public class Ticket {

    private String type;
    private String ticketId;
    private String categoryCode;
    private Iterable<String> ccEmailAddresses;
    private String displayId;
    private String language;
    private Iterable<TicketReply> recentReplies;
    private String serviceCode;
    private TicketSeverity severityCode;
    private TicketStatus status;
    private String subject;
    private String submittedBy;
    private String timeCreated;
    private boolean hasReplies = true;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId( String ticketId ) {
        this.ticketId = ticketId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode( String categoryCode ) {
        this.categoryCode = categoryCode;
    }

    public Iterable<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses( Iterable<String> ccEmailAddresses ) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId( String displayId ) {
        this.displayId = displayId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

    public Iterable<TicketReply> getRecentReplies() {
        return recentReplies;
    }

    public void setRecentReplies( Iterable<TicketReply> recentReplies ) {
        this.recentReplies = recentReplies;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode( String serviceCode ) {
        this.serviceCode = serviceCode;
    }

    public TicketSeverity getSeverityCode() {
        return severityCode;
    }

    public void setSeverityCode( TicketSeverity severityCode ) {
        this.severityCode = severityCode;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus( TicketStatus status ) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject( String subject ) {
        this.subject = subject;
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

    public boolean isHasReplies() {
        return hasReplies;
    }

    public void setHasReplies( boolean hasReplies ) {
        this.hasReplies = hasReplies;
    }
}
