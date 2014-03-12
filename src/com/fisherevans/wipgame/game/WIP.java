package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.GameSettings;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.help.HelpState;
import com.fisherevans.wipgame.game.states.loading.LoadingState;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class WIP extends StateBasedGame {
    public static final int STATE_COMMAND = -999;
    public final static int STATE_LOADING = 0;
    public final static int STATE_START = 1;
    public final static int STATE_READY = 2;
    public final static int STATE_PLAY = 3;
    public final static int STATE_PAUSE = 4;
    public final static int STATE_RESULTS = 5;
    public final static int STATE_CONFIRM = 6;
    public final static int STATE_HELP = 7;

    public static WIP game;
    public static GameContainer container;
    public static boolean debug = false;
    public static GameSettings gameSettings;

    public static final Log log = new Log(WIP.class);

    public static Map<Integer, Class> overlayStates = new HashMap<>();

    public WIP(String name) {
        super(name);
        game = this;
        gameSettings = new GameSettings();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
        addState(new LoadingState());
        overlayStates.put(Input.KEY_F1, HelpState.class);
        overlayStates.put(Input.KEY_F12, CommandState.class);
    }

    public static WIPState currentState() {
        return (WIPState) game.getCurrentState();
    }

    public static float width() {
        return container.getWidth();
    }

    public static float height() {
        return container.getHeight();
    }

    public static void enterNewState(WIPState state) {
        state.init();
        enterState(state);
    }

    public static void enterNewState(WIPState state, Transition leave, Transition enter) {
        state.init();
        enterState(state, leave, enter);
    }

    public static void enterState(WIPState state) {
        game.addState(state);
        game.enterState(state.getID());
    }

    public static void enterState(WIPState state, Transition leave, Transition enter) {
        game.addState(state);
        game.enterState(state.getID(), leave, enter);
    }

    public static void exitGame() {
        Log.close();
        System.exit(0);
    }

    public static void toggleOverlay(int keyCode) {
        Class overlayClass = WIP.overlayStates.get(keyCode);
        if(overlayClass != null) {
            try {
                WIPState current = WIP.currentState();
                if(current.getClass().equals(overlayClass)) { // leave overlay
                    WIP.enterState(((OverlayState)current).getOverlayedState());
                } else if(current instanceof OverlayState) { // switch overlays
                    WIP.enterNewState(OverlayState.create(overlayClass, ((OverlayState)current).getOverlayedState()));
                } else {
                    WIP.enterState(OverlayState.create(overlayClass, current));
                }
            } catch(Exception e) {
                log.error("Failed to load overlay state!");
                log.error(e.toString());
            }
        }
    }

    public static void saveScreenShot() {
            saveScreenShot(new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS").format(new Date()));
    }

    public static void saveScreenShot(String filename) {
        try {
            filename += ".png";
            File saveDir = new File("screenshots");
            if(!saveDir.exists());
            saveDir.mkdir();
            Image ss = new Image((int)WIP.width(), (int)WIP.height());
            container.getGraphics().copyArea(ss, 0, 0);
            File screenshotFile = new File("screenshots/" + filename);
            FileOutputStream stream = new FileOutputStream(screenshotFile);
            ImageIOWriter writer = new ImageIOWriter();
            writer.saveImage(ss, "png", stream, false);
            stream.close();
            WIP.log.info("Screenshot saved as: " + filename);
        } catch(Exception e) {
            WIP.log.error("Failed to save screenshot!");
            WIP.log.error(e.toString());
        }
    }
}
