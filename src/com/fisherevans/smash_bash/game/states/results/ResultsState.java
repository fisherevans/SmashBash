package com.fisherevans.smash_bash.game.states.results;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.Fonts;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class ResultsState extends SmashBashState {
    @Override
    public int getID() {
        return SmashBash.STATE_RESULTS;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.setFont(Fonts.getFont(Config.largeSize));
        graphics.setColor(Color.white);
        graphics.drawString("Results", 10, 10);
    }

    @Override
    public void update(float delta) {
        SmashBash.game.enterState(SmashBash.STATE_START);
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {

    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
