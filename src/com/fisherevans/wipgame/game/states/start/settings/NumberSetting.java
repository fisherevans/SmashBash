package com.fisherevans.wipgame.game.states.start.settings;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class NumberSetting extends Setting {
    private int _min, _max;

    private int _current;

    public NumberSetting(String name, int min, int current, int max) {
        super(name, true);
        _min = min;
        _max = max;
        _current = current;
    }

    @Override
    public String getSelected() {
        return _current + "";
    }

    @Override
    public boolean hasNext() {
        return _current < _max;
    }

    @Override
    public boolean hasPrevious() {
        return _current > _min;
    }

    @Override
    public void next() {
        _current++;
        if(_current > _max)
            _current = _max;
    }

    @Override
    public void previous() {
        _current--;
        if(_current < _min)
            _current = _min;
    }
}
