package com.fisherevans.smash_bash.game.states.play;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.smash_bash.game.states.play.lights.Light;
import com.fisherevans.smash_bash.game.states.play.lights.LightSettings;
import com.fisherevans.smash_bash.log.Log;
import com.fisherevans.smash_bash.resources.Lights;
import com.fisherevans.smash_bash.tools.MathUtil;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/28/14
 */
public abstract class GameObject {
    public static final float DEFAULT_DIRECTION_ACC_USE_VEL = 0.1f;

    public enum DirectionMethod { Input, Velocity };

    public static final Log log = new Log(GameObject.class);

    private Rectangle _body;
    private Controller _controller;

    private float _directionTime;
    private Direction _direction;
    private DirectionMethod _directionMethod = DirectionMethod.Velocity;

    private Light _light = null;
    private float _flipTime = 0.15f/2f;

    public GameObject(Rectangle body) {
        _body = body;
        _controller = null;

        _direction = Direction.Right;

        body.setObject(this);
    }

    public final void update(float delta) {
        if(_directionMethod == DirectionMethod.Velocity) {
            float vel = _body.getVelocity().getX();
            if(Math.abs(vel) > DEFAULT_DIRECTION_ACC_USE_VEL)
                _direction = vel < 0 ? Direction.Left : Direction.Right;
        }

        _directionTime += delta*(_direction == Direction.Right ? 1f : -1f);
        _directionTime = MathUtil.clamp(-_flipTime, _directionTime, _flipTime);

        if(_light != null)
            _light.setPosition(new Vector(getBody().getCenterX(), getBody().getCenterY()));

        updateObject(delta);
    }

    public abstract void updateObject(float delta);

    public abstract Image getImage(int size);

    public float getImageFlipScale() {
        float scale = _directionTime/ _flipTime;
        return scale;
    }

    public void attachNewLight(String lightName) {
        attachNewLight(Lights.getLightSettingsByName(lightName));
    }

    public void attachNewLight(LightSettings lightSettings) {
        if(lightSettings == null)
            log.error("Failed to create light in " + this.getClass().getName());
        else
            attachLight(new Light(lightSettings, new Vector(getBody().getCenterX(), getBody().getCenterY())));
    }

    public void attachLight(Light light) {
        detachLight();
        _light = light;
        PlayState.current.getLightManager().getLights().add(light);
    }

    public void detachLight() {
        if(_light != null) {
            PlayState.current.getLightManager().getLights().remove(_light);
        }
    }

    public void destroy() {
        PlayState.current.removeGameObject(this);
        detachLight();
        destroyObject();
    }

    public abstract void destroyObject();

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

    public DirectionMethod getDirectionMethod() {
        return _directionMethod;
    }

    public void setDirectionMethod(DirectionMethod directionMethod) {
        _directionMethod = directionMethod;
    }

    public boolean inRadiusRange(Vector center, float radius) {
        return radius*radius >= Math.pow(center.getX()-getBody().getCenterX(), 2) + Math.pow(center.getY()-getBody().getCenterY(), 2);
    }

    public float getFlipTime() {
        return _flipTime;
    }

    public void setFlipTime(float flipTime) {
        _flipTime = flipTime;
    }
}
