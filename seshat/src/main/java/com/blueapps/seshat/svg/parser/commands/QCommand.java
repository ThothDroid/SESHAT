package com.blueapps.seshat.svg.parser.commands;

public class QCommand extends Command {
    public QCommand(String pathFragment) {
        super(pathFragment);
        if (relative){
            commandChar = 'q';
        } else {
            commandChar = 'Q';
        }
    }
}
