package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class VariablePrint extends CommandObject {
    public VariablePrint() {
        super("print");
    }

    @Override
    public boolean execute(CommandString c) {
        String variable = c.next();
        if(variable.length() == 0)
            return false;
        Object value = CommandState.getVariables().get(variable);
        if(value == null)
            printBlue("NULL");
        else
            printGrey(value.toString());
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Displays the given variable's value");
        printGrey("Usage: print (variable)");
    }
}
