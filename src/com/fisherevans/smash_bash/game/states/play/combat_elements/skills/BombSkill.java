package com.fisherevans.smash_bash.game.states.play.combat_elements.skills;

import com.fisherevans.eventRouter.EventAction;
import com.fisherevans.eventRouter.EventRouter;
import com.fisherevans.smash_bash.game.states.play.Direction;
import com.fisherevans.smash_bash.game.states.play.PlayState;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Skill;
import com.fisherevans.smash_bash.game.states.play.entities.Bomb;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class BombSkill extends Skill {
    public static final float USAGE_COST = 0.75f;
    public static final float REGEN_RATE = 0.45f;

    public BombSkill(GameCharacter owner) {
        super(USAGE_COST, REGEN_RATE, 0.4f, owner);
        EventRouter.subscribe(this, "play");
    }

    @EventAction(1)
    @Override
    public boolean executeSkill() {
        float dir = getOwner().getDirection() == Direction.Right ? 1f : -1f;
        Bomb bomb = new Bomb(getOwner().getBody().getCenterX() + dir*0.3f, getOwner().getBody().getCenterY()+0.11f, dir*10f, 10f, getOwner());
        PlayState.current.addGameObject(bomb);
        return true;
    }
}
