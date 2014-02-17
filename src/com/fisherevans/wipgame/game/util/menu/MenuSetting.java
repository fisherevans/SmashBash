package com.fisherevans.wipgame.game.util.menu;

import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public abstract class MenuSetting extends MenuOption {
    public MenuSetting(String displayName) {
        super(displayName);
    }

    @Override
    public void action() {

    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + ": " + getValue();
    }

    public abstract String getValue();

    public abstract boolean hasNext();

    public abstract boolean hasPrevious();

    public abstract void previous();

    public abstract void next();

    public abstract int getSelectOptionId();

    public abstract List<Object> getAvailableOptions();
}
