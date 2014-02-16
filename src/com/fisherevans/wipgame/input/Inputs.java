package com.fisherevans.wipgame.input;

import com.fisherevans.wipgame.Main;
import com.fisherevans.wipgame.game.WIP;
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
        _inputsMap.put(0, c0);

        Map<Key, Integer> c1 = new HashMap<>();
        c1.put(Key.Up, Input.KEY_UP);
        c1.put(Key.Down, Input.KEY_DOWN);
        c1.put(Key.Left, Input.KEY_LEFT);
        c1.put(Key.Right, Input.KEY_RIGHT);
        c1.put(Key.Select, Input.KEY_ENTER);
        c1.put(Key.Back, Input.KEY_RCONTROL);
        _inputsMap.put(1, c1);

        Map<Key, Integer> cz = new HashMap<>();
        cz.put(Key.Menu, Input.KEY_ESCAPE);
        _inputsMap.put(-1, cz);

        WIP.container.getInput().addKeyListener(_itself);
    }

    public static void setListener(InputsListener listener) {
        _listener = listener;
    }

    public static InputsListener getListener() {
        return _listener;
    }

    @Override
    public void keyPressed(int keyCode, char c) {
        if(keyCode == Input.KEY_F8)
            WIP.debug = !WIP.debug;
        if(_listener == null)
            return;
        for(int inputSource:_inputsMap.keySet()) {
            for(Key key:_inputsMap.get(inputSource).keySet()) {
                if(keyCode == _inputsMap.get(inputSource).get(key)) {
                    _listener.keyDown(key, inputSource);
                }
            }
        }
    }

    @Override
    public void keyReleased(int keyCode, char c) {
        if(_listener == null)
            return;
        for(int inputSource:_inputsMap.keySet()) {
            for(Key key:_inputsMap.get(inputSource).keySet()) {
                if(keyCode == _inputsMap.get(inputSource).get(key)) {
                    _listener.keyUp(key, inputSource);
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
