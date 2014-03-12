package com.fisherevans.wipgame.game.states.play.object_controllers;


import com.fisherevans.fizzics.listeners.CollisionListener;
import com.fisherevans.wipgame.game.states.play.Controller;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Inputs;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public abstract class CharacterController extends Controller implements CollisionListener {
    private int _inputSource;
    private GameCharacter _character;

    protected CharacterController(GameCharacter character, int inputSource) {
        super(character);
        _character = character;
        _inputSource = inputSource;
    }

    public abstract void up(Key key);

    public abstract void down(Key key);

    public GameCharacter getCharacter() {
        return _character;
    }

    public boolean state(Key key) {
        return Inputs.controllers.get(_inputSource).keyState(key);
    }

    public int getInputSource() {
        return _inputSource;
    }
}
