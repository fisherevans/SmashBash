package com.fisherevans.wipgame.game.states.play.combat_elements.skills;

import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.game.states.play.characters.SpriteType;
import com.fisherevans.wipgame.game.states.play.combat_elements.Skill;
import com.fisherevans.wipgame.game.states.play.entities.Laser;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class CrazyLaserSkill extends Skill {
    public static final float USAGE_COST = 0.75f;
    public static final float REGEN_RATE = 0.45f;

    public CrazyLaserSkill(GameCharacter owner) {
        super(USAGE_COST, REGEN_RATE, 0.4f, owner);
    }

    @Override
    public boolean executeSkill() {
        Laser laser;
        float diff = 0.2f;
        for(int id = 1;id < 10;id++) {
            laser = new Laser(getOwner().getBody().getCenterX() + -1f*0.3f, getOwner().getBody().getY1()-diff*id, -1f*15, getOwner());
            laser.attachNewLight("laser");
            laser.setLifeSpan(1f);
            PlayState.current.addGameObject(laser);
            laser = new Laser(getOwner().getBody().getCenterX() + 0.3f, getOwner().getBody().getY1()-diff*id , 15, getOwner());
            laser.attachNewLight("laser");
            laser.setLifeSpan(1f);
            PlayState.current.addGameObject(laser);
        }
        return true;
    }
}
