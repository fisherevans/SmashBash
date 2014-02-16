package com.fisherevans.wipgame.game.states.start.settings;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.start.StartState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class PlaySetting extends Setting {
    private StateBasedGame _game;
    private StartState _startState;

    public PlaySetting(String name, StartState startState, StateBasedGame game) {
        super(name, false);
        _startState = startState;
        _game = game;
    }

    @Override
    public String getSelected() {
        return "";
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void select() {
        WIP.setGameSettings(_startState.getGameSettings());
        _game.enterState(WIP.STATE_READY);
    }
}
