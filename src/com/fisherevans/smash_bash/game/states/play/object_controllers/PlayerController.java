package com.fisherevans.smash_bash.game.states.play.object_controllers;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.smash_bash.game.states.play.Direction;
import com.fisherevans.smash_bash.game.states.play.characters.GameCharacter;
import com.fisherevans.smash_bash.game.states.play.characters.CharacterState;
import com.fisherevans.smash_bash.input.Key;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class PlayerController extends CharacterController {
    public static float STANDING_HEIGHT = 2f;
    public static float CROUCHING_HEIGHT = 1f;

    private boolean _canJump = true, _jumping = false;
    private float _jumpTime = 0f;
    private boolean _crouched = false;

    public PlayerController(GameCharacter character, int inputSource) {
        super(character, inputSource);
    }

    @Override
    public void update(float delta) {
        boolean acceptInput = getCharacter().acceptInput();

        Rectangle pr = getCharacter().getBody();

        if(_jumping) {
            if(!state(Key.Up) || _jumpTime > getCharacter().getDefinition().jumpTime || !acceptInput || _crouched) {
                _jumping = false;
            } else {
                _jumpTime += delta;
                pr.getVelocity().setY(getCharacter().getDefinition().jumpVelocity);
            }
        }

        Direction moveDirection = null;
        if(state(Key.Right) && !state(Key.Left) && acceptInput && !_crouched) moveDirection = Direction.Right;
        else if(state(Key.Left) && !state(Key.Right) && acceptInput && !_crouched) moveDirection = Direction.Left;
        float maxSpeed, acc;
        if(!(moveDirection == null && getCharacter().getBody().getFloor() != null)) { // not on floor not moving (let friction do its job)
            if(getCharacter().getBody().getFloor() == null) {
                if(moveDirection == null) { // air resistance
                    maxSpeed = 0;
                    acc = getCharacter().getDefinition().airDeAcceleration;
                } else {
                    maxSpeed = getCharacter().getDefinition().maxAirSpeed;
                    acc = getCharacter().getDefinition().airAcceleration;
                }
            } else {
                maxSpeed = getCharacter().getDefinition().maxLandSpeed;
                acc = getCharacter().getDefinition().landAcceleration;
            }
            if(moveDirection == Direction.Left) {
                maxSpeed *= -1f;
                acc *= -1f;
            } else if(moveDirection == null)
                acc *= -1*Math.signum(getCharacter().getBody().getVelocity().getX());
            Rectangle body = getCharacter().getBody();
            if((maxSpeed < 0 && maxSpeed > body.getVelocity().getX()) ||
                    (maxSpeed > 0 && maxSpeed < body.getVelocity().getX()))
                acc *= -1;
            body.getVelocity().setX(body.getVelocity().getX() + acc*delta);
        }

        if(_crouched) {
            getCharacter().setState(CharacterState.CROUCHED);
        } else if(getCharacter().getBody().getFloor() == null) {
            getCharacter().setState(CharacterState.FALLING);
        } else if(state(Key.Right) == false && state(Key.Left) == false) {
            getCharacter().setState(CharacterState.IDLE);
        } else {
            getCharacter().setState(CharacterState.STRAFING);
        }
    }

    @Override
    public void up(Key key) {
        if(key == Key.Up) {
            _canJump = true;
            _jumping = false;
        } else if(key == Key.Down) {
            _crouched = false;
            getCharacter().getBody().setHeight(STANDING_HEIGHT);
        }
    }

    @Override
    public void down(Key key) {
        if(key == Key.Up && _canJump && getCharacter().getBody().getFloor() == Side.South && !_crouched) {
            _canJump = false;
            _jumping = true;
            _jumpTime = 0f;
        } else if(key == Key.Down) {
            getCharacter().setCurrentAction(null);
            _crouched = true;
            getCharacter().getBody().setHeight(CROUCHING_HEIGHT);
        }
    }

    @Override
    public void collision(Rectangle rectangle, Rectangle rectangle2, Side side) {
        if(side == Side.North && _jumping) {
            _jumping = false;
        }
    }
}
