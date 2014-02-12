package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.game.states.loading.LoadingState;
import com.fisherevans.wipgame.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Game extends StateBasedGame {
    public final static int STATE_LOADING = 0;
    public final static int STATE_START = 1;
    public final static int STATE_READY = 2;
    public final static int STATE_PLAY = 3;
    public final static int STATE_PAUSE = 4;
    public final static int STATE_RESULTS = 5;

    private static GameSettings _gameSettings;

    public Game(String name) {
        super(name);
    }

    public static GameSettings getGameSettings() {
        return _gameSettings;
    }

    public static void setGameSettings(GameSettings gameSettings) {
        _gameSettings = gameSettings;
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new LoadingState());
    }
}
