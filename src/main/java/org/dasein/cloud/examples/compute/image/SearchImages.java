/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

package org.dasein.cloud.examples.compute.image;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.compute.Platform;
import org.dasein.cloud.examples.ProviderLoader;

import javax.annotation.Nullable;

/**
 * Example showing how to search a public image library like the AWS image library using Dasein Cloud. You can call this
 * command with one, two, or three arguments:
 * <ol>
 *     <li>keyword or 'none' indicating no keyword preference</li>
 *     <li>platform or 'none' indicating no platform choice</li>
 *     <li>architecture or 'none' indicating no architecture choice</li>
 * </ol>
 * <p>
 * The platform and architecture values should be values from the {@link org.dasein.cloud.compute.Platform} and
 * {@link org.dasein.cloud.compute.Architecture} enums, respectively.
 * </p>
 * <p>Created by George Reese: 10/3/12 6:57 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class SearchImages {
    static public void main(String ... args) throws Exception {
        ProviderLoader loader = new ProviderLoader();

        SearchImages searcher = new SearchImages(loader.getConfiguredProvider());

        try {
            Architecture architecture = null;
            Platform platform = null;
            String keyword = null;

            if( args.length > 0 ) {
                keyword = args[0];
                if( keyword.equalsIgnoreCase("none") ) {
                    keyword = null;
                }
                if( args.length > 1 ) {
                    if( !args[1].equalsIgnoreCase("none") ) {
                        try { platform = Platform.valueOf(args[1].toUpperCase()); }
                        catch( Throwable ignore ) { }
                    }
                    if( args.length > 2 ) {
                        if( !args[2].equalsIgnoreCase("none") ) {
                            try { architecture = Architecture.valueOf(args[2].toUpperCase()); }
                            catch( Throwable ignore ) { }
                        }
                    }
                }
            }
            searcher.list(keyword, platform, architecture);
        }
        finally {
            searcher.provider.close();
        }
    }

    private CloudProvider provider;

    public SearchImages(CloudProvider provider) { this.provider = provider; }

    public void list(@Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture) {
        // see if the cloud provider has any compute services
        ComputeServices compute = provider.getComputeServices();

        if( compute == null ) {
            System.out.println(provider.getCloudName() + " does not support any compute services.");
        }
        else {
            // see if it specifically supports machine images
            MachineImageSupport imgSupport = compute.getImageSupport();

            if( imgSupport == null ) {
                System.out.println(provider.getCloudName() + " does not support machine images/templates.");
            }
            else {
                // search the machine images
                try {
                    int count = 0;

                    System.out.println("Machine images in " + provider.getCloudName() + " matching your terms:");
                    for( MachineImage img : imgSupport.searchMachineImages(keyword, platform, architecture) ) {
                        count++;
                        System.out.println("\t" + img.getName() + "[" + img.getProviderMachineImageId() + "] (" + img.getCurrentState() + ")");
                    }
                    System.out.println("Total: " + count);
                }
                catch( CloudException e ) {
                    System.err.println("An error occurred with the cloud provider: " + e.getMessage());
                    e.printStackTrace();
                }
                catch( InternalException e ) {
                    System.err.println("An error occurred inside Dasein Cloud: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
