package com.fisherevans.wipgame.input;

import com.fisherevans.wipgame.Log;
import com.fisherevans.wipgame.Main;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Inputs implements KeyListener {
    private static Map<Integer, Map<Key, Integer>> _inputsMap = null;

    private static Inputs _itself = null;

    private static InputsListener _listener = null;

    public static void load() {
        _itself = new Inputs();

        _inputsMap = new HashMap<>();

        Map<Key, Integer> c0 = new HashMap<>();
        c0.put(Key.Up, Input.KEY_W);
        c0.put(Key.Down, Input.KEY_S);
        c0.put(Key.Left, Input.KEY_A);
        c0.put(Key.Right, Input.KEY_D);
        c0.put(Key.Select, Input.KEY_SPACE);
        c0.put(Key.Back, Input.KEY_LCONTROL);
        c0.put(Key.Menu, Input.KEY_ESCAPE);
        _inputsMap.put(0, c0);

        Map<Key, Integer> c1 = new HashMap<>();
        c1.put(Key.Up, Input.KEY_UP);
        c1.put(Key.Down, Input.KEY_DOWN);
        c1.put(Key.Left, Input.KEY_LEFT);
        c1.put(Key.Right, Input.KEY_RIGHT);
        c1.put(Key.Select, Input.KEY_ENTER);
        c1.put(Key.Back, Input.KEY_RCONTROL);
        c1.put(Key.Menu, Input.KEY_BACK);
        _inputsMap.put(1, c1);

        Main.gameContainer.getInput().addKeyListener(_itself);
    }

    public static void setListener(InputsListener listener) {
        _listener = listener;
    }

    public static InputsListener getListener() {
        return _listener;
    }

    @Override
    public void keyPressed(int key, char c) {
        if(_listener == null)
            return;

        Map<Key, Integer> keyMap;
        for(Key keyType:Key.values()) {
            for(Integer inputSourceKey:_inputsMap.keySet()) {
                keyMap = _inputsMap.get(inputSourceKey);
                if(key == keyMap.get(keyType)) {
                    _listener.keyDown(keyType, inputSourceKey);
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if(_listener == null)
            return;

        Map<Key, Integer> keyMap;
        for(Key keyType:Key.values()) {
            for(Integer inputSourceKey:_inputsMap.keySet()) {
                keyMap = _inputsMap.get(inputSourceKey);
                if(key == keyMap.get(keyType)) {
                    _listener.keyUp(keyType, inputSourceKey);
                    break;
                }
            }
        }
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void setInput(Input input) { }

    @Override
    public void inputEnded() { }

    @Override
    public void inputStarted() { }
}
