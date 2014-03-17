package com.fisherevans.wipgame.game.states.play.combat_elements;

import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.log.Log;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public abstract class Skill {
    public static float MAX_CHARGE = 1f;

    public static Log log = new Log(Skill.class);

    private float _charge, _usageCost, _regenRate, _animationTime;
    private GameCharacter _owner;

    private CharacterAction _characterAction = null;

    public Skill(float usageCost, float regenRate, float animationTime, GameCharacter owner) {
        _charge = MAX_CHARGE;
        _usageCost = usageCost;
        _regenRate = regenRate;
        _animationTime = animationTime;
        _owner = owner;
    }

    public void update(float delta) {
        float regenScale = getOwner() instanceof GameCharacter ? (((GameCharacter)getOwner()).getState() == CharacterState.CROUCHED ? 1.5f : 1f) : 1f;
        _charge += delta*_regenRate*regenScale;
        if(_charge > MAX_CHARGE)
            _charge = MAX_CHARGE;
    }

    public boolean useSkill() {
        if(_usageCost <= _charge) {
            if(_owner.getState() == CharacterState.CROUCHED)
                return false;
            if(executeSkill()) {
                _charge -= _usageCost;
                return true;
            }
        }
        return false;
    }

    public abstract boolean executeSkill();

    public float getCharge() {
        return _charge;
    }

    public void setCharge(float charge) {
        _charge = charge;
    }

    public float getUsageCost() {
        return _usageCost;
    }

    public void setUsageCost(float usageCost) {
        _usageCost = usageCost;
    }

    public float getRegenRate() {
        return _regenRate;
    }

    public void setRegenRate(float regenRate) {
        _regenRate = regenRate;
    }

    public GameCharacter getOwner() {
        return _owner;
    }

    public void setOwner(GameCharacter owner) {
        _owner = owner;
    }

    public float getAnimationTime() {
        return _animationTime;
    }

    public void setAnimationTime(float animationTime) {
        _animationTime = animationTime;
    }

    public CharacterAction getCharacterAction() {
        return _characterAction;
    }

    public void setCharacterAction(CharacterAction characterAction) {
        _characterAction = characterAction;
    }

    public static Skill getSkill(Class clazz, GameCharacter owner) {
        Skill skill = null;
        try {
            skill = (Skill) clazz.getConstructor(GameCharacter.class).newInstance(owner);
        } catch(Exception e) {
            log.error("Failed to load secondary skill for: " + clazz.getName());
            log.error(e.toString());
        }
        return skill;
    }
}
