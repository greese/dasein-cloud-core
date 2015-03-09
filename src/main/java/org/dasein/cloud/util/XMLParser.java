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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

/**
 * A special class to minimize the issues associated with synchronization bugs in Xerces. It sucks that we have to do this,
 * but it has to be done.
 * <p>Created by George Reese: 2/21/13 10:16 AM</p>
 * @author George Reese
 * @version 2013.04
 * @since 2013.04
 */
public class XMLParser {
    static public class DBPair {
        public DocumentBuilderFactory factory;
        public DocumentBuilder builder;

        public void release() {
            builder.reset();
            synchronized( xercesSucksWorkaround ) {
                xercesSucksWorkaround.push(this);
            }
        }
    }

    static private final Stack<DBPair> xercesSucksWorkaround = new Stack<DBPair>();

    static public @Nonnull DBPair getCachedDocumentBuilder() throws ParserConfigurationException {
        synchronized( xercesSucksWorkaround ) {
            if( !xercesSucksWorkaround.isEmpty() ) {
                return xercesSucksWorkaround.pop();
            }
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();

        DBPair pair = new DBPair();

        pair.factory = dbf;
        pair.builder = builder;
        return pair;
    }

    static public Document parse(@Nonnull InputStream input) throws ParserConfigurationException, IOException, SAXException {
        DBPair pair = getCachedDocumentBuilder();

        try {
            Document doc = pair.builder.parse(input);

            input.close();
            return doc;
        }
        finally {
            pair.release();
        }
    }
}
