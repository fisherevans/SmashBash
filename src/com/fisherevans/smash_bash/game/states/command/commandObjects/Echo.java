package com.fisherevans.smash_bash.game.states.command.commandObjects;

import com.fisherevans.smash_bash.game.states.command.CommandObject;
import com.fisherevans.smash_bash.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class Echo extends CommandObject {
    public Echo() {
        super("echo");
    }

    @Override
    public boolean execute(CommandString c) {
        if(c.getRemaining().length() == 0)
            return false;
        printGrey(c.getRemaining());
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Echo's out a string after parsing variables.");
        printGrey("Usage: echo (text including variables)");
    }
}
