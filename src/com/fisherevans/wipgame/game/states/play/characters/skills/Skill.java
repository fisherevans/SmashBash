package com.fisherevans.wipgame.game.states.play.characters.skills;

import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public abstract class Skill {
    public static float MAX_CHARGE = 1f;
    private float _charge, _usageCost, _regenRate;
    private GameObject _owner;

    public Skill(float usageCost, float regenRate, GameObject owner) {
        _charge = MAX_CHARGE;
        _usageCost = usageCost;
        _regenRate = regenRate;
        _owner = owner;
    }

    public void update(float delta) {
        float regenScale = getOwner() instanceof GameCharacter ? (((GameCharacter)getOwner()).getState() == CharacterState.CROUCHED ? 1.5f : 1f) : 1f;
        _charge += delta*_regenRate*regenScale;
        if(_charge > MAX_CHARGE)
            _charge = MAX_CHARGE;
    }

    public void useSkill() {
        if(_usageCost <= _charge) {
            if(executeSkill()) {
                _charge -= _usageCost;
            }
        }
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

    public GameObject getOwner() {
        return _owner;
    }

    public void setOwner(GameObject owner) {
        _owner = owner;
    }
}
