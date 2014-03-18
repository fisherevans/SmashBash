package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.input.*;
import com.fisherevans.wipgame.log.Log;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import org.newdawn.slick.Input;

import java.util.*;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Inputs {
    public static final int GLOBAL_INPUT = -1;

    public static final Log log = new Log(Inputs.class);
    public static Map<Integer, InputController> controllers = new HashMap<Integer, InputController>();
    public static KeyboardListener keyboardListener;

    public static void load() {
        KeyboardController kg = new KeyboardController(GLOBAL_INPUT, "gui/inputs/help");
        kg.setKeyCode(Key.Menu, Input.KEY_ESCAPE);
        kg.setName("Global");
        controllers.put(kg.getSourceId(), kg);

        int sourceId = 1;

        KeyboardController k1 = new KeyboardController(sourceId++, "gui/inputs/keyboard-1");
        k1.setKeyCode(Key.Up, Input.KEY_W);
        k1.setKeyCode(Key.Down, Input.KEY_S);
        k1.setKeyCode(Key.Left, Input.KEY_A);
        k1.setKeyCode(Key.Right, Input.KEY_D);
        k1.setKeyCode(Key.Select, Input.KEY_F);
        k1.setKeyCode(Key.Back, Input.KEY_C);
        controllers.put(k1.getSourceId(), k1);

        KeyboardController k2 = new KeyboardController(sourceId++, "gui/inputs/keyboard-2");
        k2.setKeyCode(Key.Up, Input.KEY_P);
        k2.setKeyCode(Key.Down, Input.KEY_SEMICOLON);
        k2.setKeyCode(Key.Left, Input.KEY_L);
        k2.setKeyCode(Key.Right, Input.KEY_APOSTROPHE);
        k2.setKeyCode(Key.Select, Input.KEY_ENTER);
        k2.setKeyCode(Key.Back, Input.KEY_RSHIFT);
        controllers.put(k2.getSourceId(), k2);

        for(int i = 0; i < JXInputManager.getNumberOfDevices(); i++){
            if(JXInputManager.getJXInputDevice(i).getName().toLowerCase().contains("xbox")){
                XBoxController xBox = new XBoxController(new DirectInputDevice(i), sourceId++);
                controllers.put(xBox.getSourceId(), xBox);
            }
        }

        keyboardListener = new KeyboardListener();
        WIP.container.getInput().addKeyListener(keyboardListener);
    }

    public static void queryXBoxControllers() {
        for(InputController controller:controllers.values())
            if(controller instanceof XBoxController)
                ((XBoxController)controller).query();
    }

    public static void keyPressed(int keyCode) {
        for(InputController controller:controllers.values())
            if(controller instanceof KeyboardController)
                ((KeyboardController)controller).keyPressed(keyCode);
    }

    public static void keyReleased(int keyCode) {
        for(InputController controller:controllers.values())
            if(controller instanceof KeyboardController)
                ((KeyboardController)controller).keyReleased(keyCode);
    }

    public static void inputEvent(Key key, boolean state, int sourceId) {
        if(WIP.game.getCurrentState() instanceof InputsListener) {
            InputsListener listener = (InputsListener) WIP.game.getCurrentState();
            if(state)
                listener.keyDown(key, sourceId);
            else
                listener.keyUp(key, sourceId);
        }
    }
}
