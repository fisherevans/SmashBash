package com.fisherevans.wipgame.game.states.start;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.game_config.GameSettings;
import com.fisherevans.wipgame.game.game_config.MapProfile;
import com.fisherevans.wipgame.game.states.ready.ReadyState;
import com.fisherevans.wipgame.game.util.menu.Menu;
import com.fisherevans.wipgame.game.util.menu.options.QuitOption;
import com.fisherevans.wipgame.game.util.menu.options.RunnableOption;
import com.fisherevans.wipgame.game.util.menu.settings.NumberSetting;
import com.fisherevans.wipgame.game.util.menu.settings.ObjectSetting;
import com.fisherevans.wipgame.game.util.menu.settings.TimeSetting;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Maps;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class StartState extends WIPState {
    private Menu _menu;

    private NumberSetting _lives, _health, _time;
    private ObjectSetting<MapProfile> _maps;

    @Override
    public int getID() {
        return WIP.STATE_START;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _maps = new ObjectSetting<MapProfile>("Map", new MapProfile(WIP.gameSettings.map), Maps.getProfiles());
        _lives = new NumberSetting("Lives", 1, WIP.gameSettings.lives, 10);
        _health = new NumberSetting("Health", 50, WIP.gameSettings.health, 200, 25);
        _time = new TimeSetting("Time Limit", "Minutes", 1, WIP.gameSettings.time, 11);

        _menu = new Menu(Config.SIZES[1], Menu.Orientation.Left, true, Fonts.getStrokedFont(Fonts.REGULAR));
        _menu.add(new RunnableOption("Play", new Runnable() {
            @Override
            public void run() {
                WIP.gameSettings.map = _maps.getSelected().getName();
                WIP.gameSettings.lives = _lives.getCurrent();
                WIP.gameSettings.health = _health.getCurrent();
                WIP.gameSettings.time = _time.getCurrent();

                WIP.enterNewState(new ReadyState());
            }
        }));
        _menu.add(_maps);
        _menu.add(_lives);
        _menu.add(_health);
        _menu.add(_time);
        _menu.add(new QuitOption("Exit to Desktop"));
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        _menu.render(graphics, Config.SIZES[3], WIP.height()/2f);
    }

    @Override
    public void update(float delta) {
        _menu.update(delta);
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    public GameSettings getGameSettings() {
        return new GameSettings();
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        _menu.keyDown(key);
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
