/**
 * ========= CONFIDENTIAL =========
 *
 * Copyright (C) 2012 enStratus Networks Inc - ALL RIGHTS RESERVED
 *
 * ====================================================================
 *  NOTICE: All information contained herein is, and remains the
 *  property of enStratus Networks Inc. The intellectual and technical
 *  concepts contained herein are proprietary to enStratus Networks Inc
 *  and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. Dissemination
 *  of this information or reproduction of this material is strictly
 *  forbidden unless prior written permission is obtained from
 *  enStratus Networks Inc.
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

