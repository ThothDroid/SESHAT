package com.blueapps.seshat.svg;

import android.content.Context;

import com.blueapps.signprovider.SignProvider;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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

    public static Document createSVG(Context context, String glyphX, String title, String description) throws ParserConfigurationException, XmlPullParserException, IOException {

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

        // create the <path> element
        Element path = svg.createElement(SVG_PATH_TAG);
        // get sign path
        SignProvider signProvider = new SignProvider(context);
        String signPath = signProvider.getSignPathData(glyphX);
        // set the "d" attribute of the <path> element
        path.setAttribute(SVG_PATH_ATTRIBUTE_D, signPath);
        // add the <path> element to the root element
        root.appendChild(path);

        // set the viewBox attribute for the root element
        root.setAttribute(SVG_VIEWBOX_ATTRIBUTE, "0 0 1200 1200");

        return svg;
    }

}
