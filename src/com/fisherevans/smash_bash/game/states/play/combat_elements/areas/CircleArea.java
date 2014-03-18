package com.fisherevans.smash_bash.game.states.play.combat_elements.areas;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.smash_bash.game.states.play.GameObject;
import com.fisherevans.smash_bash.game.states.play.combat_elements.Area;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public class CircleArea extends Area {
    private Vector _center;
    private float _radius;

    public CircleArea(Vector center, float radius) {
        this(true, center, radius);
    }

    public CircleArea(boolean inclusive, Vector center, float radius) {
        super(inclusive);
        _center = center;
        _radius = radius;
    }

    @Override
    public boolean inArea(GameObject gameObject) {
        Rectangle body = gameObject.getBody();
        float dx = _center.getX() - body.getCenterX();
        float dy = _center.getY() - body.getCenterY();
        return _radius*_radius >= dx*dx + dy*dy;
    }
}
