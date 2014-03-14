package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.resources.Sprites;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterDefinition {
    private String _code;
    private Map<Integer, CharacterSprite> _sprites;

    public String name;
    public Class primarySkill, secondarySkill;
    public Float maxLandSpeed, landAcceleration;
    public Float maxAirSpeed, airAcceleration, airDeAcceleration;
    public Float jumpVelocity, jumpTime;
    public Float healthScale, framesPerSecond;

    public CharacterDefinition(String code) {
        _code = code;
        _sprites = Sprites.getCharacterSprites(code);

    }

    public Map<Integer, CharacterSprite> getSprites() {
        return _sprites;
    }

    public String getCode() {
        return _code;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CharacterDefinition)
                && ((CharacterDefinition) obj).getCode().equals(_code);
    }
}
