package com.blueapps.seshat.svg.parser.commands;

import java.util.ArrayList;

public class ZCommand extends Command {

    public ZCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'z';
        } else {
            commandChar = 'Z';
        }
        // Z command doesn't have any values
        values = new ArrayList<>();
    }

    @Override
    public void applyTransformation(float sx, float sy, float tx, float ty) {}
}
