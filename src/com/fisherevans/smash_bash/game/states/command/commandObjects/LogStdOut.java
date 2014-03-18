package com.fisherevans.smash_bash.game.states.command.commandObjects;

import com.fisherevans.smash_bash.game.states.command.CommandObject;
import com.fisherevans.smash_bash.game.states.command.CommandString;
import com.fisherevans.smash_bash.log.Log;

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
