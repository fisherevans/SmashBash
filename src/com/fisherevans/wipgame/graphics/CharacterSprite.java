package com.fisherevans.wipgame.graphics;

import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/17/14
 */
public class CharacterSprite extends Sprite {
    private Map<Type, Integer> _typeMap;

    public CharacterSprite(Image baseImage, int x, int y, int width, int height) {
        super(baseImage, x, y, width, height, Type.values().length);
        _typeMap = new HashMap<Type, Integer>();
        Type[] types = Type.values();
        for(int id = 0;id < types.length;id++)
            _typeMap.put(types[id], id);
    }

    public Image getFrame(Type type) {
        return getFrame(_typeMap.get(type));
    }

    public enum Type {
        Idle,
        Falling,
        Walking1,
        Walking2,
        Walking3,
        Primary,
        Secondary,
        Down,
        Crouched;
    }
}
