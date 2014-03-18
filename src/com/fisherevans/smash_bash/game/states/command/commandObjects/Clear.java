package com.fisherevans.smash_bash.game.states.command.commandObjects;

import com.fisherevans.smash_bash.game.states.command.CommandObject;
import com.fisherevans.smash_bash.game.states.command.CommandState;
import com.fisherevans.smash_bash.game.states.command.CommandString;

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
