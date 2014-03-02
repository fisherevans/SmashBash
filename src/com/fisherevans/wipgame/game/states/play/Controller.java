package com.fisherevans.wipgame.game.states.play;


import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.fizzics.listeners.CollisionListener;
import com.fisherevans.wipgame.game.states.play.characters.Character;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Inputs;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public abstract class Controller implements InputsListener, CollisionListener {
    public Controller(GameObject gameObject) {
        gameObject.getBody().addCollisionListener(this);
    }

    public abstract void update(float delta);

    @Override
    public abstract void collision(Rectangle rectangle, Rectangle rectangle2, Side side);
}
