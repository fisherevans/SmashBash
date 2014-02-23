package com.fisherevans.wipgame.game.game_config;

import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class PlayerProfile {
    public static Map<String, CharacterDefinition> CHARACTER_MAP;
    public static CharacterDefinition[] CHARACTERS = {
            new CharacterDefinition("base", "Phillip")
    };
    public static Color[] COLORS = {
            Color.white,
            Color.cyan,
            Color.blue,
            Color.red,
            Color.darkGray
    };

    static {
        CHARACTER_MAP = new HashMap<>();
        for(CharacterDefinition characterDefinition :CHARACTERS)
            CHARACTER_MAP.put(characterDefinition.getName(), characterDefinition);
    }

    private int _input;
    private CharacterDefinition _characterDefinition;
    private Color _color;
    private boolean _ready;

    public PlayerProfile(int input) {
        _input = input;
        _characterDefinition = CHARACTERS[0];
        _color = COLORS[0];
        _ready = false;
    }

    public int getInput() {
        return _input;
    }

    public CharacterDefinition getCharacterDefinition() {
        return _characterDefinition;
    }

    public void setCharacterDefinition(CharacterDefinition characterDefinition) {
        _characterDefinition = characterDefinition;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public boolean isReady() {
        return _ready;
    }

    public void setReady(boolean ready) {
        _ready = ready;
    }
}
