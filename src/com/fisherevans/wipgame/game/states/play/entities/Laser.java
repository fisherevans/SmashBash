package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.*;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.game.states.play.characters.Character;

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
        getBody().setIgnoreGravity(true);

        _owner = owner;
    }

    @Override
    public void intersection(Rectangle rectangle, Rectangle rectangle2) {
        if(rectangle2.getObject() instanceof Character && rectangle2.getObject() != _owner) {
            float dir = Math.signum(this.getBody().getVelocity().getX());
            rectangle2.getVelocity().setX(dir * 1.5f);
            ((Character)rectangle2.getObject()).damage(5);
            destroy();
        } else if(rectangle2.getObject() == null)
            destroy();
    }

    private void destroy() {
        PlayState.current.removeGameObject(this);
        Entity explosion = new Entity(getCenteredBody(getBody().getCenterX(), getBody().getCenterY()), "laser_explosion");
        explosion.getBody().setIgnoreGravity(true);
        explosion.getBody().setResolveWithStaticOnly(true);
        PlayState.current.addGameObject(explosion);
    }
}
