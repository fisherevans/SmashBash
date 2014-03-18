package com.fisherevans.smash_bash.resources;

import com.fisherevans.smash_bash.game.game_config.CharacterDefinition;
import com.fisherevans.smash_bash.log.Log;

import java.util.*;

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

        List<CharacterDefinition> characterDefinitionsList = new ArrayList<CharacterDefinition>(_characterMap.values());
        Collections.sort(characterDefinitionsList, new CharacterDefinition.CharacterDefinitionComparator());
        _characterCodes = new String[characterDefinitionsList.size()];
        _characterDefinitions = new CharacterDefinition[characterDefinitionsList.size()];
        int id = 0;
        for(CharacterDefinition d:characterDefinitionsList) {
            _characterCodes[id] = d.getCode();
            _characterDefinitions[id] = d;
            id++;
        }
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
