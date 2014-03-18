package com.fisherevans.smash_bash.game.states.play.combat_elements.effects;

import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Effect;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class HealthDeltaEffect extends Effect {
    private int healthDelta;

    public HealthDeltaEffect(int healthDelta) {
        this.healthDelta = healthDelta;
    }

    public int getHealthDelta() {
        return healthDelta;
    }

    @Override
    public void doEffect(GameCharacter character) {
        character.adjustHealth(healthDelta);
    }
}
