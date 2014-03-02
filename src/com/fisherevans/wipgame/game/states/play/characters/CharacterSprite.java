package com.fisherevans.wipgame.game.states.play.characters;

import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class CharacterSprite {
    public static final SpriteType DEFAULT_SPRITE_TYPE = SpriteType.Idle;

    private final Map<SpriteType, Image> _sprites;

    public CharacterSprite() {
        _sprites = new HashMap<>();
    }

    public CharacterSprite(Image spriteSheet, int spriteWidth, int spriteHeight) {
        _sprites = new HashMap<>();
        SpriteType[] types = SpriteType.values();
        for(int id = 0;id < types.length;id++) {
            _sprites.put(types[id], spriteSheet.getSubImage(spriteWidth*id, 0, spriteWidth, spriteHeight));
        }
    }

    public Image getSprite(SpriteType type) {
        return _sprites.get(type);
    }

    public void setSprite(SpriteType type, Image image) {
        _sprites.put(type, image);
    }
}
