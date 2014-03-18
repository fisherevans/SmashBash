package com.fisherevans.smash_bash.game.states.play.combat_elements.areas;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.smash_bash.game.states.play.GameObject;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Area;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class RectangleArea extends Area {
    private Rectangle _bounds;

    public RectangleArea(Rectangle bounds) {
        this(true, bounds);
    }

    public RectangleArea(boolean inclusive, Rectangle bounds) {
        super(inclusive);
        _bounds = bounds;
    }

    @Override
    public boolean inArea(GameObject gameObject) {
        return gameObject.getBody().intersects(_bounds);
    }
}
