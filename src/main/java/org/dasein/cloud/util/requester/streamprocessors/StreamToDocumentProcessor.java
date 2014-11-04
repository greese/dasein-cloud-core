package org.dasein.cloud.util.requester.streamprocessors;

import org.dasein.cloud.util.requester.streamprocessors.StreamProcessor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

 /**
 * @author Vlad Munthiu
 */
public class StreamToDocumentProcessor implements StreamProcessor<Document> {
    @Nullable
    @Override
    public Document read(InputStream inputStream, Class<Document> classType) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(inputStream);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        finally {
            inputStream.close();
        }
    }

    @Nullable
    @Override
    public String write(Document document) {
        try {
            StringWriter stringWriter = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        }
        catch (Exception ex) {
            throw new RuntimeException("Error converting XML Document to String", ex);
        }
    }
}
