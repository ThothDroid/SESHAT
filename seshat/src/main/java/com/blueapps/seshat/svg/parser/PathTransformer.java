package com.blueapps.seshat.svg.parser;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class PathTransformer {

    private static final String TAG = "PathTransformer";

    public static String applyBound(String path, Rect bound, float width, float height){

        // Calculate transformation
        float sx = bound.width() / width;
        float sy = bound.height() / height;

        SVGPath svgPath = new SVGPath(path);
        svgPath.applyTransformation(sx, sy, bound.left, bound.top);
        path = svgPath.toString();

        return path;
    }

    public static String mirrorPathVertically(String path, float width){

        SVGPath svgPath = new SVGPath(path);
        svgPath.mirrorVertically(width);

        return svgPath.toString();

    }

}
