package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class WhatIs extends CommandObject {
    public WhatIs() {
        super("whatis");
    }

    @Override
    public boolean execute(CommandString c) {
        Object current = WIP.game;
        try {
            String code;
            while(true) {
                code = c.next(".");
                if(code.equals(""))
                    break;

                try {
                    Field f = current.getClass().getField(code);
                    if(Modifier.isStatic(f.getModifiers())) {
                        current = f.get(current);
                        continue;
                    }
                } catch(Exception e) { }

                try {
                    Method m = current.getClass().getMethod(code);
                    if(m.getGenericReturnType() == Void.TYPE)
                        current = null;
                    else
                        current = m.invoke(current);
                    continue;
                } catch(Exception e) { }
            }
        } catch(Exception e) {
            printRed("Error while running command!");
            printRed(e.toString());
            return true;
        }
        if(current == null)
            printGreen("Return: NULL");
        else
            printGreen("Return: " + current.toString());
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Queries/runs static variables and parameterless method calls starting from WIP.game.");
        printGrey("Usage: whatis (the code)");
    }
}
