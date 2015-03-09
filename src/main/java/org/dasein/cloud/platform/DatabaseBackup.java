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

package org.dasein.cloud.platform;

import java.io.Serializable;

public class DatabaseBackup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String                adminUser;
    private DatabaseBackupState   currentState; 
    private String                providerDatabaseId;
    private String                providerOwnerId;
    private String                providerRegionId;
    private String                providerBackupId;
    private int                   storageInGigabytes;

    private String backupConfiguration = null;
    private String dueTime = null;
    private String enqueuedTime = null;
    private String startTime = null;
    private String endTime = null;

    public DatabaseBackup() { }

    public boolean equals(Object ob) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !getClass().getName().equals(ob.getClass().getName()) ) {
            return false;
        }
        DatabaseBackup other = (DatabaseBackup)ob;

        if( !getProviderOwnerId().equals(other.getProviderOwnerId()) ) {
            return false;
        }
        if( !getProviderRegionId().equals(other.getProviderRegionId()) ) {
            return false;
        }
        return getProviderBackupId().equals(other.getProviderBackupId());
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public DatabaseBackupState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(DatabaseBackupState currentState) {
        this.currentState = currentState;
    }

    public String getProviderDatabaseId() {
        return providerDatabaseId;
    }

    public void setProviderDatabaseId(String providerDatabaseId) {
        this.providerDatabaseId = providerDatabaseId;
    }

    public String getProviderRegionId() {
        return providerRegionId;
    }

    public void setProviderRegionId(String providerRegionId) {
        this.providerRegionId = providerRegionId;
    }

    public String getProviderBackupId() {
        return providerBackupId;
    }

    public void setProviderBackupId(String providerSnapshotId) {
        this.providerBackupId = providerSnapshotId;
    }

    public int getStorageInGigabytes() {
        return storageInGigabytes;
    }

    public void setStorageInGigabytes(int storageInGigabytes) {
        this.storageInGigabytes = storageInGigabytes;
    }

    public void setProviderOwnerId(String providerOwnerId) {
        this.providerOwnerId = providerOwnerId;
    }

    public String getProviderOwnerId() {
        return providerOwnerId;
    }

    public String toString() {
        return getProviderBackupId();
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
    public String getDueTime() {
        return dueTime;
    }

    public void setEnqueuedTime(String enqueuedTime) {
        this.enqueuedTime = enqueuedTime;
    }

    public String getEnqueuedTime() {
        return enqueuedTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getBackupConfiguration() {
        return backupConfiguration;
    }

    public void setBackupConfiguration(String backupConfiguration) {
        this.backupConfiguration = backupConfiguration;
    }
}