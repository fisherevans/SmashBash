package com.fisherevans.wipgame.game.states.play.combat_elements.effects;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.combat_elements.Effect;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class RadialForceEffect extends Effect {
    private float _magnitude;
    private Vector _center;
    private float _radius;
    private boolean _linearDamper;

    public RadialForceEffect(float magnitude, Vector center, float radius) {
        _magnitude = magnitude;
        _center = center;
        _radius = radius;
        _linearDamper = true;
    }

    public RadialForceEffect(float magnitude, Vector center) {
        _magnitude = magnitude;
        _center = center;

        _radius = 0;
        _linearDamper = false;
    }

    @Override
    public void doEffect(GameCharacter character) {
        Vector diff = new Vector(character.getBody().getCenterX(), character.getBody().getCenterY())
                .subtract(_center);
        Vector force = diff.normalize().scale(_magnitude);
        if(_linearDamper) {
            float diffLength = diff.length();
            if(diffLength > _radius)
                force.scale(0);
            else
                force.scale(diffLength/_radius);
        }
        character.getBody().getVelocity().add(force);
    }
}
