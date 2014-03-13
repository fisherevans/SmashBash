package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.resources.Characters;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class PlayerProfile {
    private int _input;
    private CharacterDefinition _characterDefinition;
    private Color _color;
    private boolean _ready;

    public PlayerProfile(int input) {
        _input = input;
        _characterDefinition = Characters.getCharacter(0);
        _color = Characters.getColor(0);
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
