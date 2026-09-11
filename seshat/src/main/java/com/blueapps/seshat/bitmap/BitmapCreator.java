package com.blueapps.seshat.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapCreator {

    private final String stringSVG;
    private final int width;
    private final int height;
    private final int quality;
    private final File outputFile;

    public BitmapCreator(String stringSVG, int width, int height, int quality, File outputFile) {
        this.stringSVG = stringSVG;
        this.width = width;
        this.height = height;
        this.quality = quality;
        this.outputFile = outputFile;
    }

    public void createPNG() throws SVGParseException, IOException {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        SVG svg = SVG.getFromString(stringSVG);
        svg.renderToCanvas(canvas);

        try (FileOutputStream out = new FileOutputStream(outputFile)){
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
        } finally {
            bitmap.recycle();
        }
    }

}
