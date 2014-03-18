package com.fisherevans.smash_bash.input;

import com.fisherevans.smash_bash.game.TypingState;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.log.Log;
import com.fisherevans.smash_bash.resources.Inputs;
import com.fisherevans.smash_bash.resources.Settings;
import com.fisherevans.smash_bash.tools.InputFunctions;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class KeyboardListener implements KeyListener {
    public static final Log log = new Log(KeyboardListener.class);

    private boolean shift = false;
    private Map<Integer, KeyDownAction> keyDownActions = new HashMap<Integer, KeyDownAction>();
    private Map<Integer, KeyUpAction> keyUpActions = new HashMap<Integer, KeyUpAction>();
    private String alphabet;

    public KeyboardListener() {
        // Key Down Actions
        keyDownActions.put(Input.KEY_F8, new KeyDownAction() {
            @Override
            public void perform(int keyCode) {
                SmashBash.debug = !SmashBash.debug;
            }
        });
        keyDownActions.put(Input.KEY_F10, new KeyDownAction() {
            @Override
            public void perform(int keyCode) {
                SmashBash.saveScreenShot();
            }
        });
        keyDownActions.put(Input.KEY_LSHIFT, new KeyDownAction() {
            @Override
            public void perform(int keyCode) {
                shift = true;
            }
        });
        keyDownActions.put(Input.KEY_RSHIFT, new KeyDownAction() {
            @Override
            public void perform(int keyCode) {
                shift = true;
            }
        });
        for(Integer keyCode: SmashBash.overlayStates.keySet())
            keyDownActions.put(keyCode, new KeyDownAction() {
                @Override
                public void perform(int keyCode) {
                    SmashBash.toggleOverlay(keyCode);
                }
            });

        // Key Up Actions
        keyUpActions.put(Input.KEY_LSHIFT, new KeyUpAction() {
            @Override
            public void perform(int keyCode) {
                shift = false;
            }
        });
        keyUpActions.put(Input.KEY_RSHIFT, new KeyUpAction() {
            @Override
            public void perform(int keyCode) {
                shift = false;
            }
        });

        alphabet = Settings.getString("keyboardListener.alphabet");
    }

    @Override
    public void keyPressed(int keyCode, char c) {
        KeyDownAction keyDownAction = keyDownActions.get(keyCode);
        if(keyDownAction != null)
            keyDownAction.perform(keyCode);

        if(SmashBash.currentState() instanceof TypingState) {
            TypingState typingState = (TypingState) SmashBash.currentState();
            switch(keyCode) {
                case Input.KEY_ENTER: typingState.keyEnter(); break;
                case Input.KEY_BACK: typingState.keyBackspace(); break;
                case Input.KEY_DELETE: typingState.keyDelete(); break;
                case Input.KEY_LEFT: typingState.keyArrowLeft(); break;
                case Input.KEY_RIGHT: typingState.keyArrowRight(); break;
                case Input.KEY_UP: typingState.keyArrowUp(); break;
                case Input.KEY_DOWN: typingState.keyArrowDown(); break;
                default:
                    String input = InputFunctions.getCharacter(keyCode, shift);
                    if(alphabet.contains(input))
                        typingState.typed(input);
            }
        }

        Inputs.keyPressed(keyCode);
    }

    @Override
    public void keyReleased(int keyCode, char c) {
        KeyUpAction keyUpAction = keyUpActions.get(keyCode);
        if(keyUpAction != null)
            keyUpAction.perform(keyCode);

        Inputs.keyReleased(keyCode);
    }

    @Override
    public void setInput(Input input) { }

    @Override
    public boolean isAcceptingInput() { return true; }

    @Override
    public void inputEnded() { }

    @Override
    public void inputStarted() { }

    private interface KeyDownAction {
        public abstract void perform(int keyCode);
    }

    private interface KeyUpAction {
        public abstract void perform(int keyCode);
    }
}
