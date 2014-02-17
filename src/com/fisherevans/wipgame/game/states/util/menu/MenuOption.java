package com.fisherevans.wipgame.game.states.util.menu;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.confirm.ConfirmState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.game.states.util.menu.options.RunnableOption;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public abstract class MenuOption {

    private String _displayName;

    public MenuOption(String displayName) {
        _displayName = displayName;
    }

    public abstract void action();

    public String getDisplayName() {
        return _displayName;
    }
}
