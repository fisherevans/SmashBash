package com.fisherevans.wipgame.input;

import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Inputs;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.*;

/**
 * Author: Fisher Evans
 * Date: 2/19/14
 */
public class XBoxControllerListener implements JXInputAxisEventListener, JXInputButtonEventListener, JXInputDirectionalEventListener {
    private static final Log log = new Log(XBoxControllerListener.class);

    private static final String BUTTON_A = "Button 0";
    private static final String BUTTON_B = "Button 1";
    private static final String BUTTON_X = "Button 2";
    private static final String BUTTON_Y = "Button 3";
    private static final String BUTTON_LT = "Button 4";
    private static final String BUTTON_RT = "Button 5";
    private static final String BUTTON_SELECT = "Button 6";
    private static final String BUTTON_START = "Button 7";
    private static final String BUTTON_LA = "Button 8";
    private static final String BUTTON_RA = "Button 9";

    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_RIGHT = 9000;
    private static final int DIRECTION_DOWN = 18000;
    private static final int DIRECTION_LEFT = 27000;

    private DirectInputDevice _controller;
    private int _sourceId;

    public XBoxControllerListener(DirectInputDevice controller, int sourceId) {
        _controller = controller;
        _sourceId = sourceId;

        log.info("New XBoxController on Input Source " + _sourceId);
        System.out.println("New XBoxController on Input Source " + _sourceId);

        for(int buttonId = 0;buttonId < _controller.getNumberOfButtons();buttonId++)
            JXInputEventManager.addListener(this, _controller.getButton(buttonId));

        //for(int axisId = 0;axisId < _controller.getNumberOfAxes();axisId++)
        //    JXInputEventManager.addListener(this, _controller.getAxis(axisId));

        for(int directionalId = 0;directionalId < _controller.getNumberOfDirectionals();directionalId++)
            JXInputEventManager.addListener(this, _controller.getDirectional(directionalId));
    }

    @Override
    public void changed(JXInputAxisEvent jxInputAxisEvent) {
        Axis axis = jxInputAxisEvent.getAxis();
        //System.out.println("Axis Event [Name=" + axis.getName() + "] " +
        //        "[Type=" + axis.getType() + "] " +
        //        "[Resolution=" + axis.getResolution() + "] " +
        //        "[Value=" + axis.getValue() + "]");
    }

    @Override
    public void changed(JXInputButtonEvent jxInputButtonEvent) {
        Button button = jxInputButtonEvent.getButton();
        Key key = null;
        switch(button.getName()) {
            case BUTTON_A:
                key = Key.Select;
                break;
            case BUTTON_Y:
                key = Key.Up;
                break;
            case BUTTON_B:
                key = Key.Back;
                break;
            case BUTTON_START:
                key = Key.Menu;
                break;
        }
        if(key != null) {
            Inputs.keyEvent(key, _sourceId, button.getState());
        }
        //System.out.println("Button Event [Name=" + button.getName() + "] " +
        //        "[State=" + button.getState() + "] " +
        //        "[Type=" + button.getType() + "]");
    }

    private int _lastDirectional = -1;

    @Override
    public void changed(JXInputDirectionalEvent jxInputDirectionalEvent) {
        Directional directional = jxInputDirectionalEvent.getDirectional();

        Key last = getDirectionalKey(_lastDirectional);
        Key current = getDirectionalKey(directional.getDirection());

        if(last != null)
            Inputs.keyEvent(last, _sourceId, false);

        if(current != null && directional.getValue() != 0)
            Inputs.keyEvent(current, _sourceId, true);

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
}
