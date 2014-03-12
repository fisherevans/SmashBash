package com.fisherevans.wipgame.input;

import com.fisherevans.wipgame.resources.Inputs;
import org.newdawn.slick.KeyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class Controller {
    private int _sourceId;
    private String _helpImageKey;
    private Map<Key, Boolean> _keyStates;

    public Controller(int sourceId, String helpImageKey) {
        _sourceId = sourceId;
        _helpImageKey = helpImageKey;
        _keyStates = new HashMap<>();
    }

    public int getSourceId() {
        return _sourceId;
    }

    public String getHelpImageKey() {
        return _helpImageKey;
    }

    public boolean keyState(Key key) {
        Boolean state = _keyStates.get(key);
        if(state == null) {
            _keyStates.put(key, false);
            state = false;
        }
        return state;
    }

    public void sendKeyState(Key key, boolean state) {
        if(keyState(key) != state) {
            _keyStates.put(key, state);
            Inputs.inputEvent(key, state, getSourceId());
        }
    }
}
