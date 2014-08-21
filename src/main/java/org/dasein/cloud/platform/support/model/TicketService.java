package org.dasein.cloud.platform.support.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
public class TicketService {

    private List<TicketCategory> categories = new ArrayList<TicketCategory>();
    private String code;
    private String name;

    public List<TicketCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TicketCategory> categories) {
        this.categories = categories;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}