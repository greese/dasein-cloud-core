/*
 * Copyright (C) 2014 Dell, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dasein.cloud.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 3/4/14 2:39 PM</p>
 *
 * @author George Reese
 */
public class NamingConventionsTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void verifyAlphanumeric() {
        NamingConventions c = NamingConventions.getAlphaNumeric(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertTrue("The conventions do not register as supporting numbers", c.isNumeric());
        assertFalse("The conventions are registering a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConventions.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertNull("The conventions are showing symbol constraints", c.getSymbolConstraints());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyAlphaOnly() {
        NamingConventions c = NamingConventions.getAlphaOnly(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertFalse("The conventions register as supporting numbers", c.isNumeric());
        assertFalse("The conventions are registering a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConventions.Case.MIXED, c.getAlphaCase());
        assertTrue("The conventions do not register as supporting spaces", c.isSpaces());
        assertTrue("The conventions do not register as supporting symbols", c.isSymbols());
        assertNull("The conventions are showing symbol constraints", c.getSymbolConstraints());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void verifyStrict() {
        NamingConventions c = NamingConventions.getStrictInstance(5, 10);

        assertEquals("The minimum name length does not match the requested value", 5, c.getMinimumLength());
        assertEquals("The maximum name length does not match the requested value", 10, c.getMaximumLength());

        assertTrue("The conventions do not register as supporting letters", c.isAlpha());
        assertTrue("The conventions do not register as supporting numbers", c.isNumeric());
        assertTrue("The conventions do not register a latin-1 constraint", c.isLatin1Constrained());
        assertEquals("The letter case requirements do not match", NamingConventions.Case.LOWER, c.getAlphaCase());
        assertFalse("The conventions are registering as supporting spaces", c.isSpaces());
        assertFalse("The conventions are registering as supporting symbols", c.isSymbols());
        char[] constraints = c.getSymbolConstraints();

        assertNotNull("The conventions are showing symbol constraints", c);
        assertEquals("The length of the symbol constraint should be 0", 0, constraints.length);

        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterNumericAllowed());
        assertFalse("The first character is supposed to be limited to letters for these conventions", c.isFirstCharacterSymbolAllowed());
    }

    @Test
    public void checkUniquenessBoundedAlphanumeric() {
        NamingConventions c = NamingConventions.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthing";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, Locale.getDefault(), i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void checkUniquenessAlmostLargeAlphanumeric() {
        NamingConventions c = NamingConventions.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthing12";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, Locale.getDefault(), i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void checkUniquenessLargeAlphanumeric() {
        NamingConventions c = NamingConventions.getAlphaNumeric(5, 12);
        ArrayList<String> names = new ArrayList<String>();
        String base = "testthingabcdefghi";

        for( int i=1; i<1000; i++ ) {
            String name = c.incrementName(base, Locale.getDefault(), i);

            assertNotNull("The name should not be null in this context", name);
            assertTrue("The name exceeds the maximum length for the naming conventions", name.length() <= c.getMaximumLength());
            assertFalse("The name " + name + " was already found while building increment " + i, names.contains(name));
            names.add(name);
        }
    }

    @Test
    public void upperCase() {
        String test = NamingConventions.Case.UPPER.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to upper case", "tesT".toUpperCase(), test);
    }

    @Test
    public void lowerCase() {
        String test = NamingConventions.Case.LOWER.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to lower case", "tesT".toLowerCase(), test);
    }

    @Test
    public void mixedCase() {
        String test = NamingConventions.Case.MIXED.convert("tesT", Locale.getDefault());

        assertEquals("Failed to convert to mixed case", "tesT", test);
    }
}
