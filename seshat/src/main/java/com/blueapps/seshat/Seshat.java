package com.blueapps.seshat;

import android.content.Context;

import com.blueapps.maat.BoundProperty;
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

    // basic properties
    private float textSize = 40f;
    private int verticalOrientation = BoundProperty.VERTICAL_ORIENTATION_MIDDLE;
    private int writingDirection = BoundProperty.WRITING_DIRECTION_LTR;
    private int writingLayout = BoundProperty.WRITING_LAYOUT_LINES;
    private boolean drawLines = false;
    private float lineThickness = 0f;
    private float pagePaddingLeft = 0f;
    private float pagePaddingTop = 0f;
    private float pagePaddingRight = 0f;
    private float pagePaddingBottom = 0f;
    private float signPadding = 0f;
    private float layoutSignPadding = 0f;
    private float interLinePadding = 0f;

    public Seshat(String GlyphX){
        this.glyphX = GlyphX;
    }

    public Document convertToSVGDocument(Context context, String title, String description) {
        try {
            BoundProperty property = new BoundProperty(0,0,40,1,0,0,false,0,0,0,0,0,0,0,0);
            return SVGCreator.createSVG(context, glyphX, property, title, description);
        } catch (ParserConfigurationException | XmlPullParserException | IOException |
                 SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToSVGString(Context context, String title, String description) {
        return convertToXmlString(convertToSVGDocument(context, title, description));
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
