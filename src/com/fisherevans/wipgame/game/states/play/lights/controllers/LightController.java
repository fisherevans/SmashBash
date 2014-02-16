package com.fisherevans.wipgame.game.states.play.lights.controllers;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.states.play.lights.Light;
import com.fisherevans.wipgame.game.states.play.lights.LightManager;
import com.fisherevans.wipgame.game.states.play.lights.LightSettings;

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
