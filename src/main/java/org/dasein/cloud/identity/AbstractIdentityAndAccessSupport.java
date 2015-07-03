package org.dasein.cloud.identity;

import org.dasein.cloud.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by stas on 18/06/15.
 */
public abstract class AbstractIdentityAndAccessSupport<T extends CloudProvider> extends AbstractProviderService<T> implements IdentityAndAccessSupport {

    protected AbstractIdentityAndAccessSupport(T provider) {
        super(provider);
    }

    @Nonnull
    @Override
    public Iterable<CloudGroup> listGroups(@Nullable String pathBase) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be listed in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<CloudGroup> listGroupsForUser(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be listed for user in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<CloudPolicy> listPoliciesForGroup(@Nonnull String providerGroupId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Policies cannot be listed for group in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<CloudPolicy> listPoliciesForUser(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Policies cannot be listed for user in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<CloudUser> listUsersInGroup(@Nonnull String inProviderGroupId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be listed for group in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public Iterable<CloudUser> listUsersInPath(@Nullable String pathBase) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be listed in path in " + getProvider().getCloudName());
    }

    @Override
    public void removeAccessKey(@Nonnull String sharedKeyPart, @Nullable String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Access keys cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void removeConsoleAccess(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Console access cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void removeGroup(@Nonnull String providerGroupId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void removeGroupPolicy(@Nonnull String providerGroupId, @Nonnull String providerPolicyId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Group policies cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void removeUser(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void removeUserFromGroup(@Nonnull String providerUserId, @Nonnull String providerGroupId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be removed from groups in " + getProvider().getCloudName());
    }

    @Override
    public void removeUserPolicy(@Nonnull String providerUserId, @Nonnull String providerPolicyId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("User policies cannot be removed in " + getProvider().getCloudName());
    }

    @Override
    public void modifyGroup(@Nonnull String providerGroupId, @Nullable String newGroupName, @Nullable String newPath) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be modified in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public String[] modifyGroupPolicy(@Nonnull String providerGroupId, @Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups policies cannot be modified in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public String[] modifyUserPolicy(@Nonnull String providerUserId, @Nonnull String name, @Nonnull CloudPermission permission, @Nullable ServiceAction action, @Nullable String resourceId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("User policies cannot be modified in " + getProvider().getCloudName());
    }

    @Override
    public void modifyUser(@Nonnull String providerUserId, @Nullable String newUserName, @Nullable String newPath) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be modified in " + getProvider().getCloudName());
    }

    @Override
    public void addUserToGroups(@Nonnull String providerUserId, @Nonnull String... providerGroupIds) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be added to groups in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public CloudGroup createGroup(@Nonnull String groupName, @Nullable String path, boolean asAdminGroup) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be created in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public CloudUser createUser(@Nonnull String userName, @Nullable String path, @Nullable String... autoJoinGroupIds) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be created in " + getProvider().getCloudName());
    }

    @Nonnull
    @Override
    public AccessKey enableAPIAccess(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("API access cannot be enabled in " + getProvider().getCloudName());
    }

    @Override
    public void enableConsoleAccess(@Nonnull String providerUserId, @Nonnull byte[] password) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Console access cannot be enabled in " + getProvider().getCloudName());
    }

    @Nullable
    @Override
    public CloudGroup getGroup(@Nonnull String providerGroupId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Groups cannot be retrieved in " + getProvider().getCloudName());
    }

    @Nullable
    @Override
    public CloudUser getUser(@Nonnull String providerUserId) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Users cannot be retrieved in " + getProvider().getCloudName());
    }
}
