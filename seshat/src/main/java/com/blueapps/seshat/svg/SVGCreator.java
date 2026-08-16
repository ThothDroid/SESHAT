package com.blueapps.seshat.svg;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
    public static final String SVG_PATH_TAG = "path";
    public static final String SVG_PATH_ATTRIBUTE_D = "d";

    public static Document createSVG(Context context, String glyphX) throws ParserConfigurationException, XmlPullParserException, IOException {

        // create Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document svg = builder.newDocument();

        // create root element
        Element root = svg.createElement(SVG_ROOT_TAG);
        svg.appendChild(root);

        // create the <path> element
        Element path = svg.createElement(SVG_PATH_TAG);
        // get sign path
        SignProvider signProvider = new SignProvider(context);
        String signPath = signProvider.getSignPathData(glyphX);
        // set the "d" attribute of the <path> element
        path.setAttribute(SVG_PATH_ATTRIBUTE_D, signPath);
        // add the <path> element to the root element
        root.appendChild(path);

        return svg;
    }

}
