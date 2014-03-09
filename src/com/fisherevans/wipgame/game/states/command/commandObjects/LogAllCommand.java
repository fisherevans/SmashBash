package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandString;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.log.LogLevel;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class LogAllCommand extends CommandObject {
    public LogAllCommand() {
        super("logall");
    }

    @Override
    public boolean execute(CommandString c) {
        String level = c.next();

        LogLevel logLevel;
        if(level.startsWith("e"))
            logLevel = LogLevel.Error;
        else if(level.startsWith("d"))
            logLevel = LogLevel.Debug;
        else if(level.startsWith("i"))
            logLevel = LogLevel.Info;
        else if(level.startsWith("o"))
            logLevel = LogLevel.Off;
        else
            return false;
        Log.setAllLevel(logLevel);

        printGreen("Log Level set to " + logLevel.name() + " for ALL classes");
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Sets the current log level for ALL classes.");
        printGrey("Usage:logall (error|info|debug)");
    }
}
