package com.blueapps.seshat.svg.parser.commands;

public class CCommand extends Command {
    public CCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'c';
        } else {
            commandChar = 'C';
        }
    }

}
