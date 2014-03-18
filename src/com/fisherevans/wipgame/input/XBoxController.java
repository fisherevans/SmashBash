package com.fisherevans.wipgame.input;

import com.fisherevans.wipgame.log.Log;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/19/14
 */
public class XBoxController extends InputController implements JXInputDirectionalEventListener {
    private static final Log log = new Log(XBoxController.class);

    private static final int BUTTON_A = 0;
    private static final int BUTTON_B = 1;
    private static final int BUTTON_X = 2;
    private static final int BUTTON_Y = 3;
    private static final int BUTTON_LT = 4;
    private static final int BUTTON_RT = 5;
    private static final int BUTTON_SELECT = 6;
    private static final int BUTTON_START = 7;
    private static final int BUTTON_LA = 8;
    private static final int BUTTON_RA = 9;

    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_RIGHT = 9000;
    private static final int DIRECTION_DOWN = 18000;
    private static final int DIRECTION_LEFT = 27000;

    private static final double AXIS_THRESHOLD = 0.2;
    private static final double TRIGGER_THRESHOLD = 0.1;

    private static final int AXIS_LEFT_LR = 0;
    private static final int AXIS_LEFT_UD = 1;

    private static final int AXIS_RIGHT_LR = 3;
    private static final int AXIS_RIGHT_UD = 4;

    private static final int AXIS_TRIGGER = 2;

    public static boolean useUDAnalog = true;

    private DirectInputDevice _controller;

    private int _lastDirectional = -1;

    private Map<Axis, Integer> _axisMap;
    private Map<Button, String> _buttonMap;

    private Map<Axis, Double> _axisLastState;
    private Map<Button, Boolean> _buttonLastState;

    public XBoxController(DirectInputDevice controller, int sourceId) {
        super(sourceId, "gui/inputs/xbox");
        _controller = controller;

        log.info("New XBoxController on Input Source " + getSourceId());

        for(int directionalId = 0;directionalId < _controller.getNumberOfDirectionals();directionalId++)
            JXInputEventManager.addListener(this, _controller.getDirectional(directionalId));

        _buttonMap = new HashMap<Button, String>();
        _buttonLastState = new HashMap<Button, Boolean>();
        Button button;
        for(int buttonId = 0;buttonId < _controller.getNumberOfButtons();buttonId++) {
            button = _controller.getButton(buttonId);
            _buttonMap.put(button, button.getName());
            _buttonLastState.put(button, false);
        }

        _axisMap = new HashMap<Axis, Integer>();
        _axisLastState = new HashMap<Axis, Double>();
        Axis axis;
        for(int axisId = 0;axisId < _controller.getNumberOfAxes();axisId++) {
            axis = _controller.getAxis(axisId);
            _axisMap.put(axis, axisId);
            _axisLastState.put(axis, 0d);
        }
    }

    @Override
    public void changed(JXInputDirectionalEvent jxInputDirectionalEvent) {
        Directional directional = jxInputDirectionalEvent.getDirectional();

        Key last = getDirectionalKey(_lastDirectional);
        Key current = getDirectionalKey(directional.getDirection());

        if(last != null)
            sendKeyState(last, false);

        if(current != null && directional.getValue() != 0)
            sendKeyState(current, true);

        _lastDirectional = directional.getValue() == 0 ? -1 : directional.getDirection();
        //System.out.println("Directional Event [Name=" + directional.getName() + "] " +
        //        "[Value=" + directional.getValue() + "] " +
        //        "[Direction=" + directional.getDirection() + "] " +
        //        "[Resolution=" + directional.getResolution() + "]");
    }

    private Key getDirectionalKey(int direction) {
        switch(direction) {
            case DIRECTION_UP:
                return Key.Up;
            case DIRECTION_RIGHT:
                return Key.Right;
            case DIRECTION_DOWN:
                return Key.Down;
            case DIRECTION_LEFT:
                return Key.Left;
        }
        return null;
    }

    public void query() {
        queryAxes();
        queryButtons();
    }

    private void queryButtons() {
        String buttonId;
        for(Button button:_buttonMap.keySet()) {
            buttonId = _buttonMap.get(button);
            boolean state = button.getState();
            if(state != _buttonLastState.get(button)) {
                _buttonLastState.put(button, state);
                switch(Integer.parseInt(buttonId.substring(buttonId.length()-1))) {
                    case BUTTON_A:
                        sendKeyState(Key.Select, state);
                        continue;
                    case BUTTON_B:
                        sendKeyState(Key.Back, state);
                        continue;
                    case BUTTON_START:
                        sendKeyState(Key.Menu, state);
                        continue;
                    case BUTTON_RT:
                        sendKeyState(Key.Select, state);
                        continue;
                    case BUTTON_LT:
                        sendKeyState(Key.Back, state);
                        continue;
                }
            }
        }
    }

    private void queryAxes() {
        Integer axisId;
        double value, lastValue;
        for(Axis axis:_axisMap.keySet()) {
            axisId = _axisMap.get(axis);
            Key negKey, posKey;
            switch(axisId) {
                case AXIS_TRIGGER:
                    negKey = Key.Up;
                    posKey = Key.Down;
                    break;
                case AXIS_LEFT_LR:
                    negKey = Key.Left;
                    posKey = Key.Right;
                    break;
                case AXIS_LEFT_UD:
                    if(!useUDAnalog)
                        continue;
                    negKey = Key.Up;
                    posKey = Key.Down;
                    break;
                default:
                    continue;
            }
            value = axis.getValue();
            lastValue = _axisLastState.get(axis);
            _axisLastState.put(axis, value);

            if(value >= AXIS_THRESHOLD && lastValue < AXIS_THRESHOLD)
                sendKeyState(posKey, true);
            else if(lastValue >= AXIS_THRESHOLD && value < AXIS_THRESHOLD)
                sendKeyState(posKey, false);

            if(value <= -AXIS_THRESHOLD && lastValue > -AXIS_THRESHOLD)
                sendKeyState(negKey, true);
            else if(lastValue <= -AXIS_THRESHOLD && value > -AXIS_THRESHOLD)
                sendKeyState(negKey, false);
        }
    }
}
