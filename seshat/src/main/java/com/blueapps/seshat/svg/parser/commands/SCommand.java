package com.blueapps.seshat.svg.parser.commands;

public class SCommand extends Command {
    public SCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 's';
        } else {
            commandChar = 'S';
        }
    }

}
