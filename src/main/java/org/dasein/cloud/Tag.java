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
import java.io.Serializable;

/**
 * A key/value pair for use in cloud resource meta-data.
 * @author George Reese
 * @version 2013.04 added javadoc
 * @since unknown
 */
public class Tag implements Comparable<Tag> {
    private String key;
    private String value;

    /**
     * Constructs an empty tag with no key or value.
     */
    public Tag() { }

    /**
     * Constructs a tag with the specified key/value pair.
     * @param key the key for this pair
     * @param value the value to be associated with the key
     */
    public Tag(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(@Nonnull Tag tag) {
        int x = key.compareTo(tag.key);
        
        if( x != 0 ) {
            return x;
        }
        if( value == null ) {
            return "".compareTo(tag.value == null ? "" : tag.value);
        }
        else {
            return value.compareTo(tag.value == null ? "" : tag.value);
        }
    }

    @Override
    public boolean equals(@Nullable Object ob) {
        if( ob == null ) {
            return false;
        }
        if( ob == this ) {
            return true;
        }
        if( !ob.getClass().getName().equals(getClass().getName()) ) {
            return false;
        }
        Tag tag = (Tag)ob;
        if( !(key == null ? "" : key).equals(tag.key == null ? "" : tag.key) ) {
            return false;
        }
        return (value == null ? "" : value).equals(tag.value == null ? "" : tag.value);
    }

    /**
     * Sets the key associated with this tag.
     * @param key the key
     */
    public void setKey(@Nonnull String key) {
        this.key = key;
    }

    /**
     * @return the key for this key/value pair
     */
    public @Nonnull String getKey() {
        return key;
    }

    /**
     * Sets the value associated with this key/value pair.
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the value associated with this key/value pair
     */
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return (key + "=" + value);
    }
}
