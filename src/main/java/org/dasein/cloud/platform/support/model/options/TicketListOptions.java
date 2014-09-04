package org.dasein.cloud.platform.support.model.options;

import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 14.08.2014
 */
public class TicketListOptions {

    String afterTime;
    String beforeTime;
    List<String> caseIdList;
    String displayId;
    Boolean includeCommunications;
    Boolean includeResolvedCases;
    String language;

    public String getAfterTime() {
        return afterTime;
    }

    public void setAfterTime( String afterTime ) {
        this.afterTime = afterTime;
    }

    public String getBeforeTime() {
        return beforeTime;
    }

    public void setBeforeTime( String beforeTime ) {
        this.beforeTime = beforeTime;
    }

    public List<String> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList( List<String> caseIdList ) {
        this.caseIdList = caseIdList;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId( String displayId ) {
        this.displayId = displayId;
    }

    public Boolean getIncludeCommunications() {
        return includeCommunications;
    }

    public void setIncludeCommunications( Boolean includeCommunications ) {
        this.includeCommunications = includeCommunications;
    }

    public Boolean getIncludeResolvedCases() {
        return includeResolvedCases;
    }

    public void setIncludeResolvedCases( Boolean includeResolvedCases ) {
        this.includeResolvedCases = includeResolvedCases;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

}
