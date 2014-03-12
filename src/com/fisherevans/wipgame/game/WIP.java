package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.GameSettings;
import com.fisherevans.wipgame.game.states.command.CommandState;
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

    public static WIP game;
    public static GameContainer container;
    public static boolean debug = false;
    public static GameSettings gameSettings;

    public static final Log log = new Log(WIP.class);

    public WIP(String name) {
        super(name);
        game = this;
        gameSettings = new GameSettings();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
        addState(new LoadingState());
    }

    public static WIPState currentState() {
        WIPState state = (WIPState) game.getCurrentState();
        if(state instanceof CommandState)
            return ((CommandState)state).getCurrentState();
        else
            return state;
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

    public static String saveScreenShot(String filename) throws Exception {
        if(filename.length() == 0)
            filename = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss.SSS").format(new Date());
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
        return filename;
    }

    public static void drawKey(Graphics gfx, Color color, String key, int x, int y, int size) {
        int keySize = (int)(size*1.25f);
        gfx.setColor(color);
        gfx.setLineWidth(5);
        gfx.setAntiAlias(true);
        gfx.drawRoundRect(x, y, keySize, keySize, Config.getNormalSize() / 5);
        gfx.setAntiAlias(false);
        gfx.setFont(Fonts.getFont(size));
        gfx.drawStringCentered(key, x + keySize/2f, y + keySize/2f);
    }

    public static void drawHelpKey(Graphics gfx, Color color, String key, String does, int x, int y, int size) {
        gfx.setFont(Fonts.getFont(size));
        float dy = size*0.125f;
        gfx.setColor(color);
        gfx.drawString("Press", x, y + dy);
        float dx = gfx.getFont().getWidth("Press  ");
        drawKey(gfx, color, key, (int) (x + dx), y, size);
        dx +=  size*1.5f;
        gfx.drawString(does, x + dx, y + dy);
    }
}
