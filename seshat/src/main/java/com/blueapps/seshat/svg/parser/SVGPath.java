package com.blueapps.seshat.svg.parser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.blueapps.seshat.svg.parser.commands.CCommand;
import com.blueapps.seshat.svg.parser.commands.Command;
import com.blueapps.seshat.svg.parser.commands.HCommand;
import com.blueapps.seshat.svg.parser.commands.LCommand;
import com.blueapps.seshat.svg.parser.commands.MCommand;
import com.blueapps.seshat.svg.parser.commands.QCommand;
import com.blueapps.seshat.svg.parser.commands.SCommand;
import com.blueapps.seshat.svg.parser.commands.TCommand;
import com.blueapps.seshat.svg.parser.commands.VCommand;
import com.blueapps.seshat.svg.parser.commands.ZCommand;

import java.util.ArrayList;
import java.util.Arrays;

public class SVGPath {
    
    private static final String TAG = "SVGPath";
    
    private ArrayList<Command> commands = new ArrayList<>();
    
    public SVGPath(String path){
        // parse path

        // Split String on commands
        ArrayList<String> stringCommands = new ArrayList<>(Arrays.asList(path.split("(?=[a-zA-Z])")));
        Log.d(TAG, stringCommands.toString());

        for (String command: stringCommands){
            if (!command.isEmpty()) {
                char c = command.charAt(0);
                if (c == 'C' || c == 'c'){
                    commands.add(new CCommand(command));
                } else if (c == 'H' || c == 'h') {
                    commands.add(new HCommand(command));
                } else if (c == 'L' || c == 'l') {
                    commands.add(new LCommand(command));
                } else if (c == 'M' || c == 'm'){
                    commands.add(new MCommand(command));
                } else if (c == 'Q' || c == 'q') {
                    commands.add(new QCommand(command));
                } else if (c == 'S' || c == 's') {
                    commands.add(new SCommand(command));
                } else if (c == 'T' || c == 't') {
                    commands.add(new TCommand(command));
                } else if (c == 'V' || c == 'v') {
                    commands.add(new VCommand(command));
                } else if (c == 'Z' || c == 'z') {
                    commands.add(new ZCommand(command));
                } else {
                    commands.add(new Command(command));
                }
            }
        }
    }

    public void applyTransformation(float sx, float sy, float tx, float ty){
        for (Command command: commands){
            command.applyTransformation(sx, sy, tx, ty);
        }
    }

    @NonNull
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        int counter = 0;
        for (Command command: commands){
            stringBuilder.append(command.toString());
            if (counter != commands.size() - 1){
                stringBuilder.append(' ');
            }
        }

        return stringBuilder.toString();
    }
    
}
