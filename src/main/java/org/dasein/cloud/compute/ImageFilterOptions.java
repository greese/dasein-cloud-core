/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.compute;

import org.dasein.cloud.CloudProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Options for filtering machine images when querying the cloud provider. <b>Note:</b> How searching handles account numbers is
 * dependent on the nature of the search.
 * <p>Created by Cameron Stokes: 01/28/13 08:41 AM</p>
 * @author Cameron Stokes
 * @version 2013.04 initial version
 * @version 2013.04 renamed to ImageFilterOptions to be consistent with multi-image type naming
 * @since 2013.04
 */
public class ImageFilterOptions {

    /**
     * Constructs a filter for any kind of images
     * @return a simple filter for images that does no filtering unless other options are added
     */
    static public @Nonnull ImageFilterOptions getInstance() {
        return new ImageFilterOptions(null, false);
    }

    /**
     * Constructs a filter for any kind of images
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a simple filter for images that does no filtering unless other options are added
     */
    static public @Nonnull ImageFilterOptions getInstance(boolean matchesAny) {
        return new ImageFilterOptions(null, matchesAny);
    }

    /**
     * Constructs a filter for images of the specified image class
     * @param cls the desired image class
     * @return a filter for images that match the specified image class
     */
    static public @Nonnull ImageFilterOptions getInstance(@Nonnull ImageClass cls) {
        return new ImageFilterOptions(cls, false);
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param regex the regular expression on which to filter
     * @return a filter for images that match the specified regular expression
     */
    static public @Nonnull ImageFilterOptions getInstance(@Nonnull String regex) {
        ImageFilterOptions options = new ImageFilterOptions(null, false);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a regex filter on the specified regular expression.
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex the regular expression on which to filter
     * @return a filter for images that match the specified regular expression
     */
    static public @Nonnull ImageFilterOptions getInstance(boolean matchesAny, @Nonnull String regex) {
        ImageFilterOptions options = new ImageFilterOptions(null, matchesAny);

        options.regex = regex;
        return options;
    }

    /**
     * Constructs a filter for images of the specified image class
     * @param cls the desired image class
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @return a filter for images that match the specified image class
     */
    static public @Nonnull ImageFilterOptions getInstance(ImageClass cls, boolean matchesAny) {
        return new ImageFilterOptions(cls, matchesAny);
    }

    /**
     * Constructs a filter for images of the specified image class
     * @param cls the desired image class
     * @param matchesAny <code>true</code> if it is sufficient that just one of the criteria are matched, false if all are needed to be matched
     * @param regex the regular expression on which to filter
     * @return a filter for images that match the specified image class
     */
    static public @Nonnull ImageFilterOptions getInstance(ImageClass cls, boolean matchesAny, @Nonnull String regex) {
        ImageFilterOptions options = new ImageFilterOptions(cls, matchesAny);

        options.regex = regex;
        return options;
    }

    private String             accountNumber;
    private Architecture       architecture;
    private ImageClass         imageClass;
    private boolean            matchesAny;
    private Platform           platform;
    private String             regex;
    private Map<String,String> tags;
    private boolean            withAllRegions = false;

    private ImageFilterOptions(@Nullable ImageClass cls, boolean matchesAny) {
        imageClass = cls;
        this.matchesAny = matchesAny;
    }

    /**
     * See class documentation note for this class ({@link ImageFilterOptions}) for notes on the special handling
     * associated with an account number.
     * @return an account number on which filtering should be done, or <code>null</code> to not filter on account number
     */
    public @Nullable String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @return the architecture on which filtering should be done, or <code>null</code> to not filter on architecture
     */
    public @Nullable Architecture getArchitecture() {
        return architecture;
    }

    /**
     * @return the number of criteria on which this filter is operating
     */
    public int getCriteriaCount() {
        int count = 0;

        if( accountNumber != null ) {
            count++;
        }
        if( architecture != null ) {
            count++;
        }
        if( imageClass != null ) {
            count++;
        }
        if( platform != null ) {
            count++;
        }
        if( regex != null ) {
            count++;
        }
        if( tags != null && !tags.isEmpty() ) {
            count++;
        }
        return count;
    }

    /**
     * @return the image class on which filtering should be done, or <code>null</code> to not filter on image class
     */
    public @Nullable ImageClass getImageClass() {
        return imageClass;
    }

    /**
     * @return the platform on which filtering should be done, or <code>null</code> to not filter on platform
     */
    public @Nullable Platform getPlatform() {
        return platform;
    }

    /**
     * @return a regular expression to match against an image name, description, or tag values.
     */
    public @Nullable String getRegex() {
        return regex;
    }

    /**
     * @return the tags on which filtering should be done, or <code>null</code> if no filtering on tags is to be done
     */
    public @Nullable Map<String, String> getTags() {
        return tags;
    }

    /**
     * @return whether the query is to ignore the context region and return for all regions.
     */
    public boolean getWithAllRegions(){ return withAllRegions; }

    /**
     * Indicates whether there are any criteria associated with these options.
     * @return <code>true</code> if this filter options object has any criteria associated with it
     */
    public boolean hasCriteria() {
        return ((tags != null && !tags.isEmpty()) || regex != null || accountNumber != null || imageClass != null);
    }

    /**
     * Indicates whether these options can match a single criterion (<code>true</code>) or if all criteria must be
     * matched in order for the image to pass the filter (<code>false</code>).
     * @return whether matching any single criterion is sufficient to consider an image a match
     */
    public boolean isMatchesAny() {
        return matchesAny;
    }

    /**
     * Matches an image against the criteria in this set of filter options.
     * @param image the image to test
     * @return true if the image matches all criteria
     */
    public boolean matches(@Nonnull MachineImage image) {
        if( imageClass != null ) {
            if( !imageClass.equals(image.getImageClass()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( architecture != null ) {
            if( !architecture.equals(image.getArchitecture()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( platform != null ) {
            if( !platform.equals(image.getPlatform()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( accountNumber != null ) {
            if( !accountNumber.equals(image.getProviderOwnerId()) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        if( regex != null ) {
            boolean matches = (image.getName().matches(regex) || image.getDescription().matches(regex));

            if (!matches) {
                matches = image.getProviderMachineImageId().matches(regex);
            }

            if( !matches ) {
                for( Map.Entry<String,String> tag : image.getTags().entrySet() ) {
                    String value = tag.getValue();

                    if( value != null && value.matches(regex) ) {
                        matches = true;
                        break;
                    }
                }
            }
            if( !matches && !matchesAny ) {
                return false;
            }
            else if( matches && matchesAny ) {
                return true;
            }
        }
        if( tags != null && !tags.isEmpty() ) {
            if( !CloudProvider.matchesTags(image.getTags(), image.getName(), image.getDescription(), tags) ) {
                if( !matchesAny ) {
                    return false;
                }
            }
            else if( matchesAny ) {
                return true;
            }
        }
        return !matchesAny;
    }

    /**
     * Indicates that the criteria associated with this filter must match all set criteria.
     * @return this
     */
    public @Nonnull ImageFilterOptions matchingAll() {
        this.matchesAny = false;
        return this;
    }

    /**
     * Indicates that the criteria associated with this filter must match just one single criterion.
     * @return this
     */
    public @Nonnull ImageFilterOptions matchingAny() {
        this.matchesAny = true;
        return this;
    }

    /**
     * Adds a regex to the set of filtering options. This regular expression is a standard Java regular expression
     * matches against the image name, description, and meta-data tags.
     * @param regex the Java regular expression string to match against
     * @return this
     */
    public @Nonnull ImageFilterOptions matchingRegex(@Nonnull String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * Adds a platform to the set of filtering options.
     * @param platform the platform on which to filter
     * @return this
     */
    public @Nonnull ImageFilterOptions onPlatform(@Nonnull Platform platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Sets an account number to the options on which image filtering should be done.
     * @param accountNumber the account number to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withAccountNumber(@Nonnull String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public @Nonnull ImageFilterOptions withAllRegions(boolean isAllRegions){
        this.withAllRegions = isAllRegions;
        return this;
    }

    /**
     * Sets an architecture on which image filtering should be done.
     * @param architecture the architecture to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withArchitecture(@Nonnull Architecture architecture) {
        this.architecture = architecture;
        return this;
    }

    /**
     * Sets an image class on which filtering should be done.
     * @param imageClass the image class to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withImageClass(@Nonnull ImageClass imageClass) {
        this.imageClass = imageClass;
        return this;
    }

    /**
     * Sets the tags to filter against.
     * @param tags the tags to filter against
     * @return this
     */
    public @Nonnull ImageFilterOptions withTags(@Nonnull Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public @Nonnull String toString() {
        return ("[" + (matchesAny ? "Match ANY: " : "Match ALL: ") + "accountNumber=" + accountNumber + ",imageClass=" + imageClass + ",regex=" + regex + "]");
    }
}
