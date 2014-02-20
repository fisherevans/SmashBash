package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.input.XBoxControllerListener;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.JXInputEventManager;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Inputs implements KeyListener {
    public static final int GLOBAL_INPUT = -1;

    private static Map<Integer, Map<Key, Integer>> _inputsMap = null;
    private static Map<Integer, Map<Key, Boolean>> _keyStateMap = null;

    public static Inputs itself = null;

    private static InputsListener _listener = null;

    public static void load() {
        itself = new Inputs();

        _inputsMap = new HashMap<>();

        Map<Key, Integer> c1 = new HashMap<>();
        c1.put(Key.Up, Input.KEY_W);
        c1.put(Key.Down, Input.KEY_S);
        c1.put(Key.Left, Input.KEY_A);
        c1.put(Key.Right, Input.KEY_D);
        c1.put(Key.Select, Input.KEY_SPACE);
        c1.put(Key.Back, Input.KEY_LCONTROL);
        _inputsMap.put(1, c1);

        Map<Key, Integer> c2 = new HashMap<>();
        c2.put(Key.Up, Input.KEY_UP);
        c2.put(Key.Down, Input.KEY_DOWN);
        c2.put(Key.Left, Input.KEY_LEFT);
        c2.put(Key.Right, Input.KEY_RIGHT);
        c2.put(Key.Select, Input.KEY_ENTER);
        c2.put(Key.Back, Input.KEY_RCONTROL);
        _inputsMap.put(2, c2);

        int nextInput = _inputsMap.keySet().size()+1;
        for(int i = 0; i < JXInputManager.getNumberOfDevices(); i++){
            System.out.println("Found controller: " + JXInputManager.getJXInputDevice(i).getName());
            if(JXInputManager.getJXInputDevice(i).getName().toLowerCase().contains("xbox")){
                new XBoxControllerListener(new DirectInputDevice(i), nextInput);
                _inputsMap.put(nextInput++, new HashMap<Key, Integer>());
            }
        }

        Map<Key, Integer> cg = new HashMap<>();
        cg.put(Key.Menu, Input.KEY_ESCAPE);
        _inputsMap.put(GLOBAL_INPUT, cg);

        _keyStateMap = new HashMap<>();
        Map<Key, Boolean> keyState;
        for(Integer inputSource:_inputsMap.keySet()) {
            keyState = new HashMap<>();
            for(Key key:Key.values()) {
                keyState.put(key, false);
            }
            _keyStateMap.put(inputSource, keyState);
        }

        WIP.container.getInput().addKeyListener(itself);
    }

    public static void setListener(InputsListener listener) {
        _listener = listener;
    }

    public static InputsListener getListener() {
        return _listener;
    }

    public static void keyEvent(Key key, int source, boolean state) {
        _keyStateMap.get(source).put(key, state);
        if(state)
            _listener.keyDown(key, source);
        else
            _listener.keyUp(key, source);
    }

    @Override
    public void keyPressed(int keyCode, char c) {
        if(keyCode == Input.KEY_F8) {
            WIP.debug = !WIP.debug;
        }
        if(_listener == null)
            return;
        for(int inputSource:_inputsMap.keySet()) {
            for(Key key:_inputsMap.get(inputSource).keySet()) {
                if(keyCode == _inputsMap.get(inputSource).get(key)) {
                    keyEvent(key, inputSource, true);
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
                    keyEvent(key, inputSource, false);
                }
            }
        }
    }

    public static boolean keyState(Key key, int inputSource) {
        Map<Key, Boolean> map = _keyStateMap.get(inputSource);
        if(map == null)
            return false;
        else
            return map.get(key);
    }

    public static boolean globalKeyState(Key key) {
        for(Integer inputSource:_keyStateMap.keySet()) {
            if(_keyStateMap.get(inputSource).get(key))
                return true;
        }
        return false;
    }

    public static Set<Integer> getInputSources() {
        return _inputsMap.keySet();
    }

    public static String getPhysicalKey(Key key, Integer inputSource) {
        Map<Key, Integer> map = _inputsMap.get(inputSource);
        if(map == null)
            return null;
        Integer keyValue = map.get(key);
        if(keyValue == null)
            return null;
        return Input.getKeyName(keyValue);
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
