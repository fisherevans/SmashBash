package com.fisherevans.smash_bash.game.util.menu.settings;

import com.fisherevans.smash_bash.game.util.menu.MenuSetting;
import com.fisherevans.smash_bash.tools.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class NumberSetting extends MenuSetting {
    private int _min, _current, _max, _interval;

    public NumberSetting(String displayName, int min, int current, int max) {
        this(displayName, min, current, max, 1);
    }

    public NumberSetting(String displayName, int min, int current, int max, int interval) {
        super(displayName);
        _min = min;
        _current = current;
        _max = max;
        _interval = interval;
    }

    public int getMin() {
        return _min;
    }

    public int getCurrent() {
        return _current;
    }

    public int getMax() {
        return _max;
    }

    public int getInterval() {
        return _interval;
    }

    @Override
    public String getValue() {
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
    public void previous() {
        _current -= _interval;
        _current = MathUtil.clamp(_min, _current, _max);
    }

    @Override
    public void next() {
        _current += _interval;
        _current = MathUtil.clamp(_min, _current, _max);
    }

    @Override
    public int getSelectOptionId() {
        return _current - _min;
    }

    @Override
    public List<Object> getAvailableOptions() {
        List<Object> options = new ArrayList<Object>();
        for(int id = _min;id <= _max;id++)
            options.add(id);
        return options;
    }
}
