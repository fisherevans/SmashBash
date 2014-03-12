package com.fisherevans.wipgame.game.states.help;

import com.fisherevans.wipgame.game.OverlayState;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.input.Key;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class HelpState extends OverlayState {

    public HelpState(WIPState overlayedState) {
        super(overlayedState);
    }

    @Override
    public int getID() {
        return WIP.STATE_HELP;
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void update(float delta) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        getOverlayedState().render(graphics);
        graphics.setColor(new Color(0f, 0f, 0f, 0.45f));
        graphics.fillRect(0, 0, WIP.width(), WIP.height());
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {

    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
