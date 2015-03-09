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

/**
 * <p>
 * Server architecture, often requiring distinct images.
 * </p>
 * @author George Reese @ enstratius (http://www.enstratius.com)
 */
public enum Architecture {
    /**
     * Intel 386, 32-bit
     */
    I32,
    /**
     * Intel x86, 64-bit
     */
    I64,
    /**
     * Power architecture
     */
    POWER,
    /**
     * Sparc
     */
    SPARC
    ;
    
    public String toString() {
        switch( this ) {
        case I32: return "i386";
        case I64: return "x86_64";
        case POWER: return "power";
        case SPARC: return "sparc";
        default: return "unknown";
        }
    }
}
