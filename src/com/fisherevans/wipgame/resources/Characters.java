package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.Color;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/13/14
 */
public class Characters {
    public static final Log log = new Log(Characters.class);

    private static Map<String, CharacterDefinition> _characterMap;
    private static String[] _characterCodes;
    private static CharacterDefinition[] _characters;

    private static Map<String, Color> _colorMap;
    private static String[] _colorNames;
    private static Color[] _colors;

    private static CharacterDefinition _baseDefinition;

    public static void init() {
        // CHARACTERS
        _baseDefinition = getCharacterDefinition(Settings.getSetting("characters.default"));
        _characterMap = new HashMap<>();
        for(Settings.Setting setting:Settings.getSetting("characters.define").getChildren())
            addCharacterDefinition(getCharacterDefinition(_baseDefinition, setting));

        _characterCodes = _characterMap.keySet().toArray(new String[0]);
        _characters = new CharacterDefinition[_characterCodes.length];
        for(int id = 0;id < _characters.length;id++)
            _characters[id] = getCharacter(id);

        // COLORS
        _colorMap = new HashMap<>();
        for(Settings.Setting setting:Settings.getSetting("characters.colors").getChildren())
            _colorMap.put(setting.getName(), setting.colorValue());

        _colorNames = _colorMap.keySet().toArray(new String[0]);
        _colors = new Color[_colorNames.length];
        for(int id = 0;id < _colors.length;id++)
            _colors[id] = getColor(id);
    }

    public static String[] getCharacterCodes() {
        return _characterCodes;
    }

    public static CharacterDefinition getCharacter(String code) {
        return _characterMap.get(code);
    }

    public static CharacterDefinition getCharacter(int id) {
        return getCharacter(_characterCodes[id]);
    }

    public static CharacterDefinition[] getCharacters() {
        return _characters;
    }

    public static String[] getColorNames() {
        return _colorNames;
    }

    public static Color getColor(String name) {
        return _colorMap.get(name);
    }

    public static Color getColor(int id) {
        return getColor(_colorNames[id]);
    }

    public static Color[] getColors() {
        return _colors;
    }

    private static void addCharacterDefinition(CharacterDefinition definition) {
        _characterMap.put(definition.getCode(), definition);
    }

    private static CharacterDefinition getCharacterDefinition(CharacterDefinition base, Settings.Setting baseSetting) {
        CharacterDefinition definition = getCharacterDefinition(baseSetting);
        for(Field field:CharacterDefinition.class.getFields()) {
            try {
                if(field.get(definition) == null)
                    field.set(definition, field.get(base));
            } catch (Exception e) {
                log.error("Failed setting character property from base: " + field.getName());
                log.error(e.toString());
            }
        }
        return definition;
    }

    private static CharacterDefinition getCharacterDefinition(Settings.Setting baseSetting) {
        CharacterDefinition definition = new CharacterDefinition(baseSetting.getName());
        Field field;
        for(Settings.Setting setting:baseSetting.getChildren()) {
            try {
                field = CharacterDefinition.class.getField(setting.getName());
                field.set(definition, setting.getValue());
            } catch (Exception e) {
                log.error("Failed setting character property: " + setting.getName());
                log.error(e.toString());
            }
        }
        return definition;
    }
}
