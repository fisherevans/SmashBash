package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.game.states.play.entities.EntitySprite;
import com.fisherevans.wipgame.log.Log;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class Sprites {
    public static final String ENTITY_DEFINITIONS = "res/img/sprites/entities.txt";

    public static final float PADDING_PERCENTAGE = 0.5f;

    public static final Log log = new Log(Sprites.class);

    private static Map<String, Map<Integer, CharacterSprite>> _characterSpriteMap;
    private static Map<String, Map<Integer, EntitySprite>> _entitySpriteMap;

    public static void load() {
        try {
            loadCharacters();
            loadEntities();
        } catch (Exception e) {
            log.error("Failed to load sprites.");
            e.printStackTrace();
        }
    }

    private static void loadCharacters() {
        _characterSpriteMap = new HashMap<>();
        Map<Integer, CharacterSprite> characterSprites;
        for(Settings.Setting setting:Settings.getSetting("characters.define").getChildren()) {
            characterSprites = new HashMap<>();
            for(Integer size: Config.SPRITE_SIZES) {
                characterSprites.put(size, new CharacterSprite(
                        Images.getImage("sprites/characters/re-sized/" + size + "/" + setting.getName()),
                        size*2, size*3));
            }
            _characterSpriteMap.put(setting.getName(), characterSprites);
        }
    }

    private static void loadEntities() throws FileNotFoundException {
        _entitySpriteMap = new HashMap<>();
        Map<Integer, EntitySprite> entitySprites;
        for(Settings.Setting setting:Settings.getSetting("entities").getChildren()) {
            try {
                entitySprites = new HashMap<>();
                for(Integer size: Config.SPRITE_SIZES) {
                    entitySprites.put(size, new EntitySprite(
                            Images.getImage("sprites/entities/re-sized/" + size + "/" + setting.getName()),
                            setting.getChild("frameCount").integerValue(),
                            setting.getChild("secondsPerFrame").floatValue()));
                }
                _entitySpriteMap.put(setting.getName(), entitySprites);
            } catch (Exception e) {
                log.error("Failed to load entity: " + setting.toString());
                log.error(e.toString());
                break;
            }
        }
    }

    public static Map<Integer, CharacterSprite> getCharacterSprites(String name) {
        return _characterSpriteMap.get(name);
    }

    public static Map<Integer, EntitySprite> getEntitySprites(String name) {
        return _entitySpriteMap.get(name);
    }
}
