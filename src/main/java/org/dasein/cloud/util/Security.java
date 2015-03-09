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
import java.security.SecureRandom;

/**
 * Generates a random string that can be used by driver's to create passwords randomly.
 * <p>Created by George Reese: 2/7/14 5:36 PM</p>
 * @author George Reese
 * @since 2014.03
 * @version 2014.03 initial version
 */
public class Security {
    static public final String ALPHABET       = "ABCEFGHJKMNPRSUVWXYZabcdefghjkmnpqrstuvwxyz0123456789#@()=+/{}[],.?;':|-_!$%^&*~";
    static public final String STRING_TOKEN   = "ABCEFGHJKMNPRSUVWXYZ";
    static public final String ALPHANUM_TOKEN = "ABCEFGHJKMNPRSUVWXYZ23456789";

    static private final SecureRandom random = new SecureRandom();

    static public @Nonnull String generateRandomPassword(@Nonnegative int minLength, @Nonnegative int maxLength) {
        return generateRandomPassword(ALPHABET, minLength, maxLength);
    }

    static public @Nonnull String generateRandomPassword(@Nonnull String alphabet, @Nonnegative int minLength, @Nonnegative int maxLength) {
        StringBuilder password = new StringBuilder();
        int length;

        if( maxLength < minLength ) {
            length = minLength;
        }
        else {
            length = minLength + random.nextInt(maxLength - minLength);
        }
        while( password.length() < length ) {
            int rnd = random.nextInt(255) + 1;
            char c;

            c = (char)(rnd%255);
            if( alphabet.contains(String.valueOf(c)) ) {
                password.append(c);
            }
        }
        return password.toString();
    }

    static public @Nonnull String generateRandomStringToken(@Nonnegative int length) {
        return generateRandomPassword(STRING_TOKEN, length, length);
    }

    static public @Nonnull String generateRandomAlphanumericToken(@Nonnegative int length) {
        return generateRandomPassword(ALPHANUM_TOKEN, length, length);
    }
}
