package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.tools.MathUtil;
import org.newdawn.slick.Color;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Fisher Evans
 * Date: 3/13/14
 */
public class Characters {
    public static final Log log = new Log(Characters.class);

    private static Map<String, CharacterDefinition> _characterMap;
    private static String[] _characterCodes;
    private static CharacterDefinition[] _characterDefinitions;

    private static CharacterDefinition _baseDefinition;

    public static void init() {
        _baseDefinition = getCharacterDefinition(Settings.getSetting("characters.default"));
        _characterMap = new HashMap<String, CharacterDefinition>();
        for(Settings.Setting setting:Settings.getSetting("characters.define").getChildren())
            addCharacterDefinition(getCharacterDefinition(_baseDefinition, setting));

        _characterCodes = _characterMap.keySet().toArray(new String[0]);
        _characterDefinitions = _characterMap.values().toArray(new CharacterDefinition[0]);
    }

    public static CharacterDefinition getCharacter(String code) {
        return _characterMap.get(code);
    }

    public static String[] getCharacterCodes() {
        return _characterCodes;
    }

    public static CharacterDefinition[] getCharacterDefinitions() {
        return _characterDefinitions;
    }

    private static void addCharacterDefinition(CharacterDefinition definition) {
        _characterMap.put(definition.getCode(), definition);
    }

    private static CharacterDefinition getCharacterDefinition(CharacterDefinition base, Settings.Setting baseSetting) {
        CharacterDefinition definition = getCharacterDefinition(baseSetting);
        Settings.replaceNulls(base, definition);
        definition.loadSprites();
        return definition;
    }

    private static CharacterDefinition getCharacterDefinition(Settings.Setting baseSetting) {
        CharacterDefinition definition = new CharacterDefinition(baseSetting.getName());
        Settings.populate(baseSetting, definition);
        return definition;
    }
}
