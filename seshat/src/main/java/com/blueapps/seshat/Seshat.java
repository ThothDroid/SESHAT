package com.blueapps.seshat;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import androidx.annotation.ColorInt;

import com.blueapps.maat.BoundProperty;
import com.blueapps.seshat.bitmap.BitmapCreator;
import com.blueapps.seshat.svg.SVGCreator;
import com.caverock.androidsvg.SVGParseException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Seshat {

    private String glyphX = "";
    private final ArrayList<SeshatListener> listeners = new ArrayList<>();
    private final Handler handler;

    // basic properties
    private float textSize = 100f;
    private int verticalOrientation = BoundProperty.VERTICAL_ORIENTATION_MIDDLE;
    private int writingDirection = BoundProperty.WRITING_DIRECTION_LTR;
    private int writingLayout = BoundProperty.WRITING_LAYOUT_LINES;
    private boolean drawLines = false;
    private boolean roundLineCap = true;
    private float lineThickness = 2f;
    private float pagePaddingLeft = 0f;
    private float pagePaddingTop = 0f;
    private float pagePaddingRight = 0f;
    private float pagePaddingBottom = 0f;
    private float signPadding = 10f;
    private float layoutSignPadding = 5f;
    private float interLinePadding = 25f;
    private @ColorInt int backgroundColor = Color.WHITE;
    private @ColorInt int primarySignColor = Color.BLACK;

    public Seshat(String GlyphX, Handler handler){
        this.glyphX = GlyphX;
        this.handler = handler;
    }

    public Seshat(Handler handler){
        this.handler = handler;
    }

    private Document createSVGDocument(Context context, String title, String description, boolean backgroundWithCSS, boolean backgroundTransparent) {
        try {
            BoundProperty property = new BoundProperty(0, 0, textSize, verticalOrientation, writingDirection,
                    writingLayout, drawLines, lineThickness, pagePaddingLeft, pagePaddingTop,
                    pagePaddingRight, pagePaddingBottom, signPadding, layoutSignPadding, interLinePadding);
            return SVGCreator.createSVG(context, this, glyphX, property, title, description, backgroundWithCSS, backgroundTransparent, backgroundColor, primarySignColor, roundLineCap);
        } catch (ParserConfigurationException | XmlPullParserException | IOException |
                 SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public Document convertToSVGDocument(Context context, String title, String description, boolean backgroundWithCSS, boolean backgroundTransparent) {
        this.onExportStarted();
        Document document = createSVGDocument(context, title, description, backgroundWithCSS, backgroundTransparent);
        this.onExportCompleted();
        return document;
    }

    private String createSVGString(Context context, String title, String description, boolean backgroundWithCSS, boolean backgroundTransparent) {
        return convertToXmlString(createSVGDocument(context, title, description, backgroundWithCSS, backgroundTransparent));
    }

    public String convertToSVGString(Context context, String title, String description, boolean backgroundWithCSS, boolean backgroundTransparent) {
        this.onExportStarted();
        String svgString = createSVGString(context, title, description, backgroundWithCSS, backgroundTransparent);
        this.onExportCompleted();
        return svgString;
    }

    public void convertToPNGFile(Context context, File outputFile, int width, int height, int quality, boolean backgroundTransparent) {
        this.onExportStarted();
        String svgString = createSVGString(context, null, null, false, backgroundTransparent);
        BitmapCreator bitmapCreator = new BitmapCreator(svgString, width, height, quality, outputFile);
        try {
            bitmapCreator.createPNG();
            this.onExportCompleted();
        } catch (SVGParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertToJPGFile(Context context, File outputFile, int width, int height, int quality, boolean backgroundTransparent) {
        this.onExportStarted();
        String svgString = createSVGString(context, null, null, false, backgroundTransparent);
        BitmapCreator bitmapCreator = new BitmapCreator(svgString, width, height, quality, outputFile);
        try {
            bitmapCreator.createJPG();
            this.onExportCompleted();
        } catch (SVGParseException | IOException e) {
            throw new RuntimeException(e);
        }
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

    // Getters and Setters for properties

    public String getGlyphX() {
        return glyphX;
    }

    public void setGlyphX(String glyphX) {
        this.glyphX = glyphX;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getVerticalOrientation() {
        return verticalOrientation;
    }

    public void setVerticalOrientation(int verticalOrientation) {
        this.verticalOrientation = verticalOrientation;
    }

    public int getWritingDirection() {
        return writingDirection;
    }

    public void setWritingDirection(int writingDirection) {
        this.writingDirection = writingDirection;
    }

    public int getWritingLayout() {
        return writingLayout;
    }

    public void setWritingLayout(int writingLayout) {
        this.writingLayout = writingLayout;
    }

    public boolean isDrawLines() {
        return drawLines;
    }

    public void setDrawLines(boolean drawLines) {
        this.drawLines = drawLines;
    }

    public boolean isRoundLineCap() {
        return roundLineCap;
    }

    public void setRoundLineCap(boolean roundLineCap) {
        this.roundLineCap = roundLineCap;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
    }

    public float getPagePaddingLeft() {
        return pagePaddingLeft;
    }

    public void setPagePaddingLeft(float pagePaddingLeft) {
        this.pagePaddingLeft = pagePaddingLeft;
    }

    public float getPagePaddingTop() {
        return pagePaddingTop;
    }

    public void setPagePaddingTop(float pagePaddingTop) {
        this.pagePaddingTop = pagePaddingTop;
    }

    public float getPagePaddingRight() {
        return pagePaddingRight;
    }

    public void setPagePaddingRight(float pagePaddingRight) {
        this.pagePaddingRight = pagePaddingRight;
    }

    public float getPagePaddingBottom() {
        return pagePaddingBottom;
    }

    public void setPagePaddingBottom(float pagePaddingBottom) {
        this.pagePaddingBottom = pagePaddingBottom;
    }

    public float getSignPadding() {
        return signPadding;
    }

    public void setSignPadding(float signPadding) {
        this.signPadding = signPadding;
    }

    public float getLayoutSignPadding() {
        return layoutSignPadding;
    }

    public void setLayoutSignPadding(float layoutSignPadding) {
        this.layoutSignPadding = layoutSignPadding;
    }

    public float getInterLinePadding() {
        return interLinePadding;
    }

    public void setInterLinePadding(float interLinePadding) {
        this.interLinePadding = interLinePadding;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getPrimarySignColor() {
        return primarySignColor;
    }

    public void setPrimarySignColor(@ColorInt int primarySignColor) {
        this.primarySignColor = primarySignColor;
    }

    public void addSeshatListener(SeshatListener listener){
        listeners.add(listener);
    }

    public void onExportStarted(){
        handler.post(() -> {
            for (SeshatListener listener : listeners) {
                listener.onExportStarted();
            }
        });
    }

    public void onExportProgress(int progress, int total){
        handler.post(() -> {
            for (SeshatListener listener : listeners) {
                listener.onExportProgress(progress, total);
            }
        });
    }

    public void onPostProcessingStarted(){
        handler.post(() -> {
            for (SeshatListener listener : listeners) {
                listener.onPostProcessingStarted();
            }
        });
    }

    public void onExportCompleted(){
        handler.post(() -> {
            for (SeshatListener listener : listeners) {
                listener.onExportCompleted();
            }
        });
    }
}
