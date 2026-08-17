package com.blueapps.seshat.svg.parser.commands;

public class TCommand extends Command {
    public TCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 't';
        } else {
            commandChar = 'T';
        }
    }
}
