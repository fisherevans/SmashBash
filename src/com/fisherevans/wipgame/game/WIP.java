package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.game.states.loading.LoadingState;
import com.fisherevans.wipgame.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class WIP extends StateBasedGame {
    public final static int STATE_LOADING = 0;
    public final static int STATE_START = 1;
    public final static int STATE_READY = 2;
    public final static int STATE_PLAY = 3;
    public final static int STATE_PAUSE = 4;
    public final static int STATE_RESULTS = 5;
    public final static int STATE_CONFIRM = 6;

    public static StateBasedGame game;
    public static GameContainer container;
    public static boolean debug = false;
    public static GameSettings gameSettings;

    public WIP(String name) {
        super(name);
        game = this;
    }

    public static GameSettings getGameSettings() {
        return gameSettings;
    }

    public static void setGameSettings(GameSettings newGameSettings) {
        gameSettings = newGameSettings;
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
        addState(new LoadingState());
    }
}
