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

package org.dasein.cloud;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Describes what is required for a specific cloud in order to configure Dasein to connect with that cloud.
 * <p>Created by George Reese: 2/27/14 8:09 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #123)
 * @since 2014.03
 */
public class ContextRequirements {
    /**
     * The data type associated with a configurable value
     */
    static public enum FieldType {
        /**
         * A general text value
         */
        TEXT,
        /**
         * A password field that should be treated as sensitive data
         */
        PASSWORD,
        /**
         * A secret token, as may be required alternatively to a key-pair, e.g. in OAuth authenticating accounts
         */
        TOKEN,
        /**
         * A combo text + password value with the first value being a public-like key thing or user name and the second value being a secret key or password
         */
        KEYPAIR,
        /**
         * A straight-up integer number
         */
        INTEGER,
        /**
         * A floating point number
         */
        FLOAT
    }

    /**
     * A pairing of field name and its type
     */
    static public class Field {
        static public final String ACCESS_KEYS = "accessKeys"; // legacy compatibility field name
        static public final String X509        = "x509";

        public String    compatName;
        public String    description;
        public String    name;
        public boolean   required;
        public FieldType type;

        public Field(@Nonnull String name, @Nonnull FieldType type) {
            this(name, type, true);
        }

        public Field(@Nonnull String name, @Nonnull String description, @Nonnull FieldType type) {
            this(name, description, type, true);
        }

        public Field(@Nonnull String name, @Nonnull FieldType type, @Nonnull String compatName) {
            this(name, type, compatName, true);
        }

        public Field(@Nonnull String name, @Nonnull String description, @Nonnull FieldType type, @Nonnull String compatName) {
            this(name, description, type, compatName, true);
        }

        public Field(@Nonnull String name, @Nonnull FieldType type, boolean required) {
            this.name = name;
            this.type = type;
            this.required = required;
        }

        public Field(@Nonnull String name, @Nonnull String description, @Nonnull FieldType type, boolean required) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.required = required;
        }

        public Field(@Nonnull String name, @Nonnull FieldType type, @Nonnull String compatName, boolean required) {
            this.name = name;
            this.type = type;
            this.compatName = compatName;
            this.required = required;
        }

        public Field(@Nonnull String name, @Nonnull String description, @Nonnull FieldType type, @Nonnull String compatName, boolean required) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.compatName = compatName;
            this.required = required;
        }

        public @Nonnull String toString() {
            return (name + " (" + type + "): " + description);
        }
    }

    private List<Field> configurableValues = new ArrayList<Field>();

    /**
     * Constructs a set of context requirements from the ordered fields provided. Order is useful in
     * helping a user interface to present these requirements meaningfully.
     * @param fields the ordered list of configurable fields
     */
    public ContextRequirements(@Nonnull Field ... fields) {
        Collections.addAll(configurableValues, fields);
    }

    /**
     * Identifies the field that matches the old accessPublic and accessPrivate fields. Because the new
     * mechanism treats them as one value, this returns a single field that matches both. If compatibility has
     * not been defined, the first {@link FieldType#KEYPAIR} field is considered a match.
     * @return the field that matches the old style keypair of accessPublic and accessPrivate
     */
    public @Nullable Field getCompatAccessKeys() {
        boolean hasX509 = false;
        Field def = null;

        for( Field f : configurableValues ) {
            if( !hasX509 && Field.X509.equals(f.compatName) ) {
                hasX509 = true;
            }
            if( Field.ACCESS_KEYS.equals(f.compatName) ) {
                return f;
            }
            else if( def == null && f.compatName == null && f.type.equals(FieldType.KEYPAIR) ) {
                def = f;
            }
        }
        if( hasX509 ) {
            return null;
        }
        return def;
    }

    /**
     * Identifies the field that matches the old x509Cert and x509Key fields. Because the new
     * mechanism treats them as one value, this returns a single field that matches both. If compatability has
     * not been defined, the second {@link FieldType#KEYPAIR} field is considered a match. If compatibility was
     * defined for access keys and not for x509, however, this method will return <code>null</code>.
     * @return the field that matches the old style keypair of x509Cert and x509Key
     */
    public @Nullable Field getCompatAccessX509() {
        Field d1 = null, d2 = null;
        boolean hasAccess = false;

        for( Field f : configurableValues ) {
            if( !hasAccess && Field.ACCESS_KEYS.equals(f.compatName) ) {
                hasAccess = true;
            }
            if( Field.X509.equals(f.compatName) ) {
                return f;
            }
            else if( f.type.equals(FieldType.KEYPAIR) && f.compatName == null ) {
                if( d1 == null ) {
                    d1 = f;
                }
                else if( d2 == null ) {
                    d2 = f;
                }
            }
        }
        if( hasAccess ) {
            return null;
        }
        return d2;
    }

    /**
     * @return an ordered list of configurable fields
     */
    public @Nonnull List<Field> getConfigurableValues() {
        return Collections.unmodifiableList(configurableValues);
    }
}
