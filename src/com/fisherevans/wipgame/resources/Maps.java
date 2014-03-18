package com.fisherevans.wipgame.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Maps {
    private static Map<String, MapSet> _mapSetMap;
    private static String[] _mapCodes;
    private static MapSet[] _mapSets;
    private static MapSet _baseMap;

    public static void load() {
        _mapSetMap = new HashMap<String, MapSet>();
        _baseMap = new MapSet("default", null);
        Settings.populate(Settings.getSetting("maps.default"), _baseMap);
        MapSet mapProfile;
        for(Settings.Setting mapSetting:Settings.getSetting("maps.define").getChildren()) {
            mapProfile = new MapSet(mapSetting.getName());
            Settings.populate(mapSetting, mapProfile);
            Settings.replaceNulls(_baseMap, mapProfile);
            _mapSetMap.put(mapProfile.getCode(), mapProfile);
        }
        _mapCodes = _mapSetMap.keySet().toArray(new String[0]);
        _mapSets = _mapSetMap.values().toArray(new MapSet[0]);
    }

    public static Map<String, MapSet> getMapSetMap() {
        return _mapSetMap;
    }

    public static MapSet getMapSet(String code) {
        return _mapSetMap.get(code);
    }

    public static String[] getMapCodes() {
        return _mapCodes;
    }

    public static MapSet[] getMapSets() {
        return _mapSets;
    }
}
