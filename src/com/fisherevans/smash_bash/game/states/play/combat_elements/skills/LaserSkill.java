package com.fisherevans.smash_bash.game.states.play.combat_elements.skills;

import com.fisherevans.smash_bash.game.states.play.Direction;
import com.fisherevans.smash_bash.game.states.play.PlayState;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Skill;
import com.fisherevans.smash_bash.game.states.play.entities.Laser;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class LaserSkill extends Skill {
    public static final float USAGE_COST = 0.1f;
    public static final float REGEN_RATE = 0.15f;

    public LaserSkill(GameCharacter owner) {
        super(USAGE_COST, REGEN_RATE, 0.4f, owner);
    }

    @Override
    public boolean executeSkill() {
        float dir = getOwner().getDirection() == Direction.Right ? 1f : -1f;
        Laser laser = new Laser(getOwner().getBody().getCenterX() + dir*0.3f, getOwner().getBody().getCenterY()+0.11f, dir*30, getOwner());
        laser.attachNewLight("laser");
        PlayState.current.addGameObject(laser);
        return true;
    }
}
