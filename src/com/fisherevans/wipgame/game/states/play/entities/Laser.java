package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.fizzics.listeners.IntersectionListener;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.characters.*;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.game.states.play.characters.Character;

/**
 * Author: Fisher Evans
 * Date: 3/2/14
 */
public class Laser extends Entity implements IntersectionListener {
    public static Log log = new Log(Laser.class);

    public Laser(float x, float y, float dx, GameObject owner) {
        super(getCenteredBody(x, y), "laser", -1f);
        getBody().setVelocity(new Vector(dx, 0f));
        getBody().addIntersectionListener(this);
    }

    @Override
    public void intersection(Rectangle rectangle, Rectangle rectangle2) {
        if(rectangle2.getObject() instanceof Character) {
            rectangle2.getVelocity().setY(30f);
        }
    }
}
