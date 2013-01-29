package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Options for filtering machine images when querying the cloud provider.
 * <p>Created by Cameron Stokes: 01/28/13 08:41 AM</p>
 *
 * @author Cameron Stokes
 */
public class MachineImageFilterOptions {

    private MachineImageFilterOptions() {}

    static public @Nonnull MachineImageFilterOptions getInstance() {
        return new MachineImageFilterOptions();
    }

    String accountNumber;
    private ImageClass imageClass;
    private Map<String,String> tags;

    public String getAccountNumber() {
        return accountNumber;
    }

    public ImageClass getImageClass() {
        return imageClass;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public @Nonnull MachineImageFilterOptions withAccountNumber(@Nonnull String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public @Nonnull MachineImageFilterOptions withImageClass(@Nonnull ImageClass imageClass) {
        this.imageClass = imageClass;
        return this;
    }

    public @Nonnull MachineImageFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
