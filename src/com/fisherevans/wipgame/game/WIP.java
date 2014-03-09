package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.game.game_config.GameSettings;
import com.fisherevans.wipgame.game.states.command.CommandState;
import com.fisherevans.wipgame.game.states.loading.LoadingState;
import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

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
}
