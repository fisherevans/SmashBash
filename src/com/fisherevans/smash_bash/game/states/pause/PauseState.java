package com.fisherevans.smash_bash.game.states.pause;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.game.util.menu.Menu;
import com.fisherevans.smash_bash.game.util.menu.options.DummyOption;
import com.fisherevans.smash_bash.game.util.menu.options.EndGameOption;
import com.fisherevans.smash_bash.game.util.menu.options.QuitOption;
import com.fisherevans.smash_bash.game.util.menu.options.RunnableOption;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.Fonts;
import com.fisherevans.smash_bash.resources.Settings;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class PauseState extends SmashBashState {
    private static final Color _backgroundColor = new Color(0f, 0f, 0f, 0.35f);
    private static final Color _foregroundColor = new Color(0.9f, 0.9f, 0.9f);

    private SmashBashState _otherState;

    private StateBasedGame _stateBasedGame;

    private Menu _menu;

    private Font _pauseFont;
    private String _pauseText;

    public PauseState(SmashBashState otherState) {
        _otherState = otherState;
        _pauseFont  = Fonts.getStrokedFont(Config.largeSize);
        _pauseText = Settings.getString("paused.title");
        // TODO
        _menu = new Menu(0, Config.SPRITE_SIZES[1], Menu.Orientation.Center, true, Fonts.getStrokedFont(Config.smallSize));
        _menu.add(new RunnableOption(Settings.getString("paused.option.resume"), new Runnable() {
            @Override
            public void run() {
                _stateBasedGame.enterState(_otherState.getID());
            }
        }));
        _menu.add(new DummyOption(Settings.getString("paused.option.settings")));
        _menu.add(new EndGameOption(Settings.getString("paused.option.endGame")));
        _menu.add(new QuitOption(Settings.getString("paused.option.exit")));
        _menu.setTitle(Settings.getString("paused.title"), Fonts.getStrokedFont(Config.largeSize));
    }

    @Override
    public int getID() {
        return SmashBash.STATE_PAUSE;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        _stateBasedGame = game;
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        _otherState.render(graphics);
        graphics.setColor(_backgroundColor);
        graphics.fillRect(0, 0, SmashBash.container.getWidth(), SmashBash.container.getHeight());

        float startY = (SmashBash.height() - _pauseFont.getLineHeight())/2f;

        _menu.render(graphics, SmashBash.width()/2f, startY + _pauseFont.getLineHeight() + Config.SPRITE_SIZES[0]);
    }

    @Override
    public void update(float delta) {
        _menu.update(delta);
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Menu)
            _stateBasedGame.enterState(_otherState.getID());
        else
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
