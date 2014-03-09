package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandString;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.log.LogLevel;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class LogStdOut extends CommandObject {
    public LogStdOut() {
        super("logstd");
    }

    @Override
    public boolean execute(CommandString c) {
        String action = c.next();
        if(action.length() == 0) {
            printGreen("Log STD OUT is currently set to: " + Log.printStdOut);
            return true;
        } else if(action.startsWith("t"))
            Log.printStdOut = true;
        else if(action.startsWith("f"))
            Log.printStdOut = false;
        printGreen("Log to STD OUT print set to: " + Log.printStdOut);
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Tells to log to print to STD or not");
        printGrey("Usage: logstd [true|false]");
    }
}
