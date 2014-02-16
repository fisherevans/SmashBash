package com.fisherevans.wipgame.game.states.loading;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.ready.ReadyState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.input.Inputs;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class LoadingState extends WIPState {
    private final static int FONT_SIZE = Config.SIZES[1];
    private final static int FONT_BIG_SIZE = Config.SIZES[2];
    private final static float FADE_SPEED = 0.75f;


    private AngelCodeFont _font, _fontBig;
    private String _loadingMessage;
    private String _currentlyLoading;

    private LoadState _loadState = LoadState.FADE_IN;
    private int _loadStage = 0;

    private float _fade = 0;
    private Color _color;

    @Override
    public int getID() {
        return WIP.STATE_LOADING;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _font = new AngelCodeFont(Fonts.FONT_FOLDER + "stark-" + FONT_SIZE + ".fnt", Fonts.FONT_FOLDER + "stark-" + FONT_SIZE + "_0.png");
        _fontBig = new AngelCodeFont(Fonts.FONT_FOLDER + "stark-" + FONT_BIG_SIZE + ".fnt", Fonts.FONT_FOLDER + "stark-" + FONT_BIG_SIZE + "_0.png");

        _color = new Color(0, 0, 0);

        _loadingMessage = Messages.get("loading.title");
        _currentlyLoading = Messages.get("loading.resource.images");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        float textWidth = _fontBig.getWidth(_loadingMessage);

        graphics.setColor(_color);

        graphics.setFont(_fontBig);
        graphics.drawString(_loadingMessage,
                (gameContainer.getWidth() - textWidth) / 2f,
                (gameContainer.getHeight() - _fontBig.getLineHeight()) / 2f);

        graphics.setFont(_font);
        graphics.drawString(_currentlyLoading,
                Config.SIZES[0],
                gameContainer.getHeight()-_font.getLineHeight()-Config.SIZES[0]);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        switch(_loadState) {
            case FADE_IN: {
                _fade += delta/1000f/FADE_SPEED;
                if(_fade >= 1f) {
                    _fade = 1f;
                    _loadState = LoadState.LOAD;
                }
                _color = new Color(_fade, _fade, _fade);
                break;
            }
            case LOAD: {
                try {
                    switch(_loadStage) {
                        case 0: _currentlyLoading = Messages.get("loading.resource.images"); break;
                        case 1: Images.load(); break;
                        case 2: _currentlyLoading = Messages.get("loading.resource.fonts"); break;
                        case 3: Fonts.load(); break;
                        case 4: _currentlyLoading = Messages.get("loading.resource.sprites"); break;
                        case 5: Sprites.load(); break;
                        case 6: _currentlyLoading = Messages.get("loading.resource.lights"); break;
                        case 7: Lights.load(); break;
                        case 8: _currentlyLoading = Messages.get("loading.resource.maps"); break;
                        case 9: Maps.load(); break;
                        case 10: _currentlyLoading = Messages.get("loading.resource.input"); break;
                        case 11: Inputs.load(); break;
                        case 12: _currentlyLoading = Messages.get("loading.resource.states"); break;
                        case 13: {
                            BasicGameState startState = new StartState();
                            startState.init(gameContainer, stateBasedGame);

                            BasicGameState readyState = new ReadyState();
                            readyState.init(gameContainer, stateBasedGame);

                            BasicGameState playState = new PlayState();
                            playState.init(gameContainer, stateBasedGame);

                            stateBasedGame.addState(startState);
                            stateBasedGame.addState(readyState);
                            stateBasedGame.addState(playState);

                            _currentlyLoading = Messages.get("loading.complete");
                            break;
                        }
                        default: { _loadState = LoadState.FADE_OUT; break; }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _loadStage++;
                break;
            }
            case FADE_OUT: {
                _fade -= delta/1000f/FADE_SPEED;
                if(_fade <= 0f) {
                    _fade = 0f;
                    _loadState = LoadState.SWITCH_STATE;
                }
                _color = new Color(_fade, _fade, _fade);
                break;
            }
            case SWITCH_STATE: {
                stateBasedGame.enterState(WIP.STATE_START);
                break;
            }
        }
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {

    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

    private enum LoadState { FADE_IN, LOAD, FADE_OUT, SWITCH_STATE }
}
