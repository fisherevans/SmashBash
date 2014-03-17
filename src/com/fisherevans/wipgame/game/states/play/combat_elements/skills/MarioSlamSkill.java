package com.fisherevans.wipgame.game.states.play.combat_elements.skills;

import com.fisherevans.eventRouter.EventAction;
import com.fisherevans.eventRouter.EventRouter;
import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.combat_elements.AreaEffect;
import com.fisherevans.wipgame.game.states.play.combat_elements.Skill;
import com.fisherevans.wipgame.game.states.play.combat_elements.areas.CircleArea;
import com.fisherevans.wipgame.game.states.play.combat_elements.effects.HealthDeltaEffect;
import com.fisherevans.wipgame.game.states.play.entities.Entity;
import com.fisherevans.wipgame.graphics.CharacterSprite;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 3/3/14
 */
public class MarioSlamSkill extends Skill {
    public static final float USAGE_COST = 1f;
    public static final float REGEN_RATE = 0.1f;
    public static final float SLAM_DAMAGE = -1.5f;
    public static final int SHOCK_WAVE_DAMAGE = -50;

    public MarioSlamSkill(GameCharacter owner) {
        super(USAGE_COST, REGEN_RATE, 0.4f, owner);
        EventRouter.subscribe(this, "play");
    }

    @EventAction(1)
    @Override
    public boolean executeSkill() {
        if(getOwner().getBody().getFloor() == null) {
            setCharacterAction(new CharacterAction(CharacterSprite.Type.Secondary, 1.5f, true));
            getOwner().getBody().getVelocity().setX(0);
            getOwner().getBody().addIntersectionListener(new SlamListener(getOwner()));
            return true;
        } else
            return false;
    }

    private class SlamListener implements IntersectionListener {
        private GameCharacter _owner;
        private List<Rectangle> _damaged;
        private boolean _stopped = false;

        private SlamListener(GameCharacter owner) {
            _owner = owner;
            _damaged = new LinkedList<>();
        }

        @Override
        public void intersection(Rectangle rectangle, Rectangle rectangle2) {
            if(!_stopped) {
                if(rectangle2.getObject() instanceof GameCharacter && !_damaged.contains(rectangle2)) {
                    _damaged.add(rectangle2);
                    GameCharacter c = (GameCharacter) rectangle2.getObject();
                    c.setCurrentAction(new CharacterAction(CharacterSprite.Type.Down, 2f, true));
                    c.adjustHealth((int) (SLAM_DAMAGE * Math.abs(_owner.getBody().getVelocity().getY())));
                } else if(rectangle2.isStatic()) {
                    PlayState.current.addAreaEffect(new AreaEffect(AreaEffect.SetType.Exclusive, _owner)
                            .addArea(new CircleArea(new Vector(_owner.getBody().getCenterX(), _owner.getBody().getCenterY()), 2.5f))
                            .addEffect(new HealthDeltaEffect(SHOCK_WAVE_DAMAGE)));
                    Entity wave = new Entity(Entity.getCenteredBody(_owner.getBody().getCenterX(), _owner.getBody().getY2()), "slam_wave");
                    wave.getBody().setStatic(true);
                    wave.getBody().setSolid(false);
                    PlayState.current.addGameObject(wave);
                    _stopped = true;
                    _owner.getBody().removeIntersectionListener(this);
                    if(_owner.getCurrentAction() != null && _owner.getCurrentAction().getSpriteType() == CharacterSprite.Type.Secondary)
                        _owner.setCurrentAction(new CharacterAction(CharacterSprite.Type.Secondary, 0.5f, true));
                }
            }
        }
    }
}
