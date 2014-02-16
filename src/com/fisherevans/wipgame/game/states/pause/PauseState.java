package com.fisherevans.wipgame.game.states.pause;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.util.menu.Menu;
import com.fisherevans.wipgame.game.states.util.menu.options.DummyOption;
import com.fisherevans.wipgame.game.states.util.menu.options.QuitOption;
import com.fisherevans.wipgame.game.states.util.menu.options.RunnableOption;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Messages;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class PauseState extends WIPState {
    private static final Color _backgroundColor = new Color(0f, 0f, 0f, 0.7f);
    private static final Color _foregroundColor = new Color(0.9f, 0.9f, 0.9f);

    private WIPState _otherState;

    private StateBasedGame _stateBasedGame;

    private Menu _menu;

    private Font _pauseFont;
    private String _pauseText;

    public PauseState(WIPState otherState) {
        _otherState = otherState;
        _pauseFont  = Fonts.getStrokedFont(Fonts.HUGE);
        _pauseText = Messages.get("paused.title");
        // TODO
        _menu = new Menu(Config.SIZES[1], Menu.Orientation.Center, true, true, Fonts.getStrokedFont(Fonts.SMALL));
        _menu.add(new RunnableOption("Resume", new Runnable() {
            @Override
            public void run() {
                _stateBasedGame.enterState(_otherState.getID());
            }
        }));
        _menu.add(new DummyOption("Settings"));
        _menu.add(new QuitOption("Exit to Desktop"));
    }

    @Override
    public int getID() {
        return WIP.STATE_PAUSE;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        _stateBasedGame = game;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics gfx) throws SlickException {
        _otherState.render(container, game, gfx);
        gfx.setColor(_backgroundColor);
        gfx.fillRect(0, 0, container.getWidth(), container.getHeight());

        gfx.setColor(_foregroundColor);
        gfx.setFont(_pauseFont);
        float textWidth = _pauseFont.getWidth(_pauseText);
        float startY = (container.getHeight() - _pauseFont.getLineHeight())/2f;

        gfx.drawString(_pauseText, (container.getWidth() - textWidth)/2f, startY);

        _menu.render(gfx, container.getScreenWidth() / 2f, startY + _pauseFont.getLineHeight() + Config.SIZES[1]);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {
        _menu.keyDown(key);
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    private enum RenderState { FADE_IN, WAIT, FADE_OUT }
}
