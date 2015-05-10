package com.fisherevans.smash_bash.game.states.play.combat_elements.skills;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.smash_bash.game.states.play.Direction;
import com.fisherevans.smash_bash.game.states.play.PlayState;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.combat_elements.AreaEffect;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Skill;
import com.fisherevans.smash_bash.game.states.play.combat_elements.areas.RectangleArea;
import com.fisherevans.smash_bash.game.states.play.combat_elements.effects.DirectionalForceEffect;
import com.fisherevans.smash_bash.game.states.play.combat_elements.effects.HealthDeltaEffect;
import com.fisherevans.smash_bash.game.states.play.combat_elements.effects.KnockdownEffect;
import com.fisherevans.smash_bash.game.states.play.entities.Laser;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class UppercutSkill extends Skill {
    public static final float USAGE_COST = 0.25f;
    public static final float REGEN_RATE = 0.1f;

    public UppercutSkill(GameCharacter owner) {
        super(USAGE_COST, REGEN_RATE, 0.4f, owner);
    }

    @Override
    public boolean executeSkill() {
        float dir = getOwner().getDirection() == Direction.Right ? 0.5f : -0.5f;
        Rectangle areaRect = getOwner().getBody().getCopy();
        areaRect.move(new Vector(dir, 0));
        RectangleArea area = new RectangleArea(areaRect);
        PlayState.current.addAreaEffect(new AreaEffect(AreaEffect.SetType.Exclusive, getOwner())
                .addArea(area)
                .addEffect(new HealthDeltaEffect(-15))
                .addEffect(new KnockdownEffect(0.333f)));
        PlayState.current.addAreaEffect(new AreaEffect()
                .addArea(area)
                .addEffect(new DirectionalForceEffect(new Vector(0, 10f))));
        return true;
    }
}
