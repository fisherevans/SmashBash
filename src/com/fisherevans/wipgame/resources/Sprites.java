package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import org.newdawn.slick.Image;
import org.newdawn.slick.imageout.ImageIOWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class Sprites {
    public static final String[] _characters = { "base" };

    public static final float PADDING_PERCENTAGE = 0.5f;

    private static Map<String, Map<Integer, CharacterSprite>> _characterSpriteMap;

    public static void load() {
        _characterSpriteMap = new HashMap<>();
        Map<Integer, CharacterSprite> characterSprites;
        Image spriteSheet;
        int spriteWidth, spriteHeight;
        for(String character: _characters) {
            characterSprites = new HashMap<>();
            for(Integer size: Config.SIZES) {
                spriteWidth = size*2;
                spriteHeight = size*3;
                spriteSheet = Images.getImage("sprites/characters/re-sized/" + size + "/" + character);
                characterSprites.put(size, new CharacterSprite(
                        spriteSheet.getSubImage(spriteWidth*0, 0, spriteWidth, spriteHeight),
                        spriteSheet.getSubImage(spriteWidth*1, 0, spriteWidth, spriteHeight),
                        spriteSheet.getSubImage(spriteWidth*2, 0, spriteWidth, spriteHeight),
                        spriteSheet.getSubImage(spriteWidth*3, 0, spriteWidth, spriteHeight),
                        spriteSheet.getSubImage(spriteWidth*4, 0, spriteWidth, spriteHeight)));
            }
            _characterSpriteMap.put(character, characterSprites);
        }
    }

    public static Map<Integer, CharacterSprite> getCharacterSprites(String name) {
        return _characterSpriteMap.get(name);
    }
}
