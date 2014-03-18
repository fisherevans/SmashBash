package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.graphics.CharacterSprite;
import com.fisherevans.wipgame.tools.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterDefinition {

    private String _code;
    private List<Map<Integer, CharacterSprite>> _spriteList;
    private Integer[] _spriteIds;

    public String name;
    public Class primarySkill, secondarySkill;
    public Float maxLandSpeed, landAcceleration;
    public Float maxAirSpeed, airAcceleration, airDeAcceleration;
    public Float jumpVelocity, jumpTime;
    public Float healthScale, framesPerSecond;
    public Integer spriteCount;

    public CharacterDefinition(String code) {
        _code = code;
    }

    public void loadSprites() {
        _spriteList = new ArrayList<Map<Integer, CharacterSprite>>();
        Map<Integer, CharacterSprite> spriteMap;
        for(int spriteId = 0;spriteId < spriteCount;spriteId++) {
            spriteMap = new HashMap<Integer, CharacterSprite>();
            for(Integer size:Config.SPRITE_SIZES) {
                spriteMap.put(size, new CharacterSprite(Images.getImage("sprites/characters/re-sized/" + size + "/" + _code),
                        0, size*3*spriteId, size*2, size*3));
            }
            _spriteList.add(spriteMap);
        }
        _spriteIds = MathUtil.getIncrementedArray(spriteCount);
    }

    public String getCode() {
        return _code;
    }

    public List<Map<Integer, CharacterSprite>> getSpriteList() {
        return _spriteList;
    }

    public Map<Integer, CharacterSprite> getSprite(int id) {
        return _spriteList.get(id);
    }

    public Integer[] getSpriteIds() {
        return _spriteIds;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CharacterDefinition)
                && ((CharacterDefinition) obj).getCode().equals(_code);
    }
}
