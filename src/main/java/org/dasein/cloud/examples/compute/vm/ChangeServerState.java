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

package org.dasein.cloud.examples.compute.vm;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.compute.*;
import org.dasein.cloud.examples.ProviderLoader;

import javax.annotation.Nonnull;

/**
 * Will alter the state of a target server to a new, specified state. This example checks if the state change
 * is supported for the underlying virtual machine and either provides the proper feedback or executes the change.
 * The arguments to this command are:
 * <ol>
 *     <li>vmId</li>
 *     <li>stateChange</li>
 * </ol>
 * Valid state changes are:
 * <ul>
 *     <li>terminate</li>
 *     <li>start</li>
 *     <li>stop</li>
 *     <li>pause</li>
 *     <li>unpause</li>
 *     <li>suspend</li>
 *     <li>resume</li>
 * </ul>
 * <p>Created by George Reese: 10/3/12 3:15 PM</p>
 * @author George Reese
 * @version 2012.09 initial version
 * @since 2012.09
 */
public class ChangeServerState {
    static public void main(String ... args) throws Exception {
        ProviderLoader loader = new ProviderLoader();

        ChangeServerState changer = new ChangeServerState(loader.getConfiguredProvider());

        try {
            changer.change(args[0], args[1]);
        }
        finally {
            changer.provider.close();
        }
    }

    private CloudProvider provider;

    public ChangeServerState(@Nonnull CloudProvider provider) { this.provider = provider; }

    public void change(@Nonnull String vmId, @Nonnull String newState) {
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
                // find the vm and change its state
                try {
                    VirtualMachine vm = vmSupport.getVirtualMachine(vmId);

                    if( vm == null ) {
                        System.err.println("No such VM: " + vmId);
                        return;
                    }
                    VirtualMachineCapabilities capabilities = vmSupport.getCapabilities();
                    VmState currentState = vm.getCurrentState();
                    VmState targetState = null;

                    if( newState.equalsIgnoreCase("terminate") || newState.equalsIgnoreCase("terminated")) {
                        if( capabilities.canTerminate(vm.getCurrentState()) ) {
                            targetState = VmState.TERMINATED;
                            if( currentState.equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Terminating " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.terminate(vmId);
                        }
                        else {
                            System.err.println("You cannot terminate a VM in the " + vm.getCurrentState() + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("stop") || newState.equalsIgnoreCase("stopped")) {
                        if( capabilities.canStop(vm.getCurrentState()) ) {
                            targetState = VmState.STOPPED;
                            if( currentState.equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Stopping " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.stop(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support stopping of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("start") ) {
                        if( capabilities.canStart(currentState) ) {
                            targetState = VmState.RUNNING;
                            if( vm.getCurrentState().equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Starting " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.start(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support starting of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("pause") || newState.equalsIgnoreCase("paused")) {
                        if( capabilities.canPause(currentState) ) {
                            targetState = VmState.PAUSED;
                            if( vm.getCurrentState().equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Pausing " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.pause(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support pausing of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("unpause") ) {
                        if( capabilities.canUnpause(currentState) ) {
                            targetState = VmState.RUNNING;
                            if( vm.getCurrentState().equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Unpausing " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.unpause(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support unpausing of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("suspend") || newState.equalsIgnoreCase("suspended")) {
                        if( capabilities.canSuspend(currentState) ) {
                            targetState = VmState.SUSPENDED;
                            if( vm.getCurrentState().equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Suspending " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.suspend(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support suspending of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("resume") ) {
                        if( capabilities.canResume(currentState) ) {
                            targetState = VmState.RUNNING;
                            if( vm.getCurrentState().equals(targetState) ) {
                                System.err.println("VM is already " + targetState);
                                return;
                            }
                            System.out.print("Resuming " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.resume(vmId);
                        }
                        else {
                            System.err.println("Cloud does not support resuming of virtual machines in the " + currentState + " state");
                        }
                    }
                    else if( newState.equalsIgnoreCase("running") ) {
                        targetState = VmState.RUNNING;
                        if( currentState.equals(targetState) ) {
                            System.err.println("VM is already " + targetState);
                            return;
                        }
                        if( currentState.equals(VmState.PAUSED) ) {
                            if( !capabilities.canUnpause(currentState) ) {
                                System.err.println("Cloud does not support unpausing of virtual machines in the " + currentState + " state");
                            }
                            System.out.print("Unpausing " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.unpause(vmId);
                        }
                        else if( currentState.equals(VmState.STOPPED) ) {
                            if( !capabilities.canStart(currentState) ) {
                                System.err.println("Cloud does not support starting of virtual machines in the " + currentState + " state");
                            }
                            System.out.print("Starting " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.start(vmId);
                        }
                        else if( currentState.equals(VmState.SUSPENDED) ) {
                            if( !capabilities.canResume(currentState) ) {
                                System.err.println("Cloud does not support suspend/resume of virtual machines.");
                            }
                            System.out.print("Resuming " + vm.getProviderVirtualMachineId() + "...");
                            vmSupport.resume(vmId);
                        }
                        else {
                            System.out.println("Can't get from " + vm.getCurrentState() + " to " + targetState + ".");
                            return;
                        }
                    }
                    while( vm != null && !vm.getCurrentState().equals(targetState) ) {
                        System.out.print(".");
                        try { Thread.sleep(5000L); }
                        catch( InterruptedException ignore ) { }
                        vm = vmSupport.getVirtualMachine(vmId);
                    }
                    if( vm == null && !VmState.TERMINATED.equals(targetState) ) {
                        System.err.println("Virtual machine was unexpectedly terminated.");
                    }
                    else {
                        System.out.println("Current state: " + (vm == null ? VmState.TERMINATED : vm.getCurrentState()));
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
