package org.dasein.cloud.ci;

import javax.annotation.Nonnull;

public class ConvergedHttpLoadbalancerOptions {
    private ConvergedHttpLoadbalancerOptions() { }

    static public @Nonnull ConvergedHttpLoadbalancerOptions getInstance(@Nonnull String name, @Nonnull String description, @Nonnull String zone, @Nonnull int size, @Nonnull String instanceTemplate) {
        ConvergedHttpLoadbalancerOptions options = new ConvergedHttpLoadbalancerOptions();


        return options;
    }
}
