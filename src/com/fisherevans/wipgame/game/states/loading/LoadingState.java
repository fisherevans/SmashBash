package com.fisherevans.wipgame.game.states.loading;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.ready.ReadyState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.resources.Inputs;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.IOException;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class LoadingState extends WIPState {
    private final static int FONT_SIZE = Config.SIZES[1];
    private final static int FONT_BIG_SIZE = Config.SIZES[3];
    private final static float FADE_SPEED = 0.75f;


    private AngelCodeFont _font, _fontBig;
    private String _title;
    private String _loadingMessage;
    private String _currentlyLoading;
    private Image _bottomRight;

    private LoadState _loadState = LoadState.FADE_IN;
    private int _loadStage = 0;

    private float _fade = 1f;
    private Color _color;

    @Override
    public int getID() {
        return WIP.STATE_LOADING;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _font = new AngelCodeFont(Fonts.FONT_FOLDER + FONT_SIZE + "/stark.fnt", Fonts.FONT_FOLDER + FONT_SIZE + "/stark_0.png");
        _fontBig = new AngelCodeFont(Fonts.FONT_FOLDER + FONT_BIG_SIZE + "/stark.fnt", Fonts.FONT_FOLDER + FONT_BIG_SIZE + "/stark_0.png");

        _color = new Color(1f, 1f, 1f);

        _title = Messages.get("game.name").toUpperCase();
        _loadingMessage = Messages.get("loading.prefix");
        _currentlyLoading = Messages.get("loading.resource.images");

        _bottomRight = new Image("res/img/loading/bottomRight.png", false, Image.FILTER_LINEAR);
        _bottomRight = _bottomRight.getScaledCopy(WIP.container.getWidth()*0.75f/_bottomRight.getWidth());
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.drawImage(_bottomRight, WIP.width()-_bottomRight.getWidth(), WIP.height()-_bottomRight.getHeight());

        graphics.setColor(_color);

        graphics.setFont(_fontBig);
        graphics.drawString(_title,
                (WIP.container.getWidth() - _fontBig.getWidth(_title)) / 2f,
                (WIP.container.getHeight() - _fontBig.getLineHeight()) / 3f);

        graphics.setFont(_font);
        graphics.drawString(_loadingMessage + _currentlyLoading,
                Config.SIZES[0],
                WIP.container.getHeight() - _font.getLineHeight() - Config.SIZES[0]);

        graphics.setColor(new Color(0f, 0f, 0f, _fade));
        graphics.fillRect(0, 0, WIP.width(), WIP.height());
    }

    @Override
    public void update(float delta) {
        switch(_loadState) {
            case FADE_IN: {
                _fade -= delta/FADE_SPEED;
                if(_fade < 0f) {
                    _fade = 0f;
                    _loadState = LoadState.LOAD;
                }
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
                        case 10: _currentlyLoading = Messages.get("loading.resource.inputs"); break;
                        case 11: Inputs.load(); break;
                        case 12: _currentlyLoading = Messages.get("loading.resource.states"); break;
                        case 13: {
                            StartState startState = new StartState();
                            startState.init();
                            WIP.game.addState(startState);
                            _currentlyLoading = Messages.get("loading.complete");
                            break;
                        }
                        default: { _loadState = LoadState.SWITCH_STATE; break; }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _loadStage++;
                break;
            }
            case SWITCH_STATE: {
                WIP.game.enterState(WIP.STATE_START, new FadeOutTransition(), new FadeInTransition());
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

    private enum LoadState { FADE_IN, LOAD, SWITCH_STATE }
}
