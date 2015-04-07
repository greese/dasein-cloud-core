package org.dasein.cloud.ci;

import javax.annotation.Nonnull;

public class ConvergedHttpLoadbalancerOptions {
    private String name;
    private String description;
    private String getUrlMap;
    private ConvergedHttpLoadbalancerOptions() { }

    static public @Nonnull ConvergedHttpLoadbalancerOptions getInstance(@Nonnull String name, @Nonnull String description, @Nonnull String getUrlMap) {
        ConvergedHttpLoadbalancerOptions options = new ConvergedHttpLoadbalancerOptions();
        options.name = name;
        options.description = description;
        options.getUrlMap = getUrlMap;

        return options;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlMap() {
        return getUrlMap;
    }
}
