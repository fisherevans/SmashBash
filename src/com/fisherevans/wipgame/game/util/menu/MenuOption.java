package com.fisherevans.wipgame.game.util.menu;

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
