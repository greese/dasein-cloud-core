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
import org.dasein.cloud.Requirement;
import org.dasein.cloud.compute.*;
import org.dasein.cloud.examples.ProviderLoader;
import org.dasein.cloud.identity.IdentityServices;
import org.dasein.cloud.identity.SSHKeypair;
import org.dasein.cloud.identity.ShellKeySupport;
import org.dasein.cloud.network.NetworkServices;
import org.dasein.cloud.network.Subnet;
import org.dasein.cloud.network.SubnetState;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.network.VLANState;
import org.dasein.cloud.network.VLANSupport;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * Example for launching a virtual machine in any cloud. This example identifies all minimum requirements for launching
 * a virtual machine in your target cloud and executes that launch. You may, of course, add on extra launch functionality
 * in your own code. This command expects two arguments:
 * <ol>
 *     <li>hostName</li>
 *     <li>friendlyName</li>
 * </ol>
 * <p>
 *     Note that this might sometimes fail in some clouds because it's particularly dumb about looking for a product,
 *     architecture, machine image combo. In most real world examples, you would be specifying those from user
 *     input or smarter algorithms rather than just random combos.
 * </p>
 * <p>Created by George Reese: 10/3/12 12:16 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class LaunchServer {
    static public void main(String ... args) throws Exception {
        ProviderLoader loader = new ProviderLoader();

        LaunchServer launcher = new LaunchServer(loader.getConfiguredProvider());

        try {
            launcher.launch(args[0], args.length < 2 ? args[0] : args[1]);
        }
        finally {
            launcher.provider.close();
        }
    }

    private CloudProvider provider;

    public LaunchServer(@Nonnull CloudProvider provider) { this.provider = provider; }

    public void launch(@Nonnull String hostName, @Nonnull String friendlyName) {
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
                // launch a vm
                try {
                    VirtualMachineProduct product = null;
                    Architecture targetArchitecture = null;

                    for( Architecture architecture : vmSupport.getCapabilities().listSupportedArchitectures() ) {
                        Iterator<VirtualMachineProduct> supported = vmSupport.listProducts(architecture).iterator();

                        if( supported.hasNext() ) {
                            product = supported.next();
                            targetArchitecture = architecture;
                            break;
                        }
                    }
                    if( product == null ) {
                        System.err.println("Unable to identify a product to use");
                        return;
                    }
                    MachineImageSupport imgSupport = compute.getImageSupport();

                    if( imgSupport == null ) {
                        System.err.println("This cloud doesn't support machine images, so launching virtual machines is impossible");
                        return;
                    }
                    Platform platform = Platform.UNKNOWN;
                    String machineImageId = null;

                    for( MachineImage image : imgSupport.listImages(ImageFilterOptions.getInstance(ImageClass.MACHINE)) ) {
                        if( image.getCurrentState().equals(MachineImageState.ACTIVE) && image.getArchitecture().equals(targetArchitecture)) {
                            machineImageId = image.getProviderMachineImageId();
                            platform = image.getPlatform();
                            break;
                        }
                    }
                    if( machineImageId == null ) {
                        System.err.println("No active machine images exist for " + targetArchitecture);
                        return;
                    }
                    VMLaunchOptions options = VMLaunchOptions.getInstance(product.getProviderProductId(), machineImageId, hostName, friendlyName, friendlyName);

                    if( vmSupport.getCapabilities().identifyShellKeyRequirement(platform).equals(Requirement.REQUIRED) ) {
                        // you must specify an SSH key when launching the VM
                        // we'll look one up
                        IdentityServices identity = provider.getIdentityServices();

                        if( identity == null ) {
                            System.err.println("No identity services exist, but shell keys are required.");
                            return;
                        }
                        ShellKeySupport keySupport = identity.getShellKeySupport();

                        if( keySupport == null ) {
                            System.err.println("No shell key support exists, but shell keys are required.");
                            return;
                        }
                        Iterator<SSHKeypair> keys = keySupport.list().iterator();
                        String keyId = null;

                        if( keys.hasNext() ) {
                            keyId = keys.next().getProviderKeypairId();
                        }
                        if( keyId == null ) {
                            // no keypair yet exists, so we'll create one
                            if( keySupport.getKeyImportSupport().equals(Requirement.REQUIRED) ) {
                                // hmm, this cloud doesn't create them for you; it requires you to import them
                                // importing keys is beyond the scope of this example
                                System.err.println("This example cannot import key pairs and thus won't work against " + provider.getCloudName() + ".");
                                return;
                            }
                            // create the sample keypair
                            keyId = keySupport.createKeypair("dsnex" + System.currentTimeMillis()).getProviderKeypairId();
                        }
                        if( keyId != null ) {
                            options.withBoostrapKey(keyId);
                        }
                    }
                    if( vmSupport.getCapabilities().identifyPasswordRequirement(platform).equals(Requirement.REQUIRED) ) {
                        // you must specify a password when launching a VM
                        options.withBootstrapUser("dsnexample", "pw" + System.currentTimeMillis());
                    }
                    if( vmSupport.getCapabilities().identifyRootVolumeRequirement().equals(Requirement.REQUIRED) ) {
                        // let's look for the product with the smallest volume size
                        VolumeSupport volumeSupport = compute.getVolumeSupport();

                        if( volumeSupport == null ) {
                            System.err.println("A root volume product definition is required, but no volume support exists.");
                            return;
                        }
                        boolean findSmallest = volumeSupport.isVolumeSizeDeterminedByProduct();
                        VolumeProduct vp = null;
                        long vpSize = 0L;

                        for( VolumeProduct prd : volumeSupport.listVolumeProducts() ) {
                            Storage<Gigabyte> size = prd.getVolumeSize();

                            if( vp == null || (size != null && size.getQuantity().longValue() > 0L && prd.getVolumeSize().getQuantity().longValue() < vpSize) ) {
                                vp = prd;
                                size = vp.getVolumeSize();
                                if( size != null ) {
                                    vpSize = size.getQuantity().longValue();
                                }
                                if( !findSmallest ) { // size is not included in the product definition
                                    break;
                                }
                            }
                        }
                        if( vp == null ) {
                            System.err.println("Unable to identify any volume products.");
                            return;
                        }
                        options.withRootVolumeProduct(vp.getProviderProductId());
                    }
                    if( vmSupport.getCapabilities().identifyVlanRequirement().equals(Requirement.REQUIRED) ) {
                        NetworkServices network = provider.getNetworkServices();

                        if( network == null ) {
                            System.err.println("No network services exist even though a VLAN is required for launching a VM.");
                            return;
                        }
                        VLANSupport vlanSupport = network.getVlanSupport();

                        if( vlanSupport == null ) {
                            System.err.println("No VLANs are supported in " + provider.getCloudName() + " event though a VLAN is required to launch a VM.");
                            return;
                        }
                        VLAN vlan = null;

                        for( VLAN v : vlanSupport.listVlans() ) {
                            if( v.getCurrentState().equals(VLANState.AVAILABLE) ) {
                                vlan = v;
                                break;
                            }
                        }
                        if( vlan == null ) {
                            System.err.println("VLAN support is required, but was not able to identify a VLAN in an available state");
                            return;
                        }
                        if( vlanSupport.getSubnetSupport().equals(Requirement.REQUIRED) ) {
                            Subnet subnet = null;

                            for( Subnet s : vlanSupport.listSubnets(vlan.getProviderVlanId()) ) {
                                if( s.getCurrentState().equals(SubnetState.AVAILABLE) ) {
                                    subnet = s;
                                }
                            }
                            if( subnet != null ) { // let's just hope it works if no active subnet exists, probably won't
                                options.inVlan(null, vlan.getProviderDataCenterId(), subnet.getProviderSubnetId());
                            }
                            options.inVlan(null, vlan.getProviderDataCenterId(), vlan.getProviderVlanId());
                        }
                        else {
                            options.inVlan(null, vlan.getProviderDataCenterId(), vlan.getProviderVlanId());
                        }
                    }
                    VirtualMachine vm = vmSupport.launch(options);

                    System.out.println("Launched: " + vm.getName() + "[" + vm.getProviderVirtualMachineId() + "] (" + vm.getCurrentState() + ")");
                    while( vm != null && vm.getCurrentState().equals(VmState.PENDING) ) {
                        System.out.print(".");
                        try { Thread.sleep(5000L); }
                        catch( InterruptedException ignore ) { }
                        vm = vmSupport.getVirtualMachine(vm.getProviderVirtualMachineId());
                    }
                    if( vm == null ) {
                        System.out.println("VM self-terminated before entering a usable state");
                    }
                    else {
                        System.out.println("Launch complete (" + vm.getCurrentState() + ")");
                    }
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
