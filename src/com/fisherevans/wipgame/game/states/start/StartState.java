package com.fisherevans.wipgame.game.states.start;

import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.GameSettings;
import com.fisherevans.wipgame.game.states.start.settings.*;
import com.fisherevans.wipgame.input.Inputs;
import com.fisherevans.wipgame.input.InputsListener;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class StartState extends WIPState {
    private List<Setting> _settings;

    private int _selected = 0;

    private AngelCodeFont _font;

    private int _fontHeight;

    @Override
    public int getID() {
        return WIP.STATE_START;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _settings = new ArrayList<>();

        _settings.add(new PlaySetting("Play WIP", this, stateBasedGame));
        _settings.add(new StringSetting("Map", new String[] {"Dusty Level", "Lava Lake", "Gateway"}, 0));
        _settings.add(new NumberSetting("Player Count", 1, 1, 1));
        _settings.add(new NumberSetting("AI Count", 0, 0, 8));
        _settings.add(new StringSetting("Time Limit", new String[] {"2 Minutes", "5 Minutes", "10 Minutes", "Infinite"}, 0));
        _settings.add(new NumberSetting("Lives", 1, 10, 30));
        _settings.add(new BooleanSetting("Random Weapons", false, "Enabled", "Disabled"));

        _font = Fonts.getFont(Fonts.REGULAR);
        _fontHeight = _font.getHeight("|");
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setAntiAlias(true);
        graphics.setFont(_font);
        for(int i = 0;i < _settings.size();i++) {
            if(_selected == i)
                graphics.setColor(Color.white);
            else
                graphics.setColor(Color.darkGray);

            float xShift = _font.getWidth(_settings.get(i).getName());
            float yShift = _font.getHeight(_settings.get(i).getName())/2f - (i-_selected)*_fontHeight*2.5f - _font.getHeight(_settings.get(i).getName())/2f;
            graphics.drawString(_settings.get(i).getName(), gameContainer.getWidth()/2f - 10 - xShift, gameContainer.getHeight()/2f - yShift);
            graphics.drawString(_settings.get(i).getSelected(), gameContainer.getWidth()/2f + 10, gameContainer.getHeight()/2f - yShift);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    public GameSettings getGameSettings() {
        return new GameSettings();
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        switch(key) {
            case Up: {
                _selected--;
                if(_selected < 0)
                    _selected = 0;
                break;
            }
            case Down: {
                _selected++;
                if(_selected >= _settings.size()-1)
                    _selected = _settings.size()-1;
                break;
            }
            case Left: {
                _settings.get(_selected).previous();
                break;
            }
            case Right: {
                _settings.get(_selected).next();
                break;
            }
            case Select: {
                _settings.get(_selected).select();
                break;
            }
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
