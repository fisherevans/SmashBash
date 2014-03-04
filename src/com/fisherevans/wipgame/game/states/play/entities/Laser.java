package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.combat_elements.AreaEffect;
import com.fisherevans.wipgame.game.states.play.combat_elements.areas.CircleArea;
import com.fisherevans.wipgame.game.states.play.combat_elements.effects.DirectionalForceEffect;
import com.fisherevans.wipgame.game.states.play.combat_elements.effects.HealthDeltaEffect;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;

/**
 * Author: Fisher Evans
 * Date: 3/2/14
 */
public class Laser extends Entity implements IntersectionListener {
    public static Log log = new Log(Laser.class);

    private GameObject _owner;

    public Laser(float x, float y, float dx, GameObject owner) {
        super(getCenteredBody(x, y), "laser", -1f);
        getBody().setVelocity(new Vector(dx, 0f));
        getBody().addIntersectionListener(this);
        getBody().setResolveWithStaticOnly(true);
        getBody().setGravityScale(0f);

        _owner = owner;
    }

    @Override
    public void intersection(Rectangle rectangle, Rectangle rectangle2) {
        if((rectangle2.getObject() instanceof GameCharacter && rectangle2.getObject() != _owner) ||
                rectangle2.getObject() == null)
            destroy();
    }

    @Override
    public void destroyEntity() {
        Entity explosion = new Entity(getCenteredBody(getBody().getCenterX(), getBody().getCenterY()), "laser_explosion");
        explosion.getBody().setStatic(true);
        explosion.getBody().setSolid(false);
        explosion.attachNewLight("laser_explosion");
        PlayState.current.addGameObject(explosion);

        Vector center = new Vector(getBody().getCenterX(), getBody().getCenterY());
        float dir = Math.signum(this.getBody().getVelocity().getX());

        PlayState.current.addAreaEffect(new AreaEffect()
                .addArea(new CircleArea(center, 1f))
                .addEffect(new HealthDeltaEffect(-5))
                .addEffect(new DirectionalForceEffect(new Vector(dir*1.5f, 0f))));
    }
}
