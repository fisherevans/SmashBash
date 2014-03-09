package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.game.states.play.entities.EntitySprite;
import com.fisherevans.wipgame.log.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class Sprites {
    public static final String[] _characters = { "base" };

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

    private static void loadEntities() throws FileNotFoundException {
        _entitySpriteMap = new HashMap<>();

        Scanner input = new Scanner(new File(ENTITY_DEFINITIONS));
        String line, name;
        String[] split;
        Map<Integer, EntitySprite> entitySprites;
        while(input.hasNextLine()) {
            line = input.nextLine();
            try {
                if(line.startsWith("#"))
                    continue;
                split = line.split(",");
                name = split[0];
                entitySprites = new HashMap<>();
                for(Integer size: Config.SIZES) {
                    entitySprites.put(size, new EntitySprite(
                            Images.getImage("sprites/entities/re-sized/" + size + "/" + name),
                            Integer.parseInt(split[1]), Float.parseFloat(split[2])));
                }
                _entitySpriteMap.put(name, entitySprites);
            } catch (Exception e) {
                e.printStackTrace();
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
