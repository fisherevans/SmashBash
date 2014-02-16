package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.input.Inputs;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public abstract class WIPState extends BasicGameState implements InputsListener {
    @Override
    public final void enter(GameContainer container, StateBasedGame game) throws SlickException {
        enterState(container, game);
        Inputs.setListener(this);
    }

    public abstract void enterState(GameContainer container, StateBasedGame game) throws SlickException;

    @Override
    public final void leave(GameContainer container, StateBasedGame game) throws SlickException {
        leaveState(container, game);
        if(Inputs.getListener() == this)
            Inputs.setListener(null);
    }

    public abstract void leaveState(GameContainer container, StateBasedGame game) throws SlickException;
}
