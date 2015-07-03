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

package org.dasein.cloud.identity;

import org.dasein.cloud.AccessControlledService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Support for identifying and managing identities at the cloud provider for service like AWS IAM.
 * @author George Reese (george.reese@imaginary.com)
 * @author Stas Maksimov (stas.maksimov@enstratius.com)
 * @since 2012.02
 * @version 2012.02
 * @version 2013.04 added methods to remove policies
 * @version 2015.09 capabilities extracted to a separate class, where console URL has been added,
 * renamed saveX methods to modifyX for consistency
 */
public interface IdentityAndAccessSupport extends AccessControlledService {
    @SuppressWarnings("unused") static final ServiceAction ANY                 = new ServiceAction("IAM:ANY");

    @SuppressWarnings("unused") static final ServiceAction ADD_GROUP_ACCESS    = new ServiceAction("IAM:ADD_GROUP_ACCESS");
    @SuppressWarnings("unused") static final ServiceAction ADD_USER_ACCESS     = new ServiceAction("IAM:ADD_USER_ACCESS");
    @SuppressWarnings("unused") static final ServiceAction CREATE_USER         = new ServiceAction("IAM:CREATE_USER");
    @SuppressWarnings("unused") static final ServiceAction CREATE_GROUP        = new ServiceAction("IAM:CREATE_GROUP");
    @SuppressWarnings("unused") static final ServiceAction DISABLE_API         = new ServiceAction("IAM:DISABLE_API");
    @SuppressWarnings("unused") static final ServiceAction DISABLE_CONSOLE     = new ServiceAction("IAM:DISABLE_CONSOLE");
    @SuppressWarnings("unused") static final ServiceAction DROP_FROM_GROUP     = new ServiceAction("IAM:DROP_FROM_GROUP");
    @SuppressWarnings("unused") static final ServiceAction ENABLE_API          = new ServiceAction("IAM:ENABLE_API");
    @SuppressWarnings("unused") static final ServiceAction ENABLE_CONSOLE      = new ServiceAction("IAM:ENABLE_CONSOLE");
    @SuppressWarnings("unused") static final ServiceAction GET_ACCESS_KEY      = new ServiceAction("IAM:GET_ACCESS_KEY");
    @SuppressWarnings("unused") static final ServiceAction GET_GROUP           = new ServiceAction("IAM:GET_GROUP");
    @SuppressWarnings("unused") static final ServiceAction GET_GROUP_POLICY    = new ServiceAction("IAM:GET_GROUP_POLICY");
    @SuppressWarnings("unused") static final ServiceAction GET_USER            = new ServiceAction("IAM:GET_USER");
    @SuppressWarnings("unused") static final ServiceAction GET_USER_POLICY     = new ServiceAction("IAM:GET_USER_POLICY");
    @SuppressWarnings("unused") static final ServiceAction JOIN_GROUP          = new ServiceAction("IAM:JOIN_GROUP");
    @SuppressWarnings("unused") static final ServiceAction LIST_ACCESS_KEY     = new ServiceAction("IAM:LIST_ACCESS_KEY");
    @SuppressWarnings("unused") static final ServiceAction LIST_GROUP          = new ServiceAction("IAM:LIST_GROUP");
    @SuppressWarnings("unused") static final ServiceAction LIST_USER           = new ServiceAction("IAM:LIST_USER");
    @SuppressWarnings("unused") static final ServiceAction REMOVE_GROUP        = new ServiceAction("IAM:REMOVE_GROUP");
    @SuppressWarnings("unused") static final ServiceAction REMOVE_GROUP_ACCESS = new ServiceAction("IAM:REMOVE_GROUP_ACCESS");
    @SuppressWarnings("unused") static final ServiceAction REMOVE_USER         = new ServiceAction("IAM:REMOVE_USER");
    @SuppressWarnings("unused") static final ServiceAction REMOVE_USER_ACCESS  = new ServiceAction("IAM:REMOVE_USER_ACCESS");
    @SuppressWarnings("unused") static final ServiceAction UPDATE_GROUP        = new ServiceAction("IAM:UPDATE_GROUP");
    @SuppressWarnings("unused") static final ServiceAction UPDATE_USER         = new ServiceAction("IAM:UPDATE_USER");

    /**
     * Provides access to meta-data about identity and access capabilities in the current region of this cloud.
     * @return a description of the features supported by this region of this cloud
     * @throws InternalException an error occurred within the Dasein Cloud API implementation
     * @throws CloudException an error occurred within the cloud provider
     */
    @Nonnull IdentityAndAccessCapabilities getCapabilities() throws CloudException, InternalException;

    /**
     * Adds an existing user to the specified existing groups.
     * @param providerUserId the unique cloud provider ID for the user to add
     * @param providerGroupIds one or more unique cloud provider IDs for the groups to be added to
     * @throws CloudException an error occurred with the cloud provider adding the user
     * @throws InternalException an error occurred within the Dasein Cloud implementation while adding the user
     */
    void addUserToGroups(@Nonnull String providerUserId, @Nonnull String ... providerGroupIds) throws CloudException, InternalException;

    /**
     * Creates a new group with the cloud provider belonging in the specified path.
     * @param groupName the name of the new group
     * @param path the parent path into which the group is placed
     * @param asAdminGroup if the group should be granted full admin privileges (see {@link IdentityAndAccessCapabilities#supportsAccessControls()})
     * @return the newly created group
     * @throws CloudException an error occurred with the cloud provider while creating the group
     * @throws InternalException an error occurred within the Dasein Cloud implementation while creating the group
     */
    @Nonnull CloudGroup createGroup(@Nonnull String groupName, @Nullable String path, boolean asAdminGroup) throws CloudException, InternalException;

    /**
     * Creates a new user with the cloud provider belonging in the specified path.
     * @param userName the name of the new user to create
     * @param path the parent path into which the user is placed
     * @param autoJoinGroupIds any groups the user should automatically be joined to
     * @return the newly created cloud user (even if the auto-joins failed!)
     * @throws CloudException an error occurred creating the user with the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation while creating the user
     */
    @Nonnull CloudUser createUser(@Nonnull String userName, @Nullable String path, @Nullable String ... autoJoinGroupIds) throws CloudException, InternalException;

    /**
     * Enables the specified user to access the cloud API via their own API keys.
     * @param providerUserId the user to grant API access to
     * @return the access keys for the user
     * @throws CloudException an error occurred within the cloud provider enabling API access
     * @throws InternalException an error occurred within the Dasein Cloud implementation while enabling access
     */
    @Nonnull AccessKey enableAPIAccess(@Nonnull String providerUserId) throws CloudException, InternalException;

    /**
     * Enables console access for the specified user with the specified password.
     * @param providerUserId the cloud provider ID of the user to add console access for
     * @param password the password to use for access to the console 
     * @throws CloudException an error occurred in the cloud provider enabling console access
     * @throws InternalException an error occurred within the Dasein Cloud implementation while enabling console access
     */
    void enableConsoleAccess(@Nonnull String providerUserId, @Nonnull byte[] password) throws CloudException, InternalException;

    /**
     * Provides a reference to the specified group.
     * @param providerGroupId the unique ID of the target group
     * @return the specified group if it exists
     * @throws CloudException an error occurred in the cloud provider fetching the specified group
     * @throws InternalException an error occurred in the Dasein Cloud implementation while fetching the specified group
     */
    @Nullable CloudGroup getGroup(@Nonnull String providerGroupId) throws CloudException, InternalException;

    /**
     * Provides a reference to the specified user.
     * @param providerUserId the unique ID of the target user
     * @return the specified user if it exists
     * @throws CloudException an error occurred in the cloud provider fetching the specified user
     * @throws InternalException an error occurred in the Dasein Cloud implementation while fetching the specified user
     */
    @Nullable CloudUser getUser(@Nonnull String providerUserId) throws CloudException, InternalException;
    
    /**
     * @return true if this cloud supports IdM features in the current region and this account has access to them
     * @throws CloudException an error occurred in the cloud provider determining subscription status
     * @throws InternalException an error occurred within the Dasein Cloud implementation while determining subscription status
     */
    boolean isSubscribed() throws CloudException, InternalException;

    /**
     * Lists all groups or all groups with the specified path base
     * @param pathBase the optional base path to search in
     * @return all matching groups in the cloud provider
     * @throws CloudException an error occurred listing groups from the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation processing the request
     */
    @Nonnull Iterable<CloudGroup> listGroups(@Nullable String pathBase) throws CloudException, InternalException;

    /**
     * Lists all groups to which a specified user belongs.
     * @param providerUserId the user to search on
     * @return all groups in which the specified user belongs
     * @throws CloudException an error occurred with the cloud provider searching for the specified user's groups
     * @throws InternalException an error occurred within the Dasein Cloud implementation executing the search
     */
    @Nonnull Iterable<CloudGroup> listGroupsForUser(@Nonnull String providerUserId) throws CloudException, InternalException;

    /**
     * Lists the policies attached to a specific group.
     * @param providerGroupId the unique ID of the group against which the policy search will be made
     * @return the list of matching policies
     * @throws CloudException an error occurred with the cloud provider listing the group policies
     * @throws InternalException an error occurred within the Dasein Cloud implementation executing the listing
     */
    @Nonnull Iterable<CloudPolicy> listPoliciesForGroup(@Nonnull String providerGroupId) throws CloudException, InternalException;

    /**
     * Lists the policies attached to a specific user.
     * @param providerUserId the unique ID of the ser against which the policy search will be made
     * @return the list of matching policies
     * @throws CloudException an error occurred with the cloud provider listing the user policies
     * @throws InternalException an error occurred within the Dasein Cloud implementation executing the listing
     */
    @Nonnull Iterable<CloudPolicy> listPoliciesForUser(@Nonnull String providerUserId) throws CloudException, InternalException;

    /**
     * Lists all users belonging to the specified group.
     * @param inProviderGroupId the ID of the group in which to search
     * @return a list of all users belonging to the specified group
     * @throws CloudException an error occurred with the cloud provider while performing the search
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing the search
     */
    @Nonnull Iterable<CloudUser> listUsersInGroup(@Nonnull String inProviderGroupId) throws CloudException, InternalException;

    /**
     * Lists all users or all users within the specified path base
     * @param pathBase the optional base path to search in
     * @return all matching users
     * @throws CloudException an error occurred with the cloud provider while performing the search
     * @throws InternalException an error occurred within the Dasein Cloud implementation while performing the search
     */
    @Nonnull Iterable<CloudUser> listUsersInPath(@Nullable String pathBase) throws CloudException, InternalException;

    /**
     * Removes a previously created API access key associated with a user.
     * @param sharedKeyPart the shared part of the key to remove
     * @param providerUserId the user whose access should be removed, if any
     * @throws CloudException an error occurred in the cloud provider while removing the access key
     * @throws InternalException an error occurred within the Dasein Cloud implementation while removing the access key
     */
    void removeAccessKey(@Nonnull String sharedKeyPart, @Nullable String providerUserId) throws CloudException, InternalException;

    /**
     * Removes a user's login access to the cloud provider's console.
     * @param providerUserId the user whose access should be removed
     * @throws CloudException an error occurred within the cloud provider while removing the access
     * @throws InternalException an error occurred within the Dasein Cloud implementation while removing the console access
     */
    void removeConsoleAccess(@Nonnull String providerUserId) throws CloudException, InternalException;

    /**
     * Removes the specified group from the system. Some clouds may require the group to be empty prior to allowing
     * the deletion, while others may not. It's probably a good idea to delete only empty groups anyways.
     * @param providerGroupId the group to be removed
     * @throws CloudException an error occurred in the cloud provider (did you empty the group?) removing the group
     * @throws InternalException an error occurred within the Dasein Cloud implementation removing the group
     */
    void removeGroup(@Nonnull String providerGroupId) throws CloudException, InternalException;

    /**
     * Removes the specified group policy from the list of policies associated with this group
     * @param providerGroupId the group from which the policy is being removed
     * @param providerPolicyId the policy to be removed
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    void removeGroupPolicy(@Nonnull String providerGroupId, @Nonnull String providerPolicyId) throws CloudException, InternalException;

    /**
     * Removes the specified user from the cloud provider.
     * @param providerUserId the user to be removed
     * @throws CloudException an error occurred in the cloud provider removing the user
     * @throws InternalException an error occurred in the Dasein Cloud implementation removing the group
     */
    void removeUser(@Nonnull String providerUserId) throws CloudException, InternalException;

    /**
     * Removes the specified user's membership in the specified group.
     * @param providerUserId the user to be removed
     * @param providerGroupId the group from which the user is to be removed
     * @throws CloudException an error occurred in the cloud provider (did the user belong to the group?) removing the user from the group
     * @throws InternalException an error occurred within the Dasein Cloud implementation removing the user
     */
    void removeUserFromGroup(@Nonnull String providerUserId, @Nonnull String providerGroupId) throws CloudException, InternalException;

    /**
     * Removes the specified user policy from the list of policies associated with this user
     * @param providerUserId the user from whom the policy is being removed
     * @param providerPolicyId the policy to be removed
     * @throws CloudException an error occurred in the cloud provider
     * @throws InternalException an error occurred within the Dasein Cloud implementation
     */
    void removeUserPolicy(@Nonnull String providerUserId, @Nonnull String providerPolicyId) throws CloudException, InternalException;

    /**
     * Updates the specified group with new path or name values. If <code>null</code> is specified for any value, it
     * will remain unchanged.
     * @param providerGroupId the provider ID for the group to be modified
     * @param newGroupName the new name for the group (or <code>null</code> to remain unchanged)
     * @param newPath the new path for the group (or <code>null</code> to remain unchanged)
     * @throws CloudException an error occurred with the cloud provider (does the group exist?) updating the group
     * @throws InternalException an error occurred within the Dasein Cloud implementation updating the group
     */
    void modifyGroup(@Nonnull String providerGroupId, @Nullable String newGroupName, @Nullable String newPath) throws CloudException, InternalException;

    /**
     * Saves the specified permission for the specified group to the access control system of the cloud. For any
     * nullable parameter, <code>null</code> means that global application of the permission. For example,
     * a <code>null</code> action means the prmission applies to all actions against that service and resource.
     * @param providerGroupId the group ID of the group to which this policy should apply
     * @param name the name of the policy                        
     * @param permission the permission being granted or denied
     * @param action the action against which the permission applies
     * @param resourceId the resource ID against which the permission applies
     * @return the ID or IDs of the newly created policy (can result in multiple policies)
     * @throws CloudException an error occurred with the cloud provider applying the permission
     * @throws InternalException an error occurred within Dasein Cloud processing the request
     */
    @Nonnull String[] modifyGroupPolicy(@Nonnull String providerGroupId, @Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) throws CloudException, InternalException;

    /**
     * Saves the specified permission for the specified user to the access control system of the cloud. For any
     * nullable parameter, <code>null</code> means that global application of the permission. For example,
     * a <code>null</code> action means the prmission applies to all actions against that service and resource.
     * @param providerUserId the group ID of the user to which this policy should apply
     * @param name the name of the policy                      
     * @param permission the permission being granted or denied
     * @param action the action against which the permission applies
     * @param resourceId the resource ID against which the permission applies
     * @return the ID or IDs of the newly created policy (can result in multiple policies)
     * @throws CloudException an error occurred with the cloud provider applying the permission
     * @throws InternalException an error occurred within Dasein Cloud processing the request
     */
    @Nonnull String[] modifyUserPolicy(@Nonnull String providerUserId, @Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) throws CloudException, InternalException;

    /**
     * Updates the specified user with new path or user name values. If <code>null</code> is specified for any value,
     * it will remain unchanged.
     * @param providerUserId the provider ID for the user to be modified
     * @param newUserName the new name for the user (or <code>null</code> to remain unchanged)
     * @param newPath the new path for the user (or <code>null</code> to remain unchanged)
     * @throws CloudException an error occurred with the cloud provider (does the user exist?) updating the user
     * @throws InternalException an error occurred within the Dasein Cloud implementation updating the user
     */
    void modifyUser(@Nonnull String providerUserId, @Nullable String newUserName, @Nullable String newPath) throws CloudException, InternalException;

}
