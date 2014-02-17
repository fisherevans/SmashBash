package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.game.states.loading.LoadingState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

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
        gameSettings = new GameSettings();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        container = gameContainer;
        addState(new LoadingState());
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
}
