package com.fisherevans.smash_bash.game.states.play.lights;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class LightSettings {
    private String _name;
    private Integer _id;
    private Image _image;
    private float _radius;
    private Color _color;
    private Class _controllerClass;

    public LightSettings(String name, Integer id, Image image, float radius, Color color, Class controllerClass) {
        _name = name;
        _id = id;
        _image = image;
        _radius = radius;
        _color = color;
        _controllerClass = controllerClass;
    }

    public String getName() {
        return _name;
    }

    public Integer getId() {
        return _id;
    }

    public Image getImageCopy() {
        return _image.copy();
    }

    public float getRadius() {
        return _radius;
    }

    public Color getColorCopy() {
        return _color.copy();
    }

    public Class getControllerClass() {
        return _controllerClass;
    }
}
