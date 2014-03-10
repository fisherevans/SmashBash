package com.fisherevans.wipgame.game.states.command.commandObjects;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.command.CommandObject;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.command.CommandString;
import org.newdawn.slick.Image;
import org.newdawn.slick.imageout.ImageIOWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class ScreenShot extends CommandObject {
    public ScreenShot() {
        super("ss");
    }

    @Override
    public boolean execute(CommandString c) {
        try {
            String filename = WIP.saveScreenShot(c.getRemaining());
            printGrey("Screenshot saved as: " + filename);
        } catch(Exception e) {
            printRed("Failed to save screenshot!");
            printRed(e.toString());
        }
        return true;
    }

    @Override
    public void printHelp() {
        printGrey("Saves a screenshot (of the current state) as a PNG.");
        printGrey("Usage: ss [filename]");
    }
}
