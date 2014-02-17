package com.fisherevans.wipgame.game.states.confirm;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.util.color.ColorInterpolation;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class ConfirmState extends WIPState {
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final int PADDING = Config.SIZES[1];
    private static final float ALPHA_SPEED = 6f;

    private float _alphaScale = 0f;

    private WIPState _otherState;
    private Runnable _action;
    private StateBasedGame _stateBasedGame;
    private String _message;
    private boolean _confirm = false;
    private AngelCodeFont _font;
    private Color _fillColor, _backgroundColor, foregroundColor;
    private ColorInterpolation _selectionColor;

    public ConfirmState(WIPState otherState, String message, Runnable action) {
        _otherState = otherState;
        _message = message;
        _action = action;

        _font = Fonts.getStrokedFont(Fonts.SMALL);

        _fillColor = new Color(0f, 0f, 0f, 0.25f);
        _backgroundColor = new Color(0f, 0f, 0f, 0.75f);
        foregroundColor = new Color(1f, 1f, 1f);

        _selectionColor = new ColorInterpolation(Config.HIGHLIGHT, new Color(0.6f, 0.6f, 0.6f), 6f);
    }

    @Override
    public int getID() {
        return WIP.STATE_CONFIRM;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        _stateBasedGame = game;
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        _otherState.render(graphics);

        graphics.setColor(_fillColor);
        graphics.fillRect(0, 0, WIP.width(), WIP.height());

        float messageWidth = _font.getWidth(_message);

        float noWidth = _font.getWidth(NO);

        float boxWidth = messageWidth + PADDING*2;
        float boxHeight = _font.getLineHeight()*2 + PADDING*3;

        float halfWidth = WIP.width()/2f;
        float halfHeight = WIP.height()/2f;

        graphics.setColor(_backgroundColor);
        graphics.fillRect(halfWidth-boxWidth/2f, halfHeight-boxHeight/2f, boxWidth, boxHeight);

        graphics.setColor(foregroundColor);
        graphics.setFont(_font);

        graphics.drawString(_message, halfWidth - boxWidth / 2f + PADDING, halfHeight - boxHeight / 2f + PADDING);

        graphics.setColor(_selectionColor.getInverseColor());
        graphics.drawString(NO, halfWidth - boxWidth / 2f + PADDING, halfHeight - boxHeight / 2f + _font.getLineHeight() + PADDING * 2);

        graphics.setColor(_selectionColor.getColor());
        graphics.drawString(YES, halfWidth-boxWidth/2f + messageWidth - noWidth + PADDING, halfHeight-boxHeight/2f + _font.getLineHeight() + PADDING*2);
    }

    @Override
    public void update(float delta) {
        _selectionColor.update(_confirm ? delta : -delta);
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Right)
            _confirm = true;
        else if (key == Key.Left)
            _confirm = false;
        else if(key == Key.Select) {
            if(_confirm)
                _action.run();
            else
                _stateBasedGame.enterState(_otherState.getID());
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    public static void enter(WIPState otherState, String message, Runnable action) {
        ConfirmState confirmState = new ConfirmState(otherState, message, action);
        confirmState.init();
        WIP.enterNewState(confirmState);
    }
}
