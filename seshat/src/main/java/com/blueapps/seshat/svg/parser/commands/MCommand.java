package com.blueapps.seshat.svg.parser.commands;

public class MCommand extends Command {

    public MCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'm';
        } else {
            commandChar = 'M';
        }
    }

}
