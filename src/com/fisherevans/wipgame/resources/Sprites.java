package com.fisherevans.wipgame.resources;

import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class Sprites {
    public static final String[] _characters = { "base" };
    public static final Integer[] CHARACTER_SPRITE_SIZES = { 32, 64, 96, 128 };

    public static final float PADDING_PERCENTAGE = 0.25f;
    public static final float DEFAULT_SIZE = 128f;
    public static final float DEFAULT_SIZE_TOTAL = DEFAULT_SIZE/PADDING_PERCENTAGE*2 + 128f;

    private static Map<String, Map<Integer, CharacterSprite>> _characterSpriteMap;

    public static void load() {
        _characterSpriteMap = new HashMap<>();
        Map<Integer, CharacterSprite> characterSprites;
        Image falling, walking1, walking2, walking3;
        for(String character: _characters) {
            characterSprites = new HashMap<>();
            for(Integer size: CHARACTER_SPRITE_SIZES) {
                falling = Images.getImage("sprites/characters/" + character + "-falling-" + size);
                walking1 = Images.getImage("sprites/characters/" + character + "-walking1-" + size);
                walking2 = Images.getImage("sprites/characters/" + character + "-walking2-" + size);
                walking3 = Images.getImage("sprites/characters/" + character + "-walking3-" + size);
                characterSprites.put(size,
                        new CharacterSprite(falling, walking1, walking2, walking3));
            }
            _characterSpriteMap.put(character, characterSprites);
        }
    }

    public static Map<Integer, CharacterSprite> getCharacterSprites(String name) {
        return _characterSpriteMap.get(name);
    }
}
