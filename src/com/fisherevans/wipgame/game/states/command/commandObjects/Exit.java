package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandString;

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
        WIP.exitGame();
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Completely exit's the game");
        printGrey("Usage: exit)");
    }
}
