package com.fisherevans.wipgame.game.states.play.combat_elements.skills;

import com.fisherevans.wipgame.game.states.play.Direction;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.characters.SpriteType;
import com.fisherevans.wipgame.game.states.play.combat_elements.Skill;
import com.fisherevans.wipgame.game.states.play.entities.Laser;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class LaserSkill extends Skill {
    public static final float USAGE_COST = 0.1f;
    public static final float REGEN_RATE = 0.15f;

    public LaserSkill(GameObject owner) {
        super(USAGE_COST, REGEN_RATE, owner);
    }

    @Override
    public boolean executeSkill() {
        if(getOwner() instanceof GameCharacter) {
            GameCharacter character = (GameCharacter) getOwner();
            if(character.getState() == CharacterState.CROUCHED)
                return false;
            character.setCurrentAction(new CharacterAction(SpriteType.Shooting, 0.4f, false));
        }
        float dir = getOwner().getDirection() == Direction.Right ? 1f : -1f;
        Laser laser = new Laser(getOwner().getBody().getCenterX() + dir*0.3f, getOwner().getBody().getCenterY()+0.11f, dir*30, getOwner());
        PlayState.current.addGameObject(laser);
        return true;
    }
}
