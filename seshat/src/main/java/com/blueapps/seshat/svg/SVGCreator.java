package com.blueapps.seshat.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SVGCreator {

    public static Document createSVG(String glyphX) throws ParserConfigurationException {

        // create Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document svg = builder.newDocument();

        // create root element
        Element root = svg.createElement("svg");
        svg.appendChild(root);

        return svg;
    }

}
