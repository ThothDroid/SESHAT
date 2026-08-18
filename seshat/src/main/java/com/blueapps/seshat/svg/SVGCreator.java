package com.blueapps.seshat.svg;

import static com.blueapps.seshat.svg.parser.PathTransformer.applyBound;

import android.content.Context;
import android.graphics.Rect;

import com.blueapps.maat.BoundCalculation;
import com.blueapps.maat.BoundProperty;
import com.blueapps.maat.ValuePair;
import com.blueapps.signprovider.SignProvider;
import com.blueapps.signprovider.SvgData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SVGCreator {

    // Constants for SVG
    public static final String SVG_ROOT_TAG = "svg";
    public static final String SVG_XMLNS_ATTRIBUTE = "xmlns";
    public static final String SVG_XMLNS_VALUE = "http://www.w3.org/2000/svg";
    public static final String SVG_VIEWBOX_ATTRIBUTE = "viewBox";
    public static final String SVG_TITLE_TAG = "title";
    public static final String SVG_DESC_TAG = "desc";
    public static final String SVG_ID_TAG = "id";
    public static final String SVG_PATH_TAG = "path";
    public static final String SVG_PATH_ATTRIBUTE_D = "d";

    public static Document createSVG(Context context, String glyphX, String title, String description) throws ParserConfigurationException, XmlPullParserException, IOException, SAXException {

        // create Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document svg = builder.newDocument();

        // create root element
        Element root = svg.createElement(SVG_ROOT_TAG);
        svg.appendChild(root);
        // set the xmlns attribute for the root element
        root.setAttribute(SVG_XMLNS_ATTRIBUTE, SVG_XMLNS_VALUE);

        // set title and description if provided
        if(title != null){
            Element titleElement = svg.createElement(SVG_TITLE_TAG);
            titleElement.setAttribute(SVG_ID_TAG, title);
            root.appendChild(titleElement);
        }
        if(description != null){
            Element descElement = svg.createElement(SVG_DESC_TAG);
            descElement.setAttribute(SVG_ID_TAG, description);
            root.appendChild(descElement);
        }

        // Convert String to XmlDocument
        InputStream inputStream = new ByteArrayInputStream(glyphX.getBytes(StandardCharsets.UTF_8));
        Document document = builder.parse(inputStream);

        BoundCalculation boundCalculation = attachSignChildren(context, svg, root, document);

        // set the viewBox attribute for the root element
        root.setAttribute(SVG_VIEWBOX_ATTRIBUTE, "0 0 " + boundCalculation.getWidth() + " " + boundCalculation.getHeight());
        //root.setAttribute(SVG_VIEWBOX_ATTRIBUTE, "0 0 1000 1000");

        return svg;
    }

    private static BoundCalculation attachSignChildren(Context context, Document svg, Element root, Document document) throws XmlPullParserException, IOException, SAXException {
        BoundCalculation boundCalculation = new BoundCalculation(document);
        ArrayList<String> ids = boundCalculation.getIds(false, false);

        ArrayList<String> paths = new ArrayList<>();
        ArrayList<ValuePair<Float, Float>> dimensions = new ArrayList<>();
        SignProvider signProvider = new SignProvider(context);
        for (String id : ids) {
            SvgData svgData = signProvider.getSvgData(id);
            if (svgData != null) {
                paths.add(svgData.getPathData());
                // Extract width and height
                String widthStr = svgData.getWidth();
                String heightStr = svgData.getHeight();
                if (widthStr != null && heightStr != null) {
                    try {
                        float width = Float.parseFloat(widthStr);
                        float height = Float.parseFloat(heightStr);
                        dimensions.add(new ValuePair<>(width, height));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException(" Width or Height is null for id: " + id);
                }
            }
        }

        BoundProperty boundProperty = new BoundProperty(0,0,40,1,0,0,false,0,0,0,0,0,0,0,0);
        ArrayList<Rect> bounds = boundCalculation.getBounds(dimensions, boundProperty);

        int counter = 0;
        for (Rect bound : bounds) {
            // create the <path> element
            Element path = svg.createElement(SVG_PATH_TAG);
            // get sign path
            String signPath = paths.get(counter);
            // apply  transformation to the sign path based on the bounds
            signPath = applyBound(signPath, bound, dimensions.get(counter).getKey(), dimensions.get(counter).getValue());
            // set the "d" attribute of the <path> element
            path.setAttribute(SVG_PATH_ATTRIBUTE_D, signPath);
            // add the <path> element to the root element
            root.appendChild(path);
            counter++;
        }

        return boundCalculation;
    }

}
