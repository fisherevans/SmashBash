package com.fisherevans.wipgame.game.states.play.object_controllers;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.wipgame.game.states.play.Direction;
import com.fisherevans.wipgame.game.states.play.characters.Character;
import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.SpriteType;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.input.Key;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class PlayerController extends CharacterController {
    private boolean _canJump = true, _jumping = false;
    private float _jumpTime = 0f;

    public PlayerController(com.fisherevans.wipgame.game.states.play.characters.Character character, int inputSource) {
        super(character, inputSource);
    }

    @Override
    public void update(float delta) {
        boolean acceptInput = getCharacter().acceptInput();

        Rectangle pr = getCharacter().getBody();

        if(_jumping) {
            if(!state(Key.Up) || _jumpTime > Character.DEFAULT_JUMP_MAX_TIME || !acceptInput) {
                _jumping = false;
            } else {
                _jumpTime += delta;
                pr.getVelocity().setY(Character.DEFAULT_JUMP_VEL);
            }
        }

        float a = pr.getFloor() == Side.South ? Character.DEFAULT_X_ACC : Character.DEFAULT_X_ACC_AIR;
        if (state(Key.Right) && !state(Key.Left) && acceptInput) {
            pr.getAcceleration().setX(pr.getVelocity().getX() < Character.DEFAULT_X_MAX_MOVE ? a : -a);
            getCharacter().setDirection(Direction.Right);
        } else if (state(Key.Left) && !state(Key.Right) && acceptInput) {
            pr.getAcceleration().setX(pr.getVelocity().getX() > -Character.DEFAULT_X_MAX_MOVE ? -a : a);
            getCharacter().setDirection(Direction.Left);
        } else {
            pr.getAcceleration().setX(0);
            pr.getVelocity().setX(pr.getVelocity().getX() - a*delta*pr.getVelocity().getX()/Character.DEFAULT_X_DE_ACC);
        }

        if(getCharacter().getBody().getFloor() == null) {
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
        }
    }

    @Override
    public void down(Key key) {
        if(key == Key.Up && _canJump && getCharacter().getBody().getFloor() == Side.South) {
            _canJump = false;
            _jumping = true;
            _jumpTime = 0f;
        }

        if(key == Key.Back) {
            getCharacter().setCurrentAction(new CharacterAction(SpriteType.Down, 5f, true));
        } else if (key == Key.Select) {
            getCharacter().setCurrentAction(new CharacterAction(SpriteType.Shooting, 0.4f, false));
        }
    }

    @Override
    public void collision(Rectangle rectangle, Rectangle rectangle2, Side side) {
        if(side == Side.North && _jumping) {
            _jumping = false;
        }
    }
}
