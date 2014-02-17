package com.fisherevans.wipgame.game.states.util.color;

import com.fisherevans.wipgame.tools.MathUtil;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class ColorInterpolation {
    private Color _color1, _color2;
    private float _interp, _speed;

    public ColorInterpolation(Color color1, Color color2, float speed) {
        _color1 = color1;
        _color2 = color2;
        _speed = speed;
        _interp = 0;
    }

    public void update(float delta) {
        _interp += delta*_speed;
        _interp = MathUtil.clamp(0, _interp, 1f);
    }

    public Color getColor() {
        return getColorAt(_interp);
    }

    public Color getInverseColor() {
        return getColorAt(1f - _interp);
    }

    public Color getColorAt(float interp) {
        float inv = 1 - interp;
        return new Color(
                _color1.r*interp + _color2.r*inv,
                _color1.g*interp + _color2.g*inv,
                _color1.b*interp + _color2.b*inv
        );
    }
}
