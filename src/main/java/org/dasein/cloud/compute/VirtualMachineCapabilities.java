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

package org.dasein.cloud.compute;

import org.dasein.cloud.*;
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Describes the capabilities of a region within a cloud for a specific account.
 * <p>Created by George Reese: 2/27/14 3:01 PM</p>
 * @author George Reese
 * @version 2014.03 initial version
 * @since 2014.03
 */
public interface VirtualMachineCapabilities extends Capabilities {
    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#alterVirtualMachine(String, VMScalingOptions)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be altered, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canAlter(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#clone(String, String, String, String, boolean, String...)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be cloned, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canClone(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#pause(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be paused, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canPause(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#reboot(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be rebooted, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canReboot(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#resume(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be resumed, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canResume(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#start(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be started, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canStart(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#stop(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be stopped, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canStop(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#suspend(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be suspended, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canSuspend(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#terminate(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be terminated, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canTerminate(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Indicates whether or not a virtual machine in the specified state can be the target of
     * an {@link VirtualMachineSupport#unpause(String)} call.
     * @param fromState the state in which the theoretical virtual machine exists
     * @return true if such a virtual machine may be unpaused, false otherwise
     * @throws CloudException the cloud provider encountered an error while processing the request
     * @throws InternalException a Dasein Cloud error occurred while processing the request
     */
    public boolean canUnpause(@Nonnull VmState fromState) throws CloudException, InternalException;

    /**
     * Provides the maximum number of virtual machines that may be launched in this region for the current account.
     * @return the maximum number of launchable VMs or {@link Capabilities#LIMIT_UNLIMITED} for unlimited or {@link Capabilities#LIMIT_UNKNOWN}for unknown
     * @throws CloudException an error occurred fetching the limits from the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining the limits
     */
    public int getMaximumVirtualMachineCount() throws CloudException, InternalException;

    /**
     * Provides a number between 0 and 100 describing what percentage of the standard VM bill rate should be charged for
     * virtual machines in the specified state. 0 means that the VM incurs no charges while in the specified state, 100
     * means it incurs full charges, and a number in between indicates the percent discount that applies.
     * @param state the VM state being checked
     * @return the discount factor for VMs in the specified state
     * @throws CloudException an error occurred within the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     */
    public @Nonnegative int getCostFactor(@Nonnull VmState state) throws CloudException, InternalException;

    /**
     * Assists UIs by providing the cloud-specific term for a virtual machine in the cloud.
     * @param locale the locale for which the term should be translated
     * @return the provider-specific term for a virtual server
     */
    public @Nonnull String getProviderTermForVirtualMachine(@Nonnull Locale locale) throws CloudException, InternalException;

    /**
     * Describes the ways in which this cloud supports the vertical scaling of a virtual machine. A <code>null</code>
     * response means this cloud just doesn't support vertical scaling.
     * @return a description of how this cloud supports vertical scaling
     * @throws CloudException an error occurred in the cloud processing the request
     * @throws InternalException an internal error occurred processing the request
     */
    public @Nullable VMScalingCapabilities getVerticalScalingCapabilities() throws CloudException, InternalException;

    /**
     * Identifies the naming conventions that constrain how virtual machines may be named (friendly name) in this cloud.
     * @return naming conventions that constrain virtual machine naming
     * @throws CloudException an error occurred querying the cloud for naming constraints
     * @throws InternalException an error occurred assembling the naming constraints object
     */
    public @Nonnull NamingConstraints getVirtualMachineNamingConstraints() throws CloudException, InternalException;

    /**
     * Returns the visible scope of the Virtual Machine or null if not applicable for the specific cloud
     * @return the Visible Scope of the Virtual Machine
     */
    public @Nullable VisibleScope getVirtualMachineVisibleScope();

    /**
     * Returns the visible scope of the VM Product or null if not applicable for the specific cloud
     * @return the Visible Scope of the VM Product
     */
    public @Nullable VisibleScope getVirtualMachineProductVisibleScope();

    /**
     * Indicates whether the VM requires a Data Center to be specified upon launch
     * @return the requirements for data centers upon VM launch
     * @throws CloudException
     * @throws InternalException
     */
    public @Nonnull Requirement identifyDataCenterLaunchRequirement() throws CloudException, InternalException;

    /**
     * Identifies whether images of the specified image class are required for launching a VM. This method should
     * always return {@link Requirement#REQUIRED} when the image class chosen is {@link ImageClass#MACHINE}.
     * @param cls the desired image class
     * @return the requirements level of support for this image class
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyImageRequirement(@Nonnull ImageClass cls) throws CloudException, InternalException;

    /**
     * Indicates the degree to which specifying a user name and password at launch is required.
     * @param platform the platform for which password requirements are being sought
     * @return the requirements level for specifying a user name and password at launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyPasswordRequirement(Platform platform) throws CloudException, InternalException;

    /**
     * Indicates whether or not a root volume product must be specified when launching a virtual machine.
     * @return the requirements level for a root volume product
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyRootVolumeRequirement() throws CloudException, InternalException;

    /**
     * Indicates the degree to which specifying a shell key at launch is required.
     * @param platform the target platform for which you are testing
     * @return the requirements level for shell key support at launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyShellKeyRequirement(Platform platform) throws CloudException, InternalException;

    /**
     * Indicates the degree to which static IP addresses are required when launching a VM.
     * @return the requirements level for static IP on launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyStaticIPRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether a subnet is required to be specified when launching a VM into a VLAN
     * @return the requirements level for a subnet during launch
     * @throws CloudException
     * @throws InternalException
     */
    public @Nonnull Requirement identifySubnetRequirement() throws CloudException, InternalException;

    /**
     * Indicates whether or not specifying a VLAN in your VM launch options is required or optional.
     * @return the requirements level for a VLAN during launch
     * @throws CloudException an error occurred in the cloud identifying this requirement
     * @throws InternalException an error occurred within the Dasein Cloud implementation identifying this requirement
     */
    public @Nonnull Requirement identifyVlanRequirement() throws CloudException, InternalException;

    /**
     * Indicates that the ability to terminate the VM via API can be disabled.
     * @return true if the cloud supports the ability to prevent API termination
     * @throws CloudException an error occurred in the cloud while determining this capability
     * @throws InternalException an error occurred in the Dasein Cloud implementation determining this capability
     */
    public boolean isAPITerminationPreventable() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud provider supports basic analytics. Basic analytics are analytics
     * that are being gathered for every virtual machine without any intervention necessary to enable them. Extended
     * analytics implies basic analytics, so this method should always be true if {@link #isExtendedAnalyticsSupported()}
     * is true (even if there are, in fact, only extended analytics).
     * @return true if the cloud provider supports the gathering of extended analytics
     * @throws CloudException an error occurred in the cloud provider determining extended analytics support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining extended analytics support
     */
    public boolean isBasicAnalyticsSupported() throws CloudException, InternalException;

    /**
     * Indicates whether or not this cloud provider supports extended analytics. Extended analytics are analytics
     * that must be specifically enabled above and beyond any basic analytics the cloud provider is gathering.
     * @return true if the cloud provider supports the gathering of extended analytics
     * @throws CloudException an error occurred in the cloud provider determining extended analytics support
     * @throws InternalException an error occurred within the Dasein Cloud implementation determining extended analytics support
     */
    public boolean isExtendedAnalyticsSupported() throws CloudException, InternalException;

    /**
     * Indicates whether or not the cloud allows bootstrapping with user data.
     * @return true of user-data bootstrapping is supported
     * @throws CloudException an error occurred querying the cloud for this kind of support
     * @throws InternalException an error inside the Dasein Cloud implementation occurred determining support
     */
    public boolean isUserDataSupported() throws CloudException, InternalException;

    /**
     * Identifies what architectures are supported in this cloud.
     * @return a list of supported architectures
     * @throws InternalException an error occurred within the Dasein Cloud implementation calculating supported architectures
     * @throws CloudException an error occurred fetching the list of supported architectures from the cloud
     */
    public @Nonnull Iterable<Architecture> listSupportedArchitectures() throws InternalException, CloudException;

    /**
     * Indicates whether or not the spot virtual machines are supported by the cloud provider.
     * @return true if spot vms are supported
     * @throws InternalException
     * @throws CloudException
     */
    public boolean supportsSpotVirtualMachines() throws InternalException, CloudException;
}
