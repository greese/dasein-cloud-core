package org.dasein.cloud.platform.support.model;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
public class TicketService {

    private TicketCategory[] categories;
    private String code;
    private String name;

    public TicketCategory[] getCategories() {
        return categories;
    }

    public void setCategories( TicketCategory[] categories ) {
        this.categories = categories;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}