package com.fisherevans.wipgame.game.states.play.object_controllers;


import com.fisherevans.fizzics.listeners.CollisionListener;
import com.fisherevans.wipgame.game.states.play.Controller;
import com.fisherevans.wipgame.game.states.play.characters.Character;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Inputs;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public abstract class CharacterController extends Controller implements InputsListener, CollisionListener {
    private int _inputSource;
    private com.fisherevans.wipgame.game.states.play.characters.Character _character;

    protected CharacterController(Character character, int inputSource) {
        super(character);
        _character = character;
        _inputSource = inputSource;
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(_inputSource == inputSource) {
            down(key);
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {
        if(_inputSource == inputSource) {
            up(key);
        }
    }

    public abstract void up(Key key);

    public abstract void down(Key key);

    public Character getCharacter() {
        return _character;
    }

    public boolean state(Key key) {
        return Inputs.keyState(key, _inputSource);
    }
}
