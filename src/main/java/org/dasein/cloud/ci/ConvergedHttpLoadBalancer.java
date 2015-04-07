package org.dasein.cloud.ci;

import java.math.BigInteger;
import java.util.Map;
import javax.annotation.Nonnull;
import org.dasein.cloud.Taggable;

public class ConvergedHttpLoadBalancer implements Taggable {
    private String selfLink = null;
    private BigInteger id;
    private String name = null;
    private String description = null;
    private String creationTimestamp = null;
    private String urlMap = null;

    private ConvergedHttpLoadBalancer() { }

    static public @Nonnull ConvergedHttpLoadBalancer getInstance(@Nonnull BigInteger id, @Nonnull String name, @Nonnull String description, @Nonnull String creationTimestamp, @Nonnull String urlMap) {
        ConvergedHttpLoadBalancer convergedHttpLoadBalancer = new ConvergedHttpLoadBalancer();
        convergedHttpLoadBalancer.id = id;
        convergedHttpLoadBalancer.name = name;
        convergedHttpLoadBalancer.description = description;
        convergedHttpLoadBalancer.creationTimestamp = creationTimestamp;
        convergedHttpLoadBalancer.urlMap = urlMap;

        return convergedHttpLoadBalancer;
    }

    public ConvergedHttpLoadBalancer withSelfLink(@Nonnull String selfLink) {
        this.selfLink = selfLink;
        return this;
    }

    @Override
    public Map<String, String> getTags() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTag(String key, String value) {
        // TODO Auto-generated method stub

    }

    public Object getCurrentState() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getUrlMap() {
        return urlMap;
    }

    public String getSelfLink() {
        return selfLink;
    }
}
