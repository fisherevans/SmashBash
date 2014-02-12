package com.fisherevans.wipgame.game.states.start.settings;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class BooleanSetting extends Setting {
    private String _on, _off;
    private boolean _current;

    public BooleanSetting(String name, boolean start, String on, String off) {
        super(name, false);
        _current = start;
        _on = on;
        _off = off;
    }

    @Override
    public String getSelected() {
        return _current ? _on : _off;
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
        _current = !_current;
    }
}
