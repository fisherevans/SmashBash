package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/18/14
 */
public class MapSet {
    public static final String MAP_ROOT = "res/maps/";
    public static final int BASE_ID = -1;

    private Map<Integer, TiledMap> _maps;
    private String _code;

    public Color baseColor;
    public String name, preview, background;
    public Float gravity;

    public MapSet(String code) {
        _code = code;

        _maps = new HashMap<>();
        String mapLocation = MAP_ROOT + _code + ".tmx";
        _maps = new HashMap<Integer, TiledMap>();
        try {
            _maps.put(BASE_ID, new TiledMap(mapLocation));
            for(Integer size: Config.SPRITE_SIZES) {
                _maps.put(size, new TiledMap(mapLocation, MAP_ROOT + "re-sized/" + size,
                        size/((float)(Config.SPRITE_SIZES[Config.SPRITE_SIZES.length-1]))));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public MapSet(String code, Object o) {
        _code = code;
    }

    public java.util.Map<Integer, TiledMap> getMaps() {
        return _maps;
    }

    public TiledMap getMap(int size) {
        return _maps.get(size);
    }

    public String getCode() {
        return _code;
    }

    @Override
    public String toString() {
        return name;
    }
}
