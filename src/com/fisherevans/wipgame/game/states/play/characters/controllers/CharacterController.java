package com.fisherevans.wipgame.game.states.play.characters.controllers;


import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Side;
import com.fisherevans.fizzics.listeners.CollisionListener;
import com.fisherevans.wipgame.game.states.play.characters.*;
import com.fisherevans.wipgame.game.states.play.characters.Character;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.input.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public abstract class CharacterController implements InputsListener, CollisionListener {
    private int _inputSource;
    private com.fisherevans.wipgame.game.states.play.characters.Character _character;

    private Map<Key, Boolean> _keyMap;

    protected CharacterController(Character character, int inputSource) {
        _character = character;
        _inputSource = inputSource;

        _keyMap = new HashMap<>();
        for(Key key:Key.values())
            _keyMap.put(key, false);

        _character.getBody().addCollisionListener(this);
    }

    public abstract void update(float delta);

    @Override
    public void keyDown(Key key, int inputSource) {
        if(_inputSource == inputSource) {
            _keyMap.put(key, true);
            down(key);
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {
        if(_inputSource == inputSource) {
            _keyMap.put(key, false);
            up(key);
        }
    }

    public abstract void up(Key key);

    public abstract void down(Key key);

    public Character getCharacter() {
        return _character;
    }

    public boolean state(Key key) {
        Boolean state = _keyMap.get(key);
        return state != null ? state : false;
    }

    @Override
    public void collision(Rectangle rectangle, Rectangle rectangle2, Side side) {
    }
}
