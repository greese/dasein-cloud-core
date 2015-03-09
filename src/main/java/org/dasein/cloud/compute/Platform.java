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

package org.dasein.cloud.compute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * An operating system associated with servers and images.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 */
public enum Platform {
    /**
     * Generic UNIX
     */
    UNIX, 
    /**
     * Ubuntu
     */
    UBUNTU,
    /**
     * Debian
     */
    DEBIAN,
    /**
     * Solaris
     */
    SOLARIS,
    /**
     * Fedora Core
     */
    FEDORA_CORE,
    /**
     * RHEL
     */
    RHEL,
    /**
     * FreeBSD
     */
    FREE_BSD,
    /**
     * OpenBSD
     */
    OPEN_BSD,
    /**
     * CentOS
     */
    CENT_OS,
    /**
     * CoreOS
     */
    COREOS,
    /**
     * Generic Windows
     */
    WINDOWS,
    /**
     * SUSE
     */
    SUSE,
    /**
     * SmartOS
     */
    SMARTOS,
    /**
     * No clue
     */
    UNKNOWN;

    static public @Nonnull Platform guess(@Nullable String name) {
        if( name == null ) {
            return UNKNOWN;
        }
        name = name.toLowerCase();
        if( name.contains("centos") ) {
            return CENT_OS;
        }
        else if( name.contains("coreos") ) {
            return COREOS;
        }
        else if( name.contains("ubuntu") ) {
            return UBUNTU;
        }
        else if( name.contains("fedora") ) {
            return FEDORA_CORE;
        }
        else if( name.contains("windows") || name.contains("win") ) {
            return WINDOWS;
        }
        else if( name.contains("red hat") || name.contains("redhat") || name.contains("red-hat") || name.contains("rhel") ) {
            return RHEL;
        }
        else if( name.contains("debian") ) {
            return DEBIAN;
        }
        else if( name.contains("suse") || name.contains("sles")  ) {
            return SUSE;
        }
        else if(name.contains("smartos")){
            return SMARTOS;
        }
        else if( name.contains("bsd") ) {
            if( name.contains("free") ) {
                return FREE_BSD;
            }
            else if( name.contains("open") ) {
                return OPEN_BSD;
            }
            else {
                return UNIX;
            }
        }
        else if( name.contains("solaris") ) {
            return SOLARIS;
        }
        else if( name.contains("linux") ) {
            return UNIX;
        }
        return UNKNOWN;
    }
    
    /**
     * Provides an appropriate device ID (e.g. sdh) for this platform given a device letter.
     * @param letter the letter to be mapped into a platform-specific device ID
     * @return the platform-specific device ID for the target letter
     */
    public String getDeviceId(String letter) {
        switch( this ) {
        case WINDOWS: return "xvd" + letter;
        default: return "sd" + letter;
        }
    }
    
    /**
     * Provides a device mapping (e.g. /dev/sdh) for the target device letter.
     * @param letter the letter to be mapped
     * @return the device mapping for the specified letter
     */
    public String getDeviceMapping(String letter) {
        switch( this ) {
        case WINDOWS: return "xvd" + letter;
        default: return "/dev/sd" + letter;
        }
    }
    
    public boolean isBsd() {
        return (equals(FREE_BSD) || equals(OPEN_BSD));
    }
    
    public boolean isLinux() {
        switch( this ) {
        case SOLARIS: case FREE_BSD: case OPEN_BSD: case WINDOWS: case UNKNOWN: return false;
        default: return true;
        }
    }
    
    public boolean isOpen() {
        return (isLinux() || isBsd() || equals(SOLARIS));
    }
    
    public boolean isUnix() {
        return (!isWindows() && !equals(UNKNOWN));
    }
        
    public boolean isWindows() {
        return equals(WINDOWS);
    }
    
    public String toString() {
        switch( this ) {
        case UNIX: return "Generic Unix"; 
        case UBUNTU: return "Ubuntu";
        case DEBIAN: return "Debian";
        case SOLARIS: return "Solaris";
        case FEDORA_CORE: return "Fedora";
        case RHEL: return "Red Hat";
        case SUSE: return "SUSE";
        case FREE_BSD: return "FreeBSD";
        case OPEN_BSD: return "OpenBSD";
        case CENT_OS: return "CentOS";
        case COREOS: return "CoreOS";
        case WINDOWS: return "Windows";
        }
        return "Unknown";
    }
}
