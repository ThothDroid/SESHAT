package com.blueapps.seshat.bitmap;

import java.io.File;

public class PNGCreator {

    private final String svg;
    private final int width;
    private final int height;
    private final File outputFile;

    public PNGCreator(String SVG, int width, int height, File outputFile) {
        this.svg = SVG;
        this.width = width;
        this.height = height;
        this.outputFile = outputFile;
    }

    public void createPNG() {
        //
    }

}
