package com.fisherevans.wipgame.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class KeyboardController extends InputController {
    private Map<Key, Integer> _keyMap = new HashMap<Key, Integer>();

    public KeyboardController(int sourceId, String helpImageKey) {
        super(sourceId, helpImageKey);
    }

    public void setKeyCode(Key key, Integer keyCode) {
        _keyMap.put(key, keyCode);
    }

    public Integer getKeyCode(Key key) {
        return _keyMap.get(key);
    }

    public void keyPressed(int keyCode) {
        for(Key key:_keyMap.keySet()) {
            if(keyCode == _keyMap.get(key)) {
                sendKeyState(key, true);
                break;
            }
        }
    }

    public void keyReleased(int keyCode) {
        for(Key key:_keyMap.keySet()) {
            if(keyCode == _keyMap.get(key)) {
                sendKeyState(key, false);
                break;
            }
        }
    }
}
