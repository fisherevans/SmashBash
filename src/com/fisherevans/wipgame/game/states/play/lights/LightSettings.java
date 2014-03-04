package com.fisherevans.wipgame.game.states.play.lights;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class LightSettings {
    private Image _image;
    private float _radius;
    private Color _color;
    private Class _controllerClass;
    private String _name;

    public LightSettings(Image image, float radius, Color color, Class controllerClass, String name) {
        _image = image;
        _radius = radius;
        _color = color;
        _controllerClass = controllerClass;
        _name = name;
    }

    public Image getImageCopy() {
        return _image.copy();
    }

    public float getRadiusCopy() {
        return _radius;
    }

    public Color getColorCopy() {
        return _color.copy();
    }

    public Class getControllerClassCopy() {
        return _controllerClass;
    }

    public String getName() {
        return _name;
    }
}
