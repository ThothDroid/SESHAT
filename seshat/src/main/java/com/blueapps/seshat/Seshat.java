package com.blueapps.seshat;

import android.content.Context;

import com.blueapps.seshat.svg.SVGCreator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Seshat {

    private String glyphX;
    private String title;
    private String description;

    public Seshat(String GlyphX, String Title, String Description){
        this.glyphX = GlyphX;
        this.title = Title;
        this.description = Description;
    }

    public Document convertToSVGDocument(Context context){
        try {
            return SVGCreator.createSVG(context, glyphX, title, description);
        } catch (ParserConfigurationException | XmlPullParserException | IOException |
                 SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToSVGString(Context context) {
        return convertToXmlString(convertToSVGDocument(context));
    }

    public static Document convertToXmlDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    public static String convertToXmlString(Document xml) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "public");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            DOMSource source = new DOMSource(xml);
            transformer.transform(source, result);

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
