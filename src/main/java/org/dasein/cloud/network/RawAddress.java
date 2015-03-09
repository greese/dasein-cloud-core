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

package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A raw IP address with basic address information.
 * <p>Created by George Reese: 1/9/13 10:18 AM</p>
 * @author George Reese
 * @version 2013.02 initial version (issue #38)
 * @since 2013.02
 */
public class RawAddress {
    private String ipAddress;
    private IPVersion version;

    @SuppressWarnings("UnusedDeclaration")
    private RawAddress() { }

    /**
     * Constructs a new raw addressed based on the specified IP address string and guesses at the version.
     * @param ipAddress the IP address string for the address
     */
    public RawAddress(@Nonnull String ipAddress) {
        this.ipAddress = ipAddress;
        String[] tmp = ipAddress.split("\\.");

        if( tmp.length == 4 ) {
            version = IPVersion.IPV4;
        }
        else {
            version = IPVersion.IPV6;
        }
    }

    /**
     * Constructs a new raw address based on the specified IP address string and version.
     * @param ipAddress the IP address string of the address
     * @param version the IP version associated with the address
     */
    public RawAddress(@Nonnull String ipAddress, @Nonnull IPVersion version) {
        this.ipAddress = ipAddress;
        this.version = version;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return other != null && (other == this || other.getClass().getName().equals(getClass().getName()) && ipAddress.equalsIgnoreCase(((RawAddress) other).ipAddress));
    }

    /**
     * @return the IP address string
     */
    public @Nonnull String getIpAddress() {
        return ipAddress;
    }

    /**
     * @return the IP version associated with this address
     */
    public @Nonnull IPVersion getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return ipAddress.hashCode();
    }

    /**
     * Indicates whether this IP address is reserved for private address spaces or likely represents a publicly
     * addressable IP address.
     * @return true if this address is in the publicly addressable address space
     */
    public boolean isPublicIpAddress() {
        if( getVersion().equals(IPVersion.IPV4) ) {
            if( ipAddress.startsWith("10.") || ipAddress.startsWith("192.168") || ipAddress.startsWith("169.254") ) {
                return false;
            }
            else if( ipAddress.startsWith("172.") ) {
                String[] parts = ipAddress.split("\\.");

                if( parts.length != 4 ) {
                    return true;
                }
                int x = Integer.parseInt(parts[1]);

                if( x >= 16 && x <= 31 ) {
                    return false;
                }
            }
        }
        else {
            if( ipAddress.startsWith("fd") || ipAddress.startsWith("fc00:")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @Nonnull String toString() {
        return ipAddress;
    }
}
