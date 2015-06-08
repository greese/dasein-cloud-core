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

package org.dasein.cloud.util;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Implements naming conventions for a type of resource within a given cloud. This method enables a client
 * both to learn about the naming conventions for different types of resources in a cloud as well as make names
 * that conform to those naming conventions.
 * <p>Created by George Reese: 3/4/14 9:31 AM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #134 and #121)
 * @since 2014.03
 */
public class NamingConstraints {
    /**
     * Indicates case restrictions on alphabetic components.
     */
    static public enum Case {
        /**
         * Names must contain only lower case letters
         */
        LOWER,
        /**
         * Names must contain only upper case letters
         */
        UPPER,
        /**
         * Mixed case letters are allowed
         */
        MIXED;

        /**
         * Converts a name so that it matches the case aligned with the constraints behind this enum.
         * @param baseName the raw name to be converted
         * @param locale the locale for which case is to be applied
         * @return a string converted to conform with the constraints of this enum instance
         */
        public @Nonnull String convert(@Nonnull String baseName, @Nonnull Locale locale) {
            switch( this ) {
                case LOWER: return baseName.toLowerCase(locale);
                case UPPER: return baseName.toUpperCase(locale);
                default: return baseName;
            }
        }
    }

    /**
     * Constructs a baseline set of naming conventions that supports only alphabetic characters. The resulting
     * naming conventions will be mixed case, full unicode spectrum, allowing spaces and symbols. The first character
     * allowed must be alphabetic.
     * @param minLength the minimum length of a valid name
     * @param maxLength the maximum length of a valid name (will be adjusted to min length if less than min length)
     * @return naming conventions that match the characteristics described above
     */
    static public @Nonnull NamingConstraints getAlphaOnly(@Nonnegative int minLength, @Nonnegative int maxLength) {
        NamingConstraints n = new NamingConstraints();

        n.alpha = true;
        n.alphaCase = Case.MIXED;
        n.latin1Constrained = false;
        n.minimumLength = minLength;
        n.maximumLength = maxLength;
        if( n.maximumLength < n.minimumLength ) {
            n.maximumLength = n.minimumLength;
        }
        n.numeric = false;
        n.spaces = true;
        n.symbolConstraints = null;
        n.symbols = true;
        n.firstCharacterNumericAllowed = false;
        n.firstCharacterSymbolAllowed = false;
        return n;
    }

    /**
     * Constructs a baseline set of naming conventions for full alphanumeric support. The resulting
     * naming conventions will be mixed case, full unicode spectrum, allowing numbers, spaces, and symbols. The first
     * character allowed must be a letter.
     * @param minLength the minimum length of a valid name
     * @param maxLength the maximum length of a valid name (will be adjusted to min length if less than min length)
     * @return naming conventions that match the characteristics described above
     */
    static public @Nonnull NamingConstraints getAlphaNumeric(@Nonnegative int minLength, @Nonnegative int maxLength) {
        NamingConstraints n = new NamingConstraints();

        n.alpha = true;
        n.alphaCase = Case.MIXED;
        n.latin1Constrained = false;
        n.minimumLength = minLength;
        n.maximumLength = maxLength;
        if( n.maximumLength < n.minimumLength ) {
            n.maximumLength = n.minimumLength;
        }
        n.numeric = true;
        n.spaces = true;
        n.symbolConstraints = null;
        n.symbols = true;
        n.firstCharacterNumericAllowed = false;
        n.firstCharacterSymbolAllowed = false;
        return n;
    }

    /**
     * Provides a convenient set of naming constraints for naming hosts on a network.
     * @param forWindowsNetwork true if you are targeting a windows host, false otherwise
     * @return naming constraints supporting host names
     */
    static public @Nonnull NamingConstraints getHostNameInstance(boolean forWindowsNetwork) {
        NamingConstraints n = new NamingConstraints();

        n.minimumLength = 3;
        n.maximumLength = (forWindowsNetwork ? 15 : 30);
        n.alpha = true;
        n.alphaCase = (forWindowsNetwork ? Case.UPPER : Case.MIXED);
        n.latin1Constrained = true;
        n.numeric = true;
        n.spaces = false;
        n.symbols = !forWindowsNetwork;
        n.symbolConstraints = (forWindowsNetwork ? null : new char[] { '-' });
        n.firstCharacterNumericAllowed = false;
        n.firstCharacterSymbolAllowed = false;
        return n;
    }

    /**
     * Constructs a baseline set of naming conventions that match strict naming often seen in old file systems and programming
     * languages. The resulting conventions will be alphanumeric lower-case and latin-1 only. The first
     * character allowed must be a letter.
     * @param minimumLength the minimum length of a valid name
     * @param maximumLength the maximum length of a valid name (will be adjusted to min length if less than min length)
     * @return naming conventions that match the characteristics described above
     */
    static public @Nonnull NamingConstraints getStrictInstance(@Nonnegative int minimumLength, @Nonnegative int maximumLength) {
        NamingConstraints n = new NamingConstraints();

        n.alpha = true;
        n.alphaCase = Case.LOWER;
        n.latin1Constrained = true;
        n.minimumLength = minimumLength;
        n.maximumLength = maximumLength;
        if( n.maximumLength < n.minimumLength ) {
            n.maximumLength = n.minimumLength;
        }
        n.numeric = true;
        n.spaces = false;
        n.symbolConstraints = null;
        n.symbols = false;
        n.firstCharacterNumericAllowed = false;
        n.firstCharacterSymbolAllowed = false;
        return n;
    }

    private boolean alpha;
    private Case    alphaCase;
    private boolean firstCharacterNumericAllowed;
    private boolean firstCharacterSymbolAllowed;
    private boolean latin1Constrained;
    private int     maximumLength;
    private int     minimumLength;
    private boolean numeric;
    private boolean spaces;
    private char[]  symbolConstraints;
    private boolean symbols;
    private String  regularExpression;
    private boolean lastCharacterSymbolAllowed;
    private String  description;

    private NamingConstraints() { }

    /**
     * Alters the naming conventions so that any support for symbols is specifically limited to a certain set of
     * symbols.
     * @param symbols the valid symbols for names conforming to these conventions
     * @return this
     */
    public @Nonnull
    NamingConstraints constrainedBy(@Nonnull char ... symbols) {
        symbolConstraints = symbols;
        this.symbols = true;
        return this;
    }

    /**
     * Converts a raw name into a valid name supported by these naming conventions. If the raw name already
     * conforms, it will be returned as-is. If it is too short, random characters will be appended to make it
     * conform to minimum length requirements. If it is too long, it will be truncated. It will also be
     * converted to the appropriate case with any invalid characters being removed.
     * @param baseName the raw name to be converted
     * @param locale the locale according to which conversion rules will be applied
     * @return a valid name based on the base name conforming to these naming conventions
     */
    public @Nullable String convertToValidName(@Nonnull String baseName, @Nonnull Locale locale) {
        StringBuilder str = new StringBuilder();
        int i = 0;

        baseName = alphaCase.convert(baseName.trim(), locale);
        for( char c : baseName.toCharArray() ) {
            if( str.length() == maximumLength ) {
                return str.toString();
            }
            if( isValid(c, i) ) {
                str.append(c);
            }
            else if( c == ' ' && i != 0 ) {
                c = getValidSymbol('_', '-');
                if( c != (char)0 ) {
                    str.append(c);
                }
            }
            i++;
        }
        if( str.length() < 1 ) {
            return null;
        }
        boolean dashed = false;
        while( str.length() < minimumLength ) {
            if( !dashed ) {
                dashed = true;
                char c = getValidSymbol('-', '_');

                if( c != (char)0 ) {
                    str.append(c);
                }
            }
            else {
                str.append(getRandomCharacter(true, str.length()));
            }
        }
        if( str.length() > maximumLength ) {
            return str.toString().substring(0, maximumLength);
        }

        char[] nameArray = str.toString().toCharArray();


        if (lastCharacterSymbolAllowed == false) {
            while (!Character.isLetterOrDigit(nameArray[str.length() - 1])) {
                str.deleteCharAt(str.length() - 1);
            }
        }

        if (null != regularExpression) {
            if (!str.toString().matches(regularExpression)) {
                Logger logger = Logger.getLogger("" + NamingConstraints.class) ;
                logger.warning("WARNING: regularExpression fails to validate cleaned name. NAME=" + str.toString() + " regularExpression=" + regularExpression);
            }
        }

        return str.toString();
    }

    /**
     * @return the case constraints that names supported by these naming conventions must conform
     */
    public @Nonnull Case getAlphaCase() {
        return alphaCase;
    }

    /**
     * @return the maximum number of characters allowed in names supported by these naming conventions
     */
    public @Nonnegative int getMaximumLength() {
        return maximumLength;
    }

    /**
     * @return the minimum number of characters allowed in names supported by these naming conventions
     */
    public @Nonnegative int getMinimumLength() {
        return minimumLength;
    }

    static private Random random = new Random();

    /**
     * Generates a random character that will conform to these naming conventions.
     * @param alphanumericOnly the resulting character should be alphanumeric even if symbols and spaces are normally allowed
     * @param forPosition the string position for which the character is being generated
     * @return a random character conforming to these naming conventions
     */
    public char getRandomCharacter(boolean alphanumericOnly, int forPosition) {
        char c = (char)random.nextInt(128);

        while( !isValid(c, forPosition) || (alphanumericOnly && !Character.isLetterOrDigit(c)) ) {
            c = (char)random.nextInt(128);
        }
        return c;
    }

    /**
     * @return a list of allowed symbols when {@link #isSymbols()} is true (<code>null</code> if any symbol is allowed)
     */
    public @Nullable char[] getSymbolConstraints() {
        if( !symbols ) {
            return new char[0];
        }
        return symbolConstraints;
    }

    /**
     * Provides a symbol conforming to these naming conventions based on a list of desired symbols. This method is
     * useful, for example, in picking a symbol to replace a space. The first symbol in the list considered valid
     * will be returned.
     * @param fromList the list of symbols from which a valid symbol is being sought
     * @return the first symbol in the list that is considered valid or <code>(char)0</code> if none are valid
     */
    public char getValidSymbol(char ... fromList) {
        if( !symbols ) {
            return (char)0;
        }
        if( symbolConstraints == null ) {
            return fromList[0];
        }
        for( char l : fromList ) {
            for( char c : symbolConstraints ) {
                if( c == l ) {
                    return l;
                }
            }
        }
        return (char)0;
    }

    /**
     * A tool for unique name generators to generate unique names. A calling client will maintain a count of times it
     * has called this method and then check if the result is unique in its namespace. If not unique, it will
     * increment the count and call it again. It repeats this process until it finds a unique name. For example:
     * <pre>
     *     String name = "somename";
     *
     *     if( !isUnique(name) ) {
     *         int i = 1;
     *
     *         String test = conventions.incrementName(name, Locale.getDefault(), i++);
     *
     *         while( test != null &amp;&amp; !isUnique(test) ) {
     *             test = conventions.increment(name, Locale.getDefault(), i++);
     *         }
     *         if( test == null ) {
     *             // CAN'T FIND A VALID, UNIQUE NAME!!!
     *         }
     *         else {
     *             name = test;
     *         }
     *     }
     * </pre>
     * @param baseName the original name that you hope is unique (this name is assumed to be valid already)
     * @param count the call count for this call (1 is the first call)
     * @return a name matching these naming conventions with a postfix that should hopefully make it unique
     */
    public @Nullable String incrementName(@Nonnull String baseName, @Nonnegative int count) {

        baseName = baseName.trim();
        char[] alphabet;

        if( numeric ) {
            if( alpha ) {
                if( alphaCase.equals(Case.MIXED) ) {
                    alphabet = new char[] {
                            '0','1','2','3','4','5','6','7','8','9',
                            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                    };
                }
                else if( alphaCase.equals(Case.LOWER) ) {
                    alphabet = new char[] {
                            '0','1','2','3','4','5','6','7','8','9',
                            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
                    };
                }
                else {
                    alphabet = new char[] {
                            '0','1','2','3','4','5','6','7','8','9',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                    };
                }
            }
            else {
                alphabet = new char[] {
                        '0','1','2','3','4','5','6','7','8','9'
                };
            }
        }
        else if( !alpha ) {
            return null;
        }
        else {
            if( alphaCase.equals(Case.MIXED) ) {
                alphabet = new char[] {
                        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                };
            }
            else if( alphaCase.equals(Case.LOWER) ) {
                alphabet = new char[] {
                        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
                };
            }
            else {
                alphabet = new char[] {
                        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
                };
            }
        }

        StringBuilder postfix = new StringBuilder();
        int div, mod;

        div = count/alphabet.length;
        mod = count%alphabet.length;
        if( div < 1 ) {
            postfix.append(alphabet[mod]);
        }
        else if( div == 1 ) {
            postfix.append(alphabet[mod]);
            postfix.append(alphabet[1]);
        }
        else {
            while( div > 0 ) {
                postfix.append(alphabet[mod]);
                if( div == count ) {
                    postfix.append(alphabet[0]);
                    postfix.append(alphabet[1]);
                    div = 0;
                    mod = 0;
                }
                else {
                    count = div;
                    div = count/alphabet.length;
                    mod = count%alphabet.length;
                }
            }
            if( mod > 0 ) {
                postfix.append(alphabet[mod]);
            }
        }
        if( spaces ) {
            postfix.append(' ');
        }
        else {
            char divider = getValidSymbol('-', '_');

            if( divider != (char)0 ) {
                postfix.append(divider);
            }
        }
        String tmp = postfix.reverse().toString();

        if( (baseName.length() + tmp.length()) > maximumLength ) {
            if( tmp.length() >= maximumLength ) {
                if( baseName.length() == 1 ) {
                    return null;
                }
                return incrementName(baseName.substring(0,baseName.length()-1), count);
            }
            else {
                int cut = (baseName.length() + tmp.length()) - maximumLength;

                baseName = baseName.substring(0, baseName.length() - cut);
            }
        }
        return (baseName + tmp);
    }

    /**
     * @return true if these naming conventions allow for letters
     */
    public boolean isAlpha() {
        return alpha;
    }

    /**
     * @return true if the first character in a name may be a number
     */
    public boolean isFirstCharacterNumericAllowed() {
        return firstCharacterNumericAllowed;
    }

    /**
     * @return true if the first character in a name is allowed to be a symbol (constrained by {@link #getSymbolConstraints()})
     */
    public boolean isFirstCharacterSymbolAllowed() {
        return firstCharacterSymbolAllowed;
    }

    /**
     * @return true if the last character in a name is allowed to be a symbol
     */
    public boolean isLastCharacterSymbolAllowed() { return lastCharacterSymbolAllowed; }

    /**
     * @return true if these naming conventions support only Latin 1 characters
     */
    public boolean isLatin1Constrained() {
        return latin1Constrained;
    }

    /**
     * @return true if these naming conventions allow numbers in a name
     */
    public boolean isNumeric() {
        return numeric;
    }

    /**
     * @return true if these naming conventions allow spaces in names (spaces are never allowed at the beginning or end of names)
     */
    public boolean isSpaces() {
        return spaces;
    }

    /**
     * @return true if these naming conventions allow symbols in names (symbols may be constrained by {@link #getSymbolConstraints()})
     */
    public boolean isSymbols() {
        return symbols;
    }

    /**
     * Validates the specified character against these naming conventions and returns true if it is valid in the specified
     * position.
     * @param c the character to be tested
     * @param position the position in the name
     * @return true if the character is valid according to these naming conventions for the specified position
     */
    public boolean isValid(char c, int position) {
        if( latin1Constrained && ((int)c) > 255 ) {
            return false;
        }
        if( Character.isLetter(c) ) {
            if( !alpha ) {
                return false;
            }
            if( alphaCase.equals(Case.MIXED) ) {
                return true;
            }
            else if( alphaCase.equals(Case.LOWER) ) {
                return Character.isLowerCase(c);
            }
            return !Character.isLowerCase(c);
        }
        else if( Character.isDigit(c) ) {
            return (numeric && (position > 0 || firstCharacterNumericAllowed));
        }
        if( Character.isSpaceChar(c) ) {
            return (position > 0 && spaces);
        }
        if( position == 0 && !firstCharacterSymbolAllowed ) {
            return false;
        }
        if( symbolConstraints != null ) {
            for( char option : symbolConstraints ) {
                if( c == option ) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if the specified name is valid according to these naming constraints.
     * @param name the name to be checked
     * @return true if the name is a valid name
     */
    public boolean isValidName(@Nonnull String name) {
        int len = name.length();

        if( len < minimumLength || len > maximumLength ) {
            return false;
        }
        int i = 0;

        for( char c : name.toCharArray() ) {
            if( !isValid(c, i++) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Constrains these naming conventions to the latin 1 character set.
     * @return this
     */
    public @Nonnull
    NamingConstraints limitedToLatin1() {
        latin1Constrained = true;
        return this;
    }

    /**
     * Constrains the letters supported by these naming conventions to lower case letters.
     * @return this
     */
    public @Nonnull
    NamingConstraints lowerCaseOnly() {
        alphaCase = Case.LOWER;
        return this;
    }

    /**
     * Constrains the letters supported by these naming conventions to upper case letters.
     * @return this
     */
    public @Nonnull
    NamingConstraints upperCaseOnly() {
        alphaCase = Case.UPPER;
        return this;
    }

    /**
     * Disallows spaces in names conforming to these naming conventions.
     * @return this
     */
    public @Nonnull
    NamingConstraints withNoSpaces() {
        spaces = false;
        return this;
    }

    /**
     * Disallows spaces in names conforming to these naming conventions.
     * @return this
     */
    public @Nonnull
    NamingConstraints withNoSymbols() {
        symbols = false;
        return this;
    }

    public @Nonnull NamingConstraints withRegularExpression(String regularExpression){
        this.regularExpression = regularExpression;
        return this;
    }

    public @Nullable String getRegularExpression(){
        return regularExpression;
    }

    public @Nonnull NamingConstraints withLastCharacterSymbolAllowed(boolean lastCharacterSymbolAllowed){
        this.lastCharacterSymbolAllowed = lastCharacterSymbolAllowed;
        return this;
    }

    public @Nonnull NamingConstraints withDescription(String description){
        this.description = description;
        return this;
    }

    public String getDescription(){
        return description;
    }
}
