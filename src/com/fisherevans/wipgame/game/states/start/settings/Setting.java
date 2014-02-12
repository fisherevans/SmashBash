package com.fisherevans.wipgame.game.states.start.settings;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public abstract class Setting {
    private String _name;

    private boolean _hasOptions;

    public Setting(String name, boolean hasOptions) {
        _name = name;
        _hasOptions = hasOptions;
    }

    public String getName() {
        return _name;
    }

    public boolean hasOptions() {
        return _hasOptions;
    }

    public abstract String getSelected();

    public abstract boolean hasNext();

    public abstract boolean hasPrevious();

    public abstract void next();

    public abstract void previous();

    public void select() { }
}
