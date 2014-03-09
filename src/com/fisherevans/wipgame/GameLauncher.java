package com.fisherevans.wipgame;

import com.fisherevans.eventRouter.EventRouter;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.launcher.Launcher;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Messages;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class GameLauncher {

    public static void main(String[] args) {
        try {
            loadResources();
            new Launcher();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadResources() throws NoSuchFieldException, IllegalAccessException {
        Log.open();
        String dllFolder = "dll";
        System.setProperty("java.library.path", new File(dllFolder).getAbsolutePath());
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
        EventRouter.init();
        try {
            Messages.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void startGame(final DisplayMode displayMode, final boolean fullscreen) {
        try {
            WIP game = new WIP("WIPGame");
            AppGameContainer gameContainer = new AppGameContainer(game);

            gameContainer.setDisplayMode(displayMode.getWidth(), displayMode.getHeight(), fullscreen);
            gameContainer.setUpdateOnlyWhenVisible(false);
            gameContainer.setAlwaysRender(true);
            gameContainer.setShowFPS(false);
            gameContainer.setMouseGrabbed(true);
            gameContainer.setIcon("res/img/icon.png");
            gameContainer.setVSync(true);
            //gameContainer.setTargetFrameRate(60);

            gameContainer.start();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
