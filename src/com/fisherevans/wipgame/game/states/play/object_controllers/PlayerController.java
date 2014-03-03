package com.fisherevans.wipgame.game.states.play.object_controllers;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.wipgame.game.states.play.Direction;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.input.Key;

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
            if(!state(Key.Up) || _jumpTime > GameCharacter.DEFAULT_JUMP_MAX_TIME || !acceptInput || _crouched) {
                _jumping = false;
            } else {
                _jumpTime += delta;
                pr.getVelocity().setY(GameCharacter.DEFAULT_JUMP_VEL);
            }
        }

        float a = pr.getFloor() == Side.South ? GameCharacter.DEFAULT_X_ACC : GameCharacter.DEFAULT_X_ACC_AIR;
        if (state(Key.Right) && !state(Key.Left) && acceptInput && !_crouched) {
            pr.getAcceleration().setX(pr.getVelocity().getX() < GameCharacter.DEFAULT_X_MAX_MOVE ? a : -a);
            getCharacter().setDirection(Direction.Right);
        } else if (state(Key.Left) && !state(Key.Right) && acceptInput && !_crouched) {
            pr.getAcceleration().setX(pr.getVelocity().getX() > -GameCharacter.DEFAULT_X_MAX_MOVE ? -a : a);
            getCharacter().setDirection(Direction.Left);
        } else {
            pr.getAcceleration().setX(0);
            pr.getVelocity().setX(pr.getVelocity().getX() - a*delta*pr.getVelocity().getX()/ GameCharacter.DEFAULT_X_DE_ACC);
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
