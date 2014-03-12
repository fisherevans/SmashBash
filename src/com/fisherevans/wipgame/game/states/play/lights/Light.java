package com.fisherevans.wipgame.game.states.play.lights;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.states.play.lights.controllers.LightController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class Light {
    private Image _image;
    private float _radius;
    private Color _color;
    private Vector _position;
    private LightController _lightController;

    public Light(LightSettings settings, Vector position) {
        _image = settings.getImageCopy();
        _radius = settings.getRadius();
        _color = settings.getColorCopy();
        _position = position;
        try {
            _lightController = (LightController) settings.getControllerClass()
                    .getConstructor(Light.class)
                    .newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Image getImage() {
        return _image;
    }

    public void setImage(Image image) {
        _image = image;
    }

    public float getRadius() {
        return _radius;
    }

    public void setRadius(float radius) {
        _radius = radius;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public Vector getPosition() {
        return _position;
    }

    public void setPosition(Vector position) {
        _position = position;
    }

    public LightController getLightController() {
        return _lightController;
    }

    public void setLightController(LightController lightController) {
        _lightController = lightController;
    }
}
