package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.resources.Maps;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/17/14
 */
public class MapProfile {
    public static final String UNKNOWN_PREVIEW_KEY = "backgrounds/maps/unknown";
    private String _name;

    public MapProfile(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public Image getPreviewImage() {
        Image preview = Images.getImage("backgrounds/maps/" + _name);
        if(preview == null)
            preview = Images.getImage(UNKNOWN_PREVIEW_KEY);
        return preview;
    }

    @Override
    public String toString() {
        return Maps.getMapSet(_name).name;
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
