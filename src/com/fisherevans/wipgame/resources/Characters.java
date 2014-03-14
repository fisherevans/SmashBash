package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/13/14
 */
public class Characters {
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
        if(definition.getName() == null)
            definition.setName(base.getName());
        if(definition.getPrimarySkill() == null)
            definition.setPrimarySkill(base.getPrimarySkill());
        if(definition.getSecondarySkill() == null)
            definition.setSecondarySkill(base.getSecondarySkill());
        if(definition.getMaxSpeed() == null)
            definition.setMaxSpeed(base.getMaxSpeed());
        if(definition.getXAcceleration() == null)
            definition.setXAcceleration(base.getXAcceleration());
        if(definition.getXDeAcceleration() == null)
            definition.setXDeAcceleration(base.getXDeAcceleration());
        if(definition.getXAccelerationInAir() == null)
            definition.setXAccelerationInAir(base.getXAccelerationInAir());
        if(definition.getXDeAccelerationInAir() == null)
            definition.setXDeAccelerationInAir(base.getXDeAccelerationInAir());
        if(definition.getJumpVelocity() == null)
            definition.setJumpVelocity(base.getJumpVelocity());
        if(definition.getJumpTime() == null)
            definition.setJumpTime(base.getJumpTime());
        if(definition.getHealthScale() == null)
            definition.setHealthScale(base.getHealthScale());
        if(definition.getFramesPerSecond() == null)
            definition.setFramesPerSecond(base.getFramesPerSecond());
        return definition;
    }

    private static CharacterDefinition getCharacterDefinition(Settings.Setting baseSetting) {
        CharacterDefinition definition = new CharacterDefinition();
        definition.setCode(baseSetting.getName());
        definition.setName(baseSetting.getChild("name").stringValue());
        definition.setPrimarySkill(baseSetting.getChild("primarySkill").classValue());
        definition.setSecondarySkill(baseSetting.getChild("secondarySkill").classValue());
        definition.setMaxSpeed(baseSetting.getChild("maxSpeed").floatValue());
        definition.setXAcceleration(baseSetting.getChild("xAcceleration").floatValue());
        definition.setXDeAcceleration(baseSetting.getChild("xDeAcceleration").floatValue());
        definition.setXAccelerationInAir(baseSetting.getChild("xAccelerationInAir").floatValue());
        definition.setXDeAccelerationInAir(baseSetting.getChild("xDeAccelerationInAir").floatValue());
        definition.setJumpVelocity(baseSetting.getChild("jumpVelocity").floatValue());
        definition.setJumpTime(baseSetting.getChild("jumpTime").floatValue());
        definition.setHealthScale(baseSetting.getChild("healthScale").floatValue());
        definition.setFramesPerSecond(baseSetting.getChild("framesPerSecond").floatValue());
        return definition;
    }
}
