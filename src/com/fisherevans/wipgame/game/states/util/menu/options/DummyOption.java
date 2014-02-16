package com.fisherevans.wipgame.game.states.util.menu.options;

import com.fisherevans.wipgame.game.states.util.menu.MenuOption;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class DummyOption extends MenuOption {
    public DummyOption(String displayName) {
        super(displayName);
    }

    @Override
    public boolean action() {
        return true;
    }
}
