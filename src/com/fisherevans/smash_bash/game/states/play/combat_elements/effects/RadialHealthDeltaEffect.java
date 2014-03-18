package com.fisherevans.smash_bash.game.states.play.combat_elements.effects;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class RadialHealthDeltaEffect extends HealthDeltaEffect {
    private Vector _center;
    private float _radius;

    public RadialHealthDeltaEffect(int healthDelta, Vector center, float radius) {
        super(healthDelta);
        _center = center;
        _radius = radius;
    }

    @Override
    public void doEffect(GameCharacter character) {
        Vector diff = new Vector(character.getBody().getCenterX(), character.getBody().getCenterY())
                .subtract(_center);
        float diffLength = diff.length();
        if(diffLength <= _radius)
            character.adjustHealth((int) (getHealthDelta()*diffLength/_radius));
    }
}
