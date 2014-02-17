package com.fisherevans.wipgame.game.util.menu.settings;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class TimeSetting extends NumberSetting {
    private String _unit;
    public TimeSetting(String displayName, String unit, int min, int current, int max) {
        super(displayName, min, current, max);
        _unit = unit;
    }

    @Override
    public String getValue() {
        if(getMax() == getCurrent())
            return "Infinite";
        else
            return super.getValue() + " " + _unit;
    }
}
