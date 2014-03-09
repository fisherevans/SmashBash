package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class VariableDelete extends CommandObject {
    public VariableDelete() {
        super("del");
    }

    @Override
    public boolean execute(CommandString c) {
        String variable = c.next();
        if(variable.length() == 0)
            return false;
        if(CommandState.getVariables().remove(variable) == null)
            printRed(variable + " did not already exist!");
        else
            printGreen(variable + " removed!");
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Unsets/deletes a variable.");
        printGrey("Usage: del (variable)");
    }
}
