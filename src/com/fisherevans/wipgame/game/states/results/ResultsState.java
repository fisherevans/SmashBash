package com.fisherevans.wipgame.game.states.results;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class ResultsState extends WIPState {
    @Override
    public int getID() {
        return WIP.STATE_RESULTS;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.setFont(Fonts.getFont(Fonts.HUGE));
        graphics.setColor(Color.white);
        graphics.drawString("Results", 10, 10);
    }

    @Override
    public void update(float delta) {
        WIP.game.enterState(WIP.STATE_START);
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
