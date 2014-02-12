package com.fisherevans.wipgame.game.states.play.characters;

import com.fisherevans.fizzics.components.Rectangle;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class Character {
    public static final float DEFAULT_JUMP_VEL = 12f;
    public static final float DEFAULT_JUMP_MAX_TIME = 0.25f;
    public static final float DEFAULT_X_ACC = 100f;
    public static final float DEFAULT_X_DE_ACC = 4f;
    public static final float DEFAULT_X_ACC_AIR = 16f;
    public static final float DEFAULT_X_MAX_MOVE = 6f;

    private Rectangle _body;
    private Controller _controller;
    private Color _color;

    private CharacterState _state;
    private CharacterDirection _direction;

    public Character(Rectangle body, Color color) {
        _body = body;
        _color = color;

        _state = CharacterState.IDLE;
        _direction = CharacterDirection.RIGHT;
    }

    public void update(float delta) {
        if(_controller != null)
            _controller.update(delta);
    }

    public Rectangle getBody() {
        return _body;
    }

    public Controller getController() {
        return _controller;
    }

    public void setController(Controller controller) {
        _controller = controller;
    }

    public Color getColor() {
        return _color;
    }

    public CharacterState getState() {
        return _state;
    }

    public void setState(CharacterState state) {
        _state = state;
    }

    public CharacterDirection getDirection() {
        return _direction;
    }

    public void setDirection(CharacterDirection direction) {
        _direction = direction;
    }
}
