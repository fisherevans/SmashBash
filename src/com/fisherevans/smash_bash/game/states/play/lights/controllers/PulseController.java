package com.fisherevans.smash_bash.game.states.play.lights.controllers;

import com.fisherevans.smash_bash.game.states.play.lights.Light;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class PulseController extends LightController {
    private final float SPEED = 0.5f;
    private final float MAGNITUDE = 0.1f;

    private float _time;
    private Color _baseColor;

    public PulseController(Light light)
    {
        super(light);
        _time = 0;
        _baseColor = light.getColor().copy();
    }

    @Override
    public void update(float delta)
    {
        _time += delta*SPEED;
        float alpha = (float) Math.sin(Math.toRadians(360 * _time));
        getLight().setColor(_baseColor.brighter((alpha+1f)*MAGNITUDE));
    }
}
