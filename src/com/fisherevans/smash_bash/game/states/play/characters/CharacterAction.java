package com.fisherevans.smash_bash.game.states.play.characters;

import com.fisherevans.smash_bash.graphics.CharacterSprite;

/**
 * Author: Fisher Evans
 * Date: 2/27/14
 */
public class CharacterAction {
    private CharacterSprite.Type _spriteType;
    private float _duration;
    private boolean _preventInput;

    public CharacterAction(CharacterSprite.Type spriteType, float duration, boolean preventInput) {
        _spriteType = spriteType;
        _duration = duration;
        _preventInput = preventInput;
    }

    public CharacterSprite.Type getSpriteType() {
        return _spriteType;
    }

    public float getDuration() {
        return _duration;
    }

    public boolean getPreventInput() {
        return _preventInput;
    }
}
