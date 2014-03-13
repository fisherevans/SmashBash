package com.fisherevans.wipgame.game.game_config;

import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.resources.Sprites;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterDefinition {
    private String _code, _name;
    private Map<Integer, CharacterSprite> _sprites;
    private Class _primarySkill, _secondarySkill;
    private Float _maxSpeed, _xAcceleration, _xDeAcceleration, _xAccelerationInAir, _xDeAccelerationInAir;
    private Float _jumpVelocity, _jumpTime;
    private Float _healthScale;

    public void setCode(String code) {
        _code = code;
        _sprites = Sprites.getCharacterSprites(_code);
    }

    public Map<Integer, CharacterSprite> getSprites() {
        return _sprites;
    }

    public String getCode() {
        return _code;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Class getPrimarySkill() {
        return _primarySkill;
    }

    public void setPrimarySkill(Class primarySkill) {
        _primarySkill = primarySkill;
    }

    public Class getSecondarySkill() {
        return _secondarySkill;
    }

    public void setSecondarySkill(Class secondarySkill) {
        _secondarySkill = secondarySkill;
    }

    public Float getMaxSpeed() {
        return _maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        _maxSpeed = maxSpeed;
    }

    public Float getXAcceleration() {
        return _xAcceleration;
    }

    public void setXAcceleration(Float xAcceleration) {
        _xAcceleration = xAcceleration;
    }

    public Float getXDeAcceleration() {
        return _xDeAcceleration;
    }

    public void setXDeAcceleration(Float xDeAcceleration) {
        _xDeAcceleration = xDeAcceleration;
    }

    public Float getXAccelerationInAir() {
        return _xAccelerationInAir;
    }

    public void setXAccelerationInAir(Float xAccelerationInAir) {
        _xAccelerationInAir = xAccelerationInAir;
    }

    public Float getXDeAccelerationInAir() {
        return _xDeAccelerationInAir;
    }

    public void setXDeAccelerationInAir(Float xDeAccelerationInAir) {
        _xDeAccelerationInAir = xDeAccelerationInAir;
    }

    public Float getJumpVelocity() {
        return _jumpVelocity;
    }

    public void setJumpVelocity(Float jumpVelocity) {
        _jumpVelocity = jumpVelocity;
    }

    public Float getJumpTime() {
        return _jumpTime;
    }

    public void setJumpTime(Float jumpTime) {
        _jumpTime = jumpTime;
    }

    public Float getHealthScale() {
        return _healthScale;
    }

    public void setHealthScale(Float healthScale) {
        _healthScale = healthScale;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CharacterDefinition)
                && ((CharacterDefinition) obj).getCode().equals(_code);
    }
}
