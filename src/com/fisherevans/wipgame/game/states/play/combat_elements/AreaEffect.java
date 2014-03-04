package com.fisherevans.wipgame.game.states.play.combat_elements;

import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class AreaEffect {
    private List<Area> _areas;
    private List<Effect> _effects;
    private GameCharacter[] _set;
    private SetType _setType;

    public AreaEffect() {
        _areas = new LinkedList<>();
        _effects = new LinkedList<>();
        _set = new GameCharacter[0];
        _setType = SetType.Exclusive;
    }

    public AreaEffect(SetType setType, GameCharacter ... set) {
        _setType = setType;
        _set = set;
        _areas = new LinkedList<>();
        _effects = new LinkedList<>();
    }

    public AreaEffect addArea(Area area) {
        _areas.add(area);
        return this;
    }

    public AreaEffect addEffect(Effect effect) {
        _effects.add(effect);
        return this;
    }

    public void process(GameCharacter character) {
        boolean inSet = inSet(character);
        if(_setType == SetType.Exclusive)
            inSet = !inSet;
        if(inSet) {
            boolean effected = false;
            for(Area area:_areas) {
                if(area.inArea(character)) {
                    if(area.isInclusive())
                        effected = true;
                    else {
                        effected = false;
                        break;
                    }
                }
            }
            if(effected)
                for(Effect effect:_effects)
                    effect.doEffect(character);
        }
    }

    private boolean inSet(GameCharacter character) {
        for(GameCharacter setCharacter:_set)
            if(setCharacter == character)
                return true;
        return false;
    }

    public enum SetType { Inclusive, Exclusive }
}
