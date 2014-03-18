package com.fisherevans.smash_bash.game.states.command.commandObjects;

import com.fisherevans.smash_bash.game.states.command.CommandObject;
import com.fisherevans.smash_bash.game.states.command.CommandState;
import com.fisherevans.smash_bash.game.states.command.CommandString;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class VariableSet extends CommandObject {
    public VariableSet() {
        super("set");
    }

    @Override
    public boolean execute(CommandString c) {
        String variable = c.next();
        String value = c.next();
        if(variable.length() == 0 || value.length() == 0)
            return false;
        CommandState.getVariables().put(variable, value);
        printGreen(variable + " saved!");
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Sets a variable");
        printGrey("Usage: set (variable) (value)");
    }
}
