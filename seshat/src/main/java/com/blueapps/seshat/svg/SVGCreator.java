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

    public static Document createSVG(Context context, String glyphX) throws ParserConfigurationException, XmlPullParserException, IOException {

        // create Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document svg = builder.newDocument();

        // create root element
        Element root = svg.createElement("svg");
        svg.appendChild(root);

        // get image
        SignProvider signProvider = new SignProvider(context);
        Drawable sign = signProvider.getSign(glyphX);


        return svg;
    }

}
