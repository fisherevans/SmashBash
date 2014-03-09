package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.eventRouter.EventRouter;
import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class Event extends CommandObject {
    public Event() {
        super("event");
    }

    @Override
    public boolean execute(CommandString c) {
        try {
            String channel = c.next();
            Long action = Long.parseLong(c.next());
            List<Object> args = new ArrayList<>();
            String arg;
            while(true) {
                arg = c.next();
                if(arg.equals(""))
                    break;
            }
            EventRouter.send(channel, action, args.toArray(new Object[0]));
        } catch(Exception e) {
            printRed("Failed to call the event listener!");
            printRed(e.toString());
        }

        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Calls and event listener");
        printGrey("Usage:event (channel) (action) [argument 1 ... argument x]");
    }
}
