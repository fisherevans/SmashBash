package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.log.Log;

/**
 * Author: Fisher Evans
 * Date: 3/2/14
 */
public class Fireball extends Entity implements IntersectionListener {
    public static Log log = new Log(Fireball.class);

    public static int DAMAGE = -5;
    public static int MAX_BOUNCE = 2;

    private GameObject _owner;

    private float _dy;
    private int _bounceCount = 0;

    public Fireball(float x, float y, float dx, float dy, GameObject owner) {
        super(getCenteredBody(x, y, 0.5f, 0.5f), "fireball", 10f);
        getBody().setVelocity(new Vector(dx, dy));
        getBody().setResolveWithStaticOnly(true);
        getBody().addIntersectionListener(this);
        getBody().setGravityScale(3f);
        getBody().setRestitution(1f);

        _owner = owner;
        float vi2 = dy*dy;
        float a = PlayState.current.getWorld().getGravity();
        float d = (Math.abs(y)%1) + 3.5f;
        double vf = Math.sqrt(vi2 + -2*a*d);
        _dy = (float) vf;
    }

    @Override
    public void destroyEntity() {
    }

    @Override
    public void intersection(Rectangle rectangle, Rectangle rectangle2) {
        if(rectangle2.getObject() instanceof GameCharacter && rectangle2.getObject() != _owner) {
            ((GameCharacter)rectangle2.getObject()).adjustHealth(DAMAGE);
            destroy();
        } else if(rectangle2.isStatic() && getBody().getFloor() == Side.South) {
            if(_bounceCount++ >= MAX_BOUNCE)
                destroy();
            else {
                getBody().getVelocity().setY(-_dy);
                getBody().getAcceleration().setY(0f);
            }
        }
    }
}
