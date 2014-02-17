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
        loadResources();
        new Launcher();
    }

    public static void loadResources()  {
        System.setProperty("java.library.path", new File("natives").getAbsolutePath());
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            Messages.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
            gameContainer.setIcon("res/img/icon.png");
            //gameContainer.setTargetFrameRate(60);
            //gameContainer.setVSync(true);

            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
