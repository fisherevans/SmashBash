package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.resources.Maps;

/**
 * Author: Fisher Evans
 * Date: 2/17/14
 */
public class MapProfile {
    private String _name;

    public MapProfile(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return Maps.getSizedMap(_name, Maps.BASE).getMapProperty("name", "Unknown");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MapProfile) {
            return ((MapProfile)obj).getName().equals(getName());
        } else {
            return false;
        }
    }
}
