package com.fisherevans.smash_bash.game.states.play.lights.controllers;

import com.fisherevans.smash_bash.game.states.play.lights.Light;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public abstract class LightController {
    private Light _light;

    public LightController(Light light) {
        _light = light;
    }

    public abstract void update(float delta);

    public Light getLight() {
        return  _light;
    }
}
