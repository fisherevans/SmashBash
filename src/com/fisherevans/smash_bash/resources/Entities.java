package com.fisherevans.smash_bash.resources;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.graphics.EntitySprite;
import com.fisherevans.smash_bash.log.Log;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/17/14
 */
public class Entities {
    public static final Log log = new Log(Entities.class);

    private static Map<String, Map<Integer, EntitySprite>> _entitySpriteMap;

    public static void load() throws FileNotFoundException {
        _entitySpriteMap = new HashMap<String, Map<Integer, EntitySprite>>();
        Map<Integer, EntitySprite> entitySprites;
        for(Settings.Setting setting:Settings.getSetting("entities").getChildren()) {
            try {
                entitySprites = new HashMap<Integer, EntitySprite>();
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

    public static Map<Integer, EntitySprite> getEntitySprites(String name) {
        return _entitySpriteMap.get(name);
    }
}
