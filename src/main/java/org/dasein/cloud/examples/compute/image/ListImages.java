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
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.examples.ProviderLoader;

import java.util.Locale;

/**
 * Example showing how to conditionally list templates/machine images in clouds that support them.
 * <p>Created by George Reese: 10/3/12 6:49 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class ListImages {
    static public void main(String ... args) throws Exception {
        ProviderLoader loader = new ProviderLoader();

        ListImages lister = new ListImages(loader.getConfiguredProvider());

        try {
            lister.list();
        }
        finally {
            lister.provider.close();
        }
    }

    private CloudProvider provider;

    public ListImages(CloudProvider provider) { this.provider = provider; }

    public void list() {
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
                System.out.println(provider.getCloudName() + " calls machine images \"" + imgSupport.getProviderTermForImage(Locale.getDefault()) + "\".");
                // enumerate the machine images
                try {
                    int count = 0;

                    System.out.println("Machine images in " + provider.getCloudName() + " (may be none in clouds like AWS unless you specifically own some):");
                    for( MachineImage img : imgSupport.listMachineImages() ) {
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

