package com.fisherevans.wipgame.game;

import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Inputs;
import com.fisherevans.wipgame.input.InputsListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public abstract class WIPState extends BasicGameState implements InputsListener {
    protected Log log = new Log(this.getClass());

    public void init() {
        try {
            init(WIP.container, WIP.game);
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public final void enter(GameContainer container, StateBasedGame game) throws SlickException {
        enterState(container, game);
        Inputs.setListener(this);
        log.debug("Entering...");
    }

    public abstract void enterState(GameContainer container, StateBasedGame game) throws SlickException;

    @Override
    public final void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        update(delta/1000f);
    }

    public abstract void update(float delta) throws SlickException;

    @Override
    public final void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        render(g);
        g.flush();
    }

    public abstract void render(Graphics graphics) throws SlickException;

    @Override
    public final void leave(GameContainer container, StateBasedGame game) throws SlickException {
        leaveState(container, game);
        if(Inputs.getListener() == this)
            Inputs.setListener(null);
        log.debug("Leaving...");
    }

    public abstract void leaveState(GameContainer container, StateBasedGame game) throws SlickException;
}
