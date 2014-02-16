package com.fisherevans.wipgame.game.states.util.menu;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public abstract class MenuSetting extends MenuOption {
    public MenuSetting(String displayName) {
        super(displayName);
    }

    @Override
    public boolean action() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [" + getValue() + "]";
    }

    public abstract String getValue();

    public abstract boolean hasNext();

    public abstract boolean hasPrevious();

    public abstract void previous();

    public abstract void next();
}
