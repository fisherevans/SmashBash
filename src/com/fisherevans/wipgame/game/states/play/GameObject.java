package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.wipgame.tools.MathUtil;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/28/14
 */
public abstract class GameObject {
    public static final float DEFAULT_DIRECTION_FLIP_TIME = 0.15f/2f;
    public static final float DEFAULT_DIRECTION_ACC_USE_VEL = 0.1f;

    private Rectangle _body;
    private Controller _controller;

    private float _directionTime;
    private Direction _direction;

    public GameObject(Rectangle body) {
        _body = body;
        _controller = null;

        _direction = Direction.Right;

        body.setObject(this);
    }

    public final void update(float delta) {
        float vel = _body.getVelocity().getX();
        if(Math.abs(vel) > DEFAULT_DIRECTION_ACC_USE_VEL)
            _direction = vel < 0 ? Direction.Left : Direction.Right;

        _directionTime += delta*(_direction == Direction.Right ? 1f : -1f);
        if(_directionTime < -DEFAULT_DIRECTION_FLIP_TIME)
            _directionTime = -DEFAULT_DIRECTION_FLIP_TIME;
        if(_directionTime > DEFAULT_DIRECTION_FLIP_TIME)
            _directionTime = DEFAULT_DIRECTION_FLIP_TIME;

        updateObject(delta);
    }

    public abstract void updateObject(float delta);

    public abstract Image getImage(int size);

    public float getImageFlipScale() {
        float scale = _directionTime/DEFAULT_DIRECTION_FLIP_TIME;
        scale = MathUtil.clamp(-1f, scale, 1f);
        return scale;
    }

    public Rectangle getBody() {
        return _body;
    }

    public void setBody(Rectangle body) {
        _body = body;
    }

    public Controller getController() {
        return _controller;
    }

    public void setController(Controller controller) {
        _controller = controller;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void setDirection(Direction direction) {
        _direction = direction;
    }
}
