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
package org.dasein.cloud.examples.compute.vm;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.ComputeServices;
import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.examples.ProviderLoader;

/**
 * Example for verifying a services supports virtual machine resources and listing out those resources.
 * <p>Created by George Reese: 10/3/12 12:16 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class ListServers {
    static public void main(String ... args) throws Exception {
        ProviderLoader loader = new ProviderLoader();

        ListServers lister = new ListServers(loader.getConfiguredProvider());

        try {
            lister.list();
        }
        finally {
            lister.provider.close();
        }
    }

    private CloudProvider provider;

    public ListServers(CloudProvider provider) { this.provider = provider; }

    public void list() {
        // see if the cloud provider has any compute services
        ComputeServices compute = provider.getComputeServices();

        if( compute == null ) {
            System.out.println(provider.getCloudName() + " does not support any compute services.");
        }
        else {
            // see if it specifically supports virtual machines
            VirtualMachineSupport vmSupport = compute.getVirtualMachineSupport();

            if( vmSupport == null ) {
                System.out.println(provider.getCloudName() + " does not support virtual machines.");
            }
            else {
                // enumerate the VMs
                try {
                    int count = 0;

                    System.out.println("Virtual machines in " + provider.getCloudName() + ":");
                    for( VirtualMachine vm : vmSupport.listVirtualMachines() ) {
                        count++;
                        System.out.println("\t" + vm.getName() + "[" + vm.getProviderVirtualMachineId() + "] (" + vm.getCurrentState() + ")");
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
