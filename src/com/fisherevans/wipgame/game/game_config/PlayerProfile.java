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
    private int _spriteId;
    private boolean _ready;

    public PlayerProfile(int input) {
        _input = input;
        _characterDefinition = Characters.getCharacterDefinitions()[0];
        _spriteId = 0;
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

    public int getSpriteId() {
        return _spriteId;
    }

    public void setSpriteId(int spriteId) {
        _spriteId = spriteId;
    }

    public boolean isReady() {
        return _ready;
    }

    public void setReady(boolean ready) {
        _ready = ready;
    }
}
