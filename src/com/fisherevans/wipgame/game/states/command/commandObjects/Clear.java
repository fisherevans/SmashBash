package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class Clear extends CommandObject {
    public Clear() {
        super("clear");
    }

    @Override
    public boolean execute(CommandString c) {
        CommandState.clear();
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Clears the console");
        printGrey("Usage:clear");
    }
}
