package com.fisherevans.smash_bash.game.states.play;


import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.fizzics.listeners.CollisionListener;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public abstract class Controller implements CollisionListener {
    public Controller(GameObject gameObject) {
        gameObject.getBody().addCollisionListener(this);
    }

    public abstract void update(float delta);

    @Override
    public abstract void collision(Rectangle rectangle, Rectangle rectangle2, Side side);
}
