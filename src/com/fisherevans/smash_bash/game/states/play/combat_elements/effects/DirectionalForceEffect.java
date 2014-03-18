package com.fisherevans.smash_bash.game.states.play.combat_elements.effects;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Effect;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class DirectionalForceEffect extends Effect {
    private Vector _velocity;

    public DirectionalForceEffect(Vector velocity) {
        _velocity = velocity;
    }

    @Override
    public void doEffect(GameCharacter character) {
        character.getBody().getVelocity().add(_velocity);
    }
}
