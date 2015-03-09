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

import org.dasein.cloud.*;
import org.dasein.cloud.test.TestNewCloudProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the naming convention capabilities with Dasein Cloud.
 * <p>Created by George Reese: 3/4/14 2:39 PM</p>
 * @author George Reese
 * @version 2014.03 initial version (issue #121)
 * @since 2014.03
 */
public class NamingConstraintsTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void verifyAlphanumeric() {
        NamingConstraints c = NamingConstraints.getAlphaNumeric(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertTrue("The conventions do not register as supporting numbers", c.isNumeric());
        assertFalse("The conventions are registering a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertNull("The conventions are showing symbol constraints", c.getSymbolConstraints());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyAlphaOnly() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions are registering a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertNull("The conventions are showing symbol constraints", c.getSymbolConstraints());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyStrict() {
        NamingConstraints c = NamingConstraints.getStrictInstance(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertTrue("The conventions do not register as supporting numbers", c.isNumeric());
        assertTrue("The conventions do not register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.LOWER, c.getAlphaCase());
        assertFalse("The conventions are registering as supporting spaces", c.isSpaces());
        assertFalse("The conventions are registering as supporting symbols", c.isSymbols());
        char[] constraints = c.getSymbolConstraints();

        assertNotNull("The conventions are showing symbol constraints", c);
        assertEquals("The length of the symbol constraint should be 0", 0, constraints.length);

        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifySymbolConstraints() {
        char[] test = { '_', '=', '-', '+' };
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).constrainedBy(test);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions are registering a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());

        char[] symbols = c.getSymbolConstraints();

        assertNotNull("The conventions are showing symbol constraints", c);
        assertEquals("The supported symbols don't match the target length", test.length, symbols.length);
        for( char a : symbols ) {
            boolean found = false;

            for( char b : test ) {
                if( a == b ) {
                    found = true;
                    break;
                }
            }
            assertTrue("Found character " + a + " among the returned symbols", found);
        }
        for( char a : test ) {
            boolean found = false;

            for( char b : symbols ) {
                if( a == b ) {
                    found = true;
                    break;
                }
            }
            assertTrue("Failed to find character " + a + " among the returned symbols", found);
        }
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyLatin1() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).limitedToLatin1();

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertTrue("The conventions do not register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyLowerCaseOnly() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).lowerCaseOnly();

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.LOWER, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyUpperCaseOnly() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).upperCaseOnly();

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.UPPER, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyNoSpaces() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).withNoSpaces();

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertFalse("The conventions register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyNoSymbols() {
        NamingConstraints c = NamingConstraints.getAlphaOnly(5, 10).withNoSymbols();

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConstraints.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertFalse("The conventions register as supporting symbols", c.isSymbols());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void checkUniquenessBoundedAlphanumeric() {
        NamingConstraints c = NamingConstraints.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthing";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void checkUniquenessAlmostLargeAlphanumeric() {
        NamingConstraints c = NamingConstraints.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthing12";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void checkUniquenessLargeAlphanumeric() {
        NamingConstraints c = NamingConstraints.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthingabcdefghi";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void upperCase() {
        String test = NamingConstraints.Case.UPPER.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to upper case", "tesT".toUpperCase(), test);
    }

    @Test
    public void lowerCase() {
        String test = NamingConstraints.Case.LOWER.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to lower case", "tesT".toLowerCase(), test);
    }

    @Test
    public void mixedCase() {
        String test = NamingConstraints.Case.MIXED.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to mixed case", "tesT", test);
    }

    @Test
    public void convertNames() {
        NamingConstraints c = NamingConstraints.getStrictInstance(5, 10);
        String str = "a";
        String result;

        result = c.convertToValidName(str, Locale.getDefault());
        assertNotNull("Unable to identify a valid name for " + str, result);
        assertTrue("The resulting name " + result + " was not valid", c.isValidName(result));

        str = "a1234567890";
        result = c.convertToValidName(str, Locale.getDefault());
        assertNotNull("Unable to identify a valid name for " + str, result);
        assertTrue("The resulting name " + result + " was not valid", c.isValidName(result));

        str = "1";
        result = c.convertToValidName(str, Locale.getDefault());
        assertNull("There should be no way to craft a valid name for " + str + ", but got " + result, result);

    }

    @Test
    public void findUniqueName() throws CloudException, InternalException {
        Cloud cloud = Cloud.register("Some Provider", "Some Cloud", "https://example.com", TestNewCloudProvider.class);
        ProviderContext ctx = cloud.createContext("12345", "antarctica", CloudConnectTestCase.KEYS, CloudConnectTestCase.X509, CloudConnectTestCase.VERSION);
        NamingConstraints constraints = NamingConstraints.getStrictInstance(2, 20).constrainedBy('_', '-', '=');
        final String[] names = { "friend", "friendship", "beta", "softdrink" };
        CloudProvider p = ctx.connect();

        String unique = p.findUniqueName("friend", constraints, new ResourceNamespace() {
            @Override
            public boolean hasNamedItem(@Nonnull String withName) throws CloudException, InternalException {
                for( String name : names ) {
                    if( name.equalsIgnoreCase(withName) ) { // a case-insensitive example!
                        return true;
                    }
                }
                return false;
            }
        });
        assertNotNull("It should have found a unique name, but it did not", unique);
        assertFalse("The unique name equals the provided name, which already exists", "friend".equalsIgnoreCase(unique));
    }
}
