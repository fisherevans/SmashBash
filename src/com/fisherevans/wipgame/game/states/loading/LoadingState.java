package com.fisherevans.wipgame.game.states.loading;

import com.fisherevans.wipgame.game.Game;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.ready.ReadyState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.input.Inputs;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class LoadingState extends BasicGameState {
    private final static String LOADING_IMAGE_LOCATION = "res/img/loading/loading.png";
    private final static float FADE_SPEED = 0.75f;

    private Image _loadingImage;
    private float _imageX, _imageY;

    private UnicodeFont _font;
    private String _currentlyLoading = "Images";

    private LoadState _loadState = LoadState.FADE_IN;
    private int _loadStage = 0;

    private float _fade = 0;
    private Color _color;

    @Override
    public int getID() {
        return Game.STATE_LOADING;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _loadingImage = new Image(LOADING_IMAGE_LOCATION);

        _imageX = (gameContainer.getWidth() - _loadingImage.getWidth())/2f;
        _imageY = (gameContainer.getHeight() - _loadingImage.getHeight())/2f;

        _font = new UnicodeFont(Fonts.FONT_FILE, 16, false, false);
        _font.addAsciiGlyphs();
        _font.addGlyphs(400, 600);
        _font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        _font.loadGlyphs();

        _color = new Color(0, 0, 0);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setAntiAlias(false);
        graphics.drawImage(_loadingImage, _imageX, _imageY, _color);

        graphics.setAntiAlias(true);
        graphics.setFont(_font);
        graphics.setColor(_color);
        graphics.drawString(_currentlyLoading, 10, gameContainer.getHeight()-26);
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
                        case 0: _currentlyLoading = "Images"; break;
                        case 1: Images.load(); break;
                        case 2: _currentlyLoading = "Fonts"; break;
                        case 3: Fonts.load(); break;
                        case 4: _currentlyLoading = "Inputs"; break;
                        case 5: Inputs.load(); break;
                        case 6: _currentlyLoading = "States"; break;
                        case 7: {
                            BasicGameState startState = new StartState();
                            startState.init(gameContainer, stateBasedGame);

                            BasicGameState readyState = new ReadyState();
                            readyState.init(gameContainer, stateBasedGame);

                            BasicGameState playState = new PlayState();
                            playState.init(gameContainer, stateBasedGame);

                            stateBasedGame.addState(startState);
                            stateBasedGame.addState(readyState);
                            stateBasedGame.addState(playState);
                            break;
                        }
                        case 8: _currentlyLoading = "Complete"; break;
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
                stateBasedGame.enterState(Game.STATE_START);
                break;
            }
        }
    }

    private enum LoadState { FADE_IN, LOAD, FADE_OUT, SWITCH_STATE }
}
