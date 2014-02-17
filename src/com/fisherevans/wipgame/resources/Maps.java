package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.MapProfile;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Maps {
    public static final int BASE = -1;

    private static String _mapRoot = "res/maps/";
    private static String[] _mapNames = { "test2", "test" };
    private static Map<String, Map<Integer, TiledMap>> _mapMap;

    public static void load() {
        _mapMap = new HashMap<>();
        for(String mapName:_mapNames) {
            String mapLocation = _mapRoot + mapName + ".tmx";
            Map<Integer, TiledMap> sizedMaps = new HashMap<Integer, TiledMap>();
            try {
                sizedMaps.put(BASE, new TiledMap(mapLocation));
                for(Integer size: Config.SIZES) {
                    sizedMaps.put(size, new TiledMap(mapLocation, _mapRoot + size,
                            size/((float)(Config.SIZES[Config.SIZES.length-1]))));
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
            _mapMap.put(mapName, sizedMaps);
        }
    }

    public static Map<Integer, TiledMap> getSizedMaps(String name) {
        return _mapMap.get(name);
    }

    public static TiledMap getSizedMap(String name, Integer size) {
        return _mapMap.get(name).get(size);
    }

    public static MapProfile[] getProfiles() {
        MapProfile[] profiles = new MapProfile[_mapNames.length];
        for(int id = 0;id < _mapNames.length;id++)
            profiles[id] = new MapProfile(_mapNames[id]);
        return profiles;
    }
}
