package com.blueapps.seshat.svg.parser.commands;

public class HCommand extends Command {
    public HCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'h';
        } else {
            commandChar = 'H';
        }
    }

    @Override
    public void applyTransformation(float sx, float sy, float tx, float ty) {

        int counter = 0;
        for (float value: values){
            value *= sx;
            if (!relative) value += tx;
            values.set(counter, value);
            counter++;
        }

    }
}
