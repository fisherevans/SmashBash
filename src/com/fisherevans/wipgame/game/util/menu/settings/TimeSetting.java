package com.fisherevans.wipgame.game.util.menu.settings;

import com.fisherevans.wipgame.resources.Settings;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class TimeSetting extends NumberSetting {
    private String _unit;
    private String _max;
    public TimeSetting(String displayName, String unit, int min, int current, int max) {
        super(displayName, min, current, max);
        _unit = unit;
        _max = Settings.getString("word.infinite");
    }

    @Override
    public String getValue() {
        if(getMax() == getCurrent())
            return _max;
        else
            return super.getValue() + " " + _unit;
    }
}
