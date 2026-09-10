package com.blueapps.seshat.svg.parser.commands;

public class VCommand extends Command {
    public VCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'v';
        } else {
            commandChar = 'V';
        }
    }

    @Override
    public void applyTransformation(float sx, float sy, float tx, float ty) {

        int counter = 0;
        for (float value: values){
            value *= sy;
            if (!relative) value += ty;
            values.set(counter, value);
            counter++;
        }

    }
}
