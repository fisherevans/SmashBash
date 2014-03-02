package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.game.states.play.entities.EntitySprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class Sprites {
    public static final String[] _characters = { "base" };

    public static final String[] _entities         = { "laser"};
    public static final int[]    _entitiesCount    = { 3 };
    public static final float[]  _entitiesPerFrame = { 0.1f };

    public static final float PADDING_PERCENTAGE = 0.5f;

    private static Map<String, Map<Integer, CharacterSprite>> _characterSpriteMap;
    private static Map<String, Map<Integer, EntitySprite>> _entitySpriteMap;

    public static void load() {
        loadCharacters();
        loadEntities();
    }

    private static void loadCharacters() {
        _characterSpriteMap = new HashMap<>();
        Map<Integer, CharacterSprite> characterSprites;
        for(String character: _characters) {
            characterSprites = new HashMap<>();
            for(Integer size: Config.SIZES) {
                characterSprites.put(size, new CharacterSprite(
                        Images.getImage("sprites/characters/re-sized/" + size + "/" + character),
                        size*2, size*3));
            }
            _characterSpriteMap.put(character, characterSprites);
        }
    }

    private static void loadEntities() {
        _entitySpriteMap = new HashMap<>();
        Map<Integer, EntitySprite> entitySprites;
        for(int id = 0;id < _entities.length;id++) {
            entitySprites = new HashMap<>();
            for(Integer size: Config.SIZES) {
                entitySprites.put(size, new EntitySprite(
                        Images.getImage("sprites/entities/re-sized/" + size + "/" + _entities[id]),
                        _entitiesCount[id], _entitiesPerFrame[id]));
            }
            _entitySpriteMap.put(_entities[id], entitySprites);
        }
    }

    public static Map<Integer, CharacterSprite> getCharacterSprites(String name) {
        return _characterSpriteMap.get(name);
    }

    public static Map<Integer, EntitySprite> getEntitySprites(String name) {
        return _entitySpriteMap.get(name);
    }
}
