package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.command.CommandState;
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
import org.newdawn.slick.state.GameState;

import java.util.*;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Inputs implements KeyListener {
    public static final int GLOBAL_INPUT = -1;
    public static final String alphebet = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+-=[]\\;',./{}|:\"<>?`~ ";

    private static Map<Integer, Map<Key, Integer>> _inputsMap = null;
    private static Map<Integer, Map<Key, Boolean>> _keyStateMap = null;

    public static Inputs itself = null;

    public boolean shift = false;

    private static List<XBoxControllerListener> _xboxControllers = new LinkedList<>();

    public static void load() {
        itself = new Inputs();

        _inputsMap = new HashMap<>();

        Map<Key, Integer> c1 = new HashMap<>();
        c1.put(Key.Up, Input.KEY_W);
        c1.put(Key.Down, Input.KEY_S);
        c1.put(Key.Left, Input.KEY_A);
        c1.put(Key.Right, Input.KEY_D);
        c1.put(Key.Select, Input.KEY_F);
        c1.put(Key.Back, Input.KEY_C);
        _inputsMap.put(1, c1);

        Map<Key, Integer> c2 = new HashMap<>();
        c2.put(Key.Up, Input.KEY_P);
        c2.put(Key.Down, Input.KEY_SEMICOLON);
        c2.put(Key.Left, Input.KEY_L);
        c2.put(Key.Right, Input.KEY_APOSTROPHE);
        c2.put(Key.Select, Input.KEY_ENTER);
        c2.put(Key.Back, Input.KEY_RSHIFT);
        _inputsMap.put(2, c2);

        int nextInput = _inputsMap.keySet().size()+1;
        for(int i = 0; i < JXInputManager.getNumberOfDevices(); i++){
            System.out.println("Found controller: " + JXInputManager.getJXInputDevice(i).getName());
            if(JXInputManager.getJXInputDevice(i).getName().toLowerCase().contains("xbox")){
                _xboxControllers.add(new XBoxControllerListener(new DirectInputDevice(i), nextInput));
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

    public static void queryControllers() {
        for(XBoxControllerListener controller:_xboxControllers) {
            controller.query();
        }
    }

    public static void keyEvent(Key key, int source, boolean state) {
        _keyStateMap.get(source).put(key, state);
        if(WIP.game.getCurrentState() instanceof InputsListener) {
            InputsListener listener = (InputsListener) WIP.game.getCurrentState();
            if(state)
                listener.keyDown(key, source);
            else
                listener.keyUp(key, source);
        }
    }

    @Override
    public void keyPressed(int keyCode, char c) {
        if(keyCode == Input.KEY_LSHIFT || keyCode == Input.KEY_RSHIFT)
            shift = true;
        else if(keyCode == Input.KEY_F8)
            WIP.debug = !WIP.debug;
        else if(keyCode == Input.KEY_F10) {
            try {
                String filename = WIP.saveScreenShot("");
                WIP.log.info("Screenshot saved as: " + filename);
            } catch(Exception e) {
                WIP.log.error("Failed to save screenshot!");
                WIP.log.error(e.toString());
            }
        } else if(keyCode == Input.KEY_F12) {
            GameState currentState = WIP.game.getCurrentState();
            if(currentState instanceof CommandState)
                WIP.enterState(((CommandState)currentState).getCurrentState());
            else
                WIP.enterNewState(new CommandState((WIPState)currentState));
        }

        if(CommandState.acceptInput) {
            String input = getCharacter(keyCode, shift);
            if(keyCode == Input.KEY_ENTER)
                CommandState.ketEnter();
            else if(keyCode == Input.KEY_BACK)
                CommandState.keyBack();
            else if(keyCode == Input.KEY_LEFT)
                CommandState.keyLeft();
            else if(keyCode == Input.KEY_RIGHT)
                CommandState.keyRight();
            else if(keyCode == Input.KEY_UP)
                CommandState.keyUpArrow();
            else if(keyCode == Input.KEY_DOWN)
                CommandState.keyDownArrow();
            else if(keyCode == Input.KEY_DELETE)
                CommandState.keyDelete();
            else if(alphebet.contains(input))
                CommandState.textInput(shift ? input.toUpperCase() : input);

            //System.out.println(keyCode + " " + Input.getKeyName(keyCode));
        }

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
        if(keyCode == Input.KEY_LSHIFT || keyCode == Input.KEY_RSHIFT)
            shift = false;
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

    public static String getCharacter(int keyCode, boolean isShift) {
        switch(keyCode) {
            case Input.KEY_A: return "a";
            case Input.KEY_B: return "b";
            case Input.KEY_C: return "c";
            case Input.KEY_D: return "d";
            case Input.KEY_E: return "e";
            case Input.KEY_F: return "f";
            case Input.KEY_G: return "g";
            case Input.KEY_H: return "h";
            case Input.KEY_I: return "i";
            case Input.KEY_J: return "j";
            case Input.KEY_K: return "k";
            case Input.KEY_L: return "l";
            case Input.KEY_M: return "m";
            case Input.KEY_N: return "n";
            case Input.KEY_O: return "o";
            case Input.KEY_P: return "p";
            case Input.KEY_Q: return "q";
            case Input.KEY_R: return "r";
            case Input.KEY_S: return "s";
            case Input.KEY_T: return "t";
            case Input.KEY_U: return "u";
            case Input.KEY_V: return "v";
            case Input.KEY_W: return "w";
            case Input.KEY_X: return "x";
            case Input.KEY_Y: return "y";
            case Input.KEY_Z: return "z";
            case Input.KEY_0: return isShift ? ")" : "0";
            case Input.KEY_1: return isShift ? "!" : "1";
            case Input.KEY_2: return isShift ? "@" : "2";
            case Input.KEY_3: return isShift ? "#" : "3";
            case Input.KEY_4: return isShift ? "$" : "4";
            case Input.KEY_5: return isShift ? "%" : "5";
            case Input.KEY_6: return isShift ? "^" : "6";
            case Input.KEY_7: return isShift ? "&" : "7";
            case Input.KEY_8: return isShift ? "*" : "8";
            case Input.KEY_9: return isShift ? "(" : "9";
            case Input.KEY_SPACE: return " ";
            case Input.KEY_COMMA: return !isShift ? "," : "<";
            case Input.KEY_PERIOD: return !isShift ? "." : ">";
            case Input.KEY_SLASH: return !isShift ? "/" : "?";
            case Input.KEY_SEMICOLON: return !isShift ? ";" : ":";
            case Input.KEY_APOSTROPHE: return !isShift ? "'" : "\"";
            case Input.KEY_LBRACKET: return !isShift ? "[" : "{";
            case Input.KEY_RBRACKET: return !isShift ? "]" : "}";
            case Input.KEY_BACKSLASH: return !isShift ? "\\" : "|";
            case Input.KEY_MINUS: return !isShift ? "-" : "_";
            case Input.KEY_EQUALS: return !isShift ? "=" : "+";
            case Input.KEY_GRAVE: return !isShift ? "`" : "~";
            default: return "unknown";
        }
    }
}
