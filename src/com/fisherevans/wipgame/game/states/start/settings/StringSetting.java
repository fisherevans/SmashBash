package com.fisherevans.wipgame.game.states.start.settings;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class StringSetting extends Setting {
    private String[] _options;

    private int _current;

    public StringSetting(String name, String[] options, int current) {
        super(name, true);
        _options = options;
        _current = current;
    }

    @Override
    public String getSelected() {
        return _options[_current];
    }

    @Override
    public boolean hasNext() {
        return _current < _options.length-1;
    }

    @Override
    public boolean hasPrevious() {
        return _current > 0;
    }

    @Override
    public void next() {
        _current++;
        if(_current > _options.length-1)
            _current = _options.length-1;
    }

    @Override
    public void previous() {
        _current--;
        if(_current < 0)
            _current = 0;
    }
}
