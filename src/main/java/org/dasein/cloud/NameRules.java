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

package org.dasein.cloud;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Name rules for naming different kinds of cloud objects
 * <p>Created by George Reese: 8/1/12 10:29 AM</p>
 * @author George Reese
 * @version 2012.07 initial version
 * @since 2012.07
 */
public class NameRules {
    static public NameRules getInstance(@Nonnegative int minChars, @Nonnegative int maxChars, boolean mixedCase, boolean allowNumbers, boolean latin1Only, @Nullable char[] specialChars) {
        NameRules rules = new NameRules();

        rules.allowNumbers = allowNumbers;
        rules.mixedCase = mixedCase;
        rules.maximumCharacters = maxChars;
        rules.minimumCharacters = minChars;
        rules.onlyLatin1 = latin1Only;
        rules.specialCharacters = specialChars;
        return rules;
    }

    private boolean  allowNumbers;
    private boolean  mixedCase;
    private int      maximumCharacters;
    private int      minimumCharacters;
    private boolean  onlyLatin1;
    private char[]   specialCharacters;

    private NameRules() { }


    public boolean isAllowNumbers() {
        return allowNumbers;
    }

    public boolean isMixedCase() {
        return mixedCase;
    }

    public int getMaximumCharacters() {
        return maximumCharacters;
    }

    public int getMinimumCharacters() {
        return minimumCharacters;
    }

    public boolean isOnlyLatin1() {
        return onlyLatin1;
    }

    public char[] getSpecialCharacters() {
        return specialCharacters;
    }

    public boolean isValidName(@Nonnull String name) {
        if( maximumCharacters < name.length() ) {
            return false;
        }
        if( minimumCharacters > name.length() ) {
            return false;
        }
        for( char c : name.toCharArray() ) {
            if( c < 'a' || c > 'z' ) {
                if( c >= 'A' && c <= 'Z' ) {
                    if( !mixedCase ) {
                        return false;
                    }
                }
                else if( c >= '0' && c <= '9' ) {
                    if( !allowNumbers ) {
                        return false;
                    }
                }
                else if( Character.isLetterOrDigit(c) ) {
                    if( onlyLatin1 ) {
                        return false;
                    }
                }
                else if( specialCharacters != null ) {
                    boolean ok = false;

                    for( char s : specialCharacters ) {
                        if( s == c ) {
                            ok = true;
                        }
                    }
                    if( !ok ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public @Nonnull String validName(@Nonnull String originalName) {
        StringBuilder newName = new StringBuilder();
        String special = null;

        if( !mixedCase ) {
            originalName = originalName.toLowerCase();
        }
        if( specialCharacters != null ) {
            for( char c : specialCharacters ) {
                if( c == '_' || special == null ) {
                    special = String.valueOf(c);
                }
            }
        }
        for( char c : originalName.toCharArray() ) {
            if( c >= 'a' && c <= 'z' ) {
                newName.append(c);
            }
            else if( c >= '0' && c <= '9' ) {
                if( allowNumbers ) {
                    newName.append(c);
                }
                else if( special != null ) {
                    newName.append(special);
                }
            }
            else if( Character.isLetterOrDigit(c) ) {
                if( !onlyLatin1 ) {
                    newName.append(c);
                }
                else if( special != null ) {
                    newName.append(special);
                }
            }
            else if( special != null ) {
                for( char s : special.toCharArray() ) {
                    if( s == c ) {
                        newName.append(c);
                    }
                }
            }
        }
        if( newName.length() > maximumCharacters ) {
            return newName.toString().substring(0, maximumCharacters);
        }
        while( newName.length() < minimumCharacters ) {
            newName.append('x');
        }
        return newName.toString();
    }

    @Override
    public @Nonnull String toString() {
        return ("min=" + minimumCharacters + ",max=" + maximumCharacters + ",mixed=" + mixedCase + ",numbers=" + allowNumbers + ",onlyLatin=" + onlyLatin1 + ",special=" + Arrays.toString(specialCharacters));
    }
}
