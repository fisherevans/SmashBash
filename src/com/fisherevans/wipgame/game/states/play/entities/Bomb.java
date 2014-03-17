package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.combat_elements.AreaEffect;
import com.fisherevans.wipgame.game.states.play.combat_elements.areas.CircleArea;
import com.fisherevans.wipgame.game.states.play.combat_elements.effects.*;
import com.fisherevans.wipgame.game.states.play.lights.Light;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Lights;

/**
 * Author: Fisher Evans
 * Date: 3/2/14
 */
public class Bomb extends Entity implements IntersectionListener {
    public static Log log = new Log(Bomb.class);

    private GameObject _owner;

    public Bomb(float x, float y, float dx, float dy, GameObject owner) {
        super(getCenteredBody(x, y, 0.5f, 0.5f), "bomb", 2f);
        getBody().setVelocity(new Vector(dx, dy));
        getBody().setResolveWithStaticOnly(true);
        getBody().addIntersectionListener(this);
        getBody().setRestitution(0.75f);

        _owner = owner;
    }

    @Override
    public void destroyEntity() {
        Entity explosion = new Entity(getCenteredBody(getBody().getCenterX(), getBody().getCenterY()), "bomb_explosion");
        explosion.getBody().setStatic(true);
        explosion.getBody().setSolid(false);
        explosion.setSpriteScale(2f);
        explosion.attachNewLight("bomb_explosion");
        PlayState.current.addGameObject(explosion);

        Vector center = new Vector(getBody().getCenterX(), getBody().getCenterY());
        float radius = 3f;
        float force = 10f;
        int damage = -45;

        PlayState.current.addAreaEffect(new AreaEffect()
                .addArea(new CircleArea(center, radius))
                .addEffect(new HealthDeltaEffect(damage))
                .addEffect(new RadialForceEffect(force, center))
                .addEffect(new KnockdownEffect(1.5f)));
    }

    @Override
    public void intersection(Rectangle rectangle, Rectangle rectangle2) {
        if(rectangle2.getObject() instanceof GameCharacter && rectangle2.getObject() != _owner) {
            destroy();
        }
    }
}
