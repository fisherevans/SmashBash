package com.fisherevans.wipgame;

import com.fisherevans.ant.ImageGenerator;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.launcher.Launcher;
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
public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.arch"));
        if(args.length > 0 && args[0].equals("generate")) {
            System.out.println("Pre-generating resized images");
            generateImages("128 16,32,48,64,96,128 out/dist/WIPGame/res/img/sprites/characters png".split(" "));
            generateImages("128 16,32,48,64,96,128 out/dist/WIPGame/res/maps png".split(" "));
        } else {
            try {
                if(new File("res/build.txt").exists()) {
                    loadResources();
                    new Launcher();
                } else {
                    System.out.println("Invalid build.");
                    System.exit(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateImages(String[] args) {
        int baseSize = Integer.parseInt(args[0]);

        String[] newSizesSplit = args[1].split(",");
        int[] newSizes = new int[newSizesSplit.length];
        for(int id = 0;id < newSizesSplit.length;id++)
            newSizes[id] = Integer.parseInt(newSizesSplit[id]);

        String dir = args[2];
        String format = args[3];

        ImageGenerator.run(baseSize, newSizes, dir, format);
    }

    public static void loadResources() throws NoSuchFieldException, IllegalAccessException {
        String dllFolder = "dll";
        System.setProperty("java.library.path", new File(dllFolder).getAbsolutePath());
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
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
