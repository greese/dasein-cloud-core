package org.dasein.cloud.dc;

import java.util.List;

/**
 * User: daniellemayne
 * Date: 18/08/2014
 * Time: 12:18
 */
public class Folder {
    private String id;
    private String name;
    private FolderType type;
    private Folder parent;
    private List<Folder> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FolderType getType() {
        return type;
    }

    public void setType(FolderType type) {
        this.type = type;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public List<Folder> getChildren() {
        return children;
    }

    public void setChildren(List<Folder> children) {
        this.children = children;
    }
}
