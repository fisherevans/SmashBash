package com.fisherevans.smash_bash.game.states.command.commandObjects;

import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.states.command.CommandObject;
import com.fisherevans.smash_bash.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class Exit extends CommandObject {
    public Exit() {
        super("exit");
    }

    @Override
    public boolean execute(CommandString c) {
        printGreen("Closing the game");
        SmashBash.exitGame();
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Completely exit's the game");
        printGrey("Usage: exit)");
    }
}
