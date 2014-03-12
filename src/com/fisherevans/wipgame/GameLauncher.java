package com.fisherevans.wipgame;

import com.fisherevans.eventRouter.EventRouter;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.launcher.Launcher;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.log.LogLevel;
import com.fisherevans.wipgame.resources.Settings;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class GameLauncher {
    private static Log log = new Log(GameLauncher.class);

    public static void main(String[] args) {
        try {
            new Launcher();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadResources() throws NoSuchFieldException, IllegalAccessException {
        try {
            Log.open();
            logBuildVersion();
            EventRouter.init();
            Settings.init();

            String dllFolder = "dll";
            System.setProperty("java.library.path", new File(dllFolder).getAbsolutePath());
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void logBuildVersion() {
        try {
            Scanner in = new Scanner(new File("res/build.txt"));
            String build = in.nextLine();
            String time = in.nextLine();
            log.info("Build Number: " + build);
            log.info("Build Time: " + time);
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Unable to parse build file:");
            log.error(e.toString());
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
