package com.fisherevans.smash_bash.game.states.play.lights.controllers;

import com.fisherevans.smash_bash.game.states.play.lights.Light;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class TorchController extends LightController {
    private Color _baseColor;
    private long _nextFlicker;

    private final int FLICKER_DURATION = 150;
    private final float FLICKER_DELTA = 0.15f;

    public TorchController(Light light)
    {
        super(light);
        _baseColor = getLight().getColor();
    }

    @Override
    public void update(float delta)
    {
        if(System.currentTimeMillis() > _nextFlicker)
        {
            getLight().setColor(_baseColor.darker((float) Math.random() * FLICKER_DELTA));
            _nextFlicker = System.currentTimeMillis() + (long)(Math.random()*FLICKER_DURATION);
        }
    }
}
