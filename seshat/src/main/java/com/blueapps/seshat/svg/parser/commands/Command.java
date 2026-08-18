package com.blueapps.seshat.svg.parser.commands;

import android.util.Log;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {

    private static final String TAG = "Command";

    protected boolean relative;
    protected ArrayList<Float> values = new ArrayList<>();

    protected char commandChar = 'M';

    public Command(String pathFragment){
        if (!pathFragment.isEmpty()){
            char c = pathFragment.charAt(0);
            relative = c < 65 || c > 90;    // check if uppercase
            setValues(pathFragment.substring(1));
        } else {
            throw new RuntimeException("Command empty!");
        }
    }

    public void setValues(String values){
        if (!values.isEmpty()){

            // Split String on commands
            ArrayList<String> stringValues = new ArrayList<>(Arrays.asList(values.split("(?=[ +-,])")));
            Log.d(TAG, stringValues.toString());

            for (String stringValue: stringValues){
                if (!stringValue.isEmpty()) {
                    stringValue = StringUtils.remove(stringValue, ',');
                    stringValue = StringUtils.remove(stringValue, ' ');
                    try {
                        this.values.add(Float.parseFloat(stringValue));
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }

    public void applyTransformation(float sx, float sy, float tx, float ty){
        int counter = 0;
        for (float value: values){
            if (counter%2 != 0){
                // uneven
                if (!relative) value += tx;
                value *= sx;
            } else {
                // even
                if (!relative) value += ty;
                value *= sy;
            }
            values.set(counter, value);
            counter++;
        }
    }

    @NonNull
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(commandChar);

        int counter = 0;
        for (float value: values){
            if (counter == 0){
                stringBuilder.append(' ');
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append(value);
            counter++;
        }

        return stringBuilder.toString();
    }

}
