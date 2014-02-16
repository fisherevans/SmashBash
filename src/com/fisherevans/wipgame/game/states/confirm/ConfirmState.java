package com.fisherevans.wipgame.game.states.confirm;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class ConfirmState extends WIPState {
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final int PADDING = Config.SIZES[2];

    private GameState _otherState;
    private Runnable _action;
    private StateBasedGame _stateBasedGame;
    private String _message;
    private boolean _confirm = false;

    public ConfirmState(GameState otherState, String message, Runnable action) {
        _otherState = otherState;
        _message = message;
        _action = action;
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
    public void render(GameContainer container, StateBasedGame game, Graphics gfx) throws SlickException {
        _otherState.render(container, game, gfx);

        AngelCodeFont font = Fonts.getFont(Fonts.SMALL);
        float messageWidth = font.getWidth(_message);
        float messageHeight = font.getLineHeight();

        //float yesWidth = font.getWidth(YES);
        float yesHeight = font.getHeight(YES);

        float noWidth = font.getWidth(NO);
        //float noHeight = font.getHeight(NO);

        float boxWidth = messageWidth + PADDING*2;
        float boxHeight = messageHeight + yesHeight + PADDING*3;

        float halfWidth = container.getWidth()/2f;
        float halfHeight = container.getHeight()/2f;

        gfx.setColor(new Color(0f, 0f, 0f));
        gfx.fillRect(halfWidth-boxWidth/2f, halfHeight-boxHeight/2f, boxWidth, boxHeight);


        gfx.setColor(Color.white);
        gfx.setFont(font);

        gfx.drawString(_message, halfWidth-boxWidth/2f + PADDING, halfHeight-boxHeight/2f + PADDING);

        gfx.setColor(_confirm ? Color.lightGray : new Color(0.2f, 0.4f, 0.9f));
        gfx.drawString(NO, halfWidth - boxWidth / 2f + PADDING, halfHeight - boxHeight / 2f + messageHeight + PADDING * 2);

        gfx.setColor(!_confirm ? Color.lightGray : new Color(0.2f, 0.4f, 0.9f));
        gfx.drawString(YES, halfWidth-boxWidth/2f + messageWidth - noWidth + PADDING, halfHeight-boxHeight/2f + messageHeight + PADDING*2);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

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
}
