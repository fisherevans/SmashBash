package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.log.LogLevel;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class LogCommand extends CommandObject {
    public LogCommand() {
        super("log");
    }

    @Override
    public boolean execute(CommandString c) {
        try {
            String next = c.next();
            String level = c.next();
            Class clazz = Log.getClass(next);
            if(clazz == null)
                Class.forName(next);
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
            Log.setLevel(clazz, logLevel);

            printGreen("Log Level set to " + logLevel + " for " + next);
            return true;
        } catch(Exception e) {
            printRed("Invalid class!");
            return true;
        }
    }

    @Override
    public void printHelp() {
        printGrey("Sets the current log level for a specific class.");
        printGrey("Usage:log (class name) (error|info|debug)");
    }
}
