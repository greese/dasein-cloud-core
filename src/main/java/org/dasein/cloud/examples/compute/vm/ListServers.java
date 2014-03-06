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
