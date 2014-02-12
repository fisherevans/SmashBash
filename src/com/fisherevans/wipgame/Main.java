package com.fisherevans.wipgame;

import com.fisherevans.wipgame.game.Game;
import com.fisherevans.wipgame.launcher.Launcher;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
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
    public static AppGameContainer gameContainer;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
    }

    public static void loadResources() throws FileNotFoundException, SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        Messages.load();
    }

    public static void startGame(final DisplayMode displayMode, final boolean fullscreen) {
        try {
            Game game = new Game("WIP Game");
            gameContainer = new AppGameContainer(game);
            gameContainer.setDisplayMode(displayMode.getWidth(), displayMode.getHeight(), fullscreen);
            gameContainer.setUpdateOnlyWhenVisible(false);
            gameContainer.setAlwaysRender(true);
            gameContainer.setShowFPS(false);
            gameContainer.setTargetFrameRate(60);
            gameContainer.setVSync(true);
            gameContainer.setMouseGrabbed(true);
            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
