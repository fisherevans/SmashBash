package com.fisherevans.wipgame.game.util.menu.settings;

import com.fisherevans.wipgame.game.game_config.MapProfile;
import com.fisherevans.wipgame.game.util.menu.MenuSetting;
import com.fisherevans.wipgame.tools.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class ObjectSetting<T> extends MenuSetting {
    private List<T> _objects;
    private int _current;

    public ObjectSetting(String displayName, T selected, T[] objects) {
        super(displayName);
        _objects = new ArrayList<>();
        for(T object:objects)
            _objects.add(object);
        select(selected);
    }

    @Override
    public String getValue() {
        return _objects.get(_current).toString();
    }

    @Override
    public boolean hasNext() {
        return _current < _objects.size()-1;
    }

    @Override
    public boolean hasPrevious() {
        return _current > 0;
    }

    @Override
    public void previous() {
        _current--;
        _current = MathUtil.clamp(0, _current, _objects.size()-1);
    }

    @Override
    public void next() {
        _current++;
        _current = MathUtil.clamp(0, _current, _objects.size()-1);
    }

    @Override
    public int getSelectOptionId() {
        return _current;
    }

    @Override
    public List<Object> getAvailableOptions() {;
        return (List<Object>) _objects;
    }

    public void select(T object) {
        int newId = _objects.indexOf(object);
        _current = MathUtil.clamp(0, newId, _objects.size()-1);
    }

    public T getSelected() {
        return _objects.get(_current);
    }
}
