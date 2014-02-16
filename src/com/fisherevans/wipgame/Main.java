package com.fisherevans.wipgame;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.launcher.Launcher;
import com.fisherevans.wipgame.resources.Messages;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Main {
    public static void main(String[] args) {
        new Launcher();
    }

    public static void loadResources() throws FileNotFoundException, SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        System.setProperty("java.library.path", new File("natives").getAbsolutePath());
        Messages.load();
    }

    public static void startGame(final DisplayMode displayMode, final boolean fullscreen) {
        try {
            WIP game = new WIP("WIP WIP");
            AppGameContainer gameContainer = new AppGameContainer(game);

            gameContainer.setDisplayMode(displayMode.getWidth(), displayMode.getHeight(), fullscreen);
            gameContainer.setUpdateOnlyWhenVisible(false);
            gameContainer.setAlwaysRender(true);
            gameContainer.setShowFPS(false);
            gameContainer.setMouseGrabbed(true);
            //gameContainer.setTargetFrameRate(60);
            //gameContainer.setVSync(true);

            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
