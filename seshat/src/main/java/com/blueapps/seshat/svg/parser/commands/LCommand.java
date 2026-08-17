package com.blueapps.seshat.svg.parser.commands;

public class LCommand extends Command {

    public LCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'l';
        } else {
            commandChar = 'L';
        }
    }

}
