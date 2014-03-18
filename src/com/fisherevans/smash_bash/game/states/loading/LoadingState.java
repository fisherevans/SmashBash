package com.fisherevans.smash_bash.game.states.loading;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.game.states.start.StartState;
import com.fisherevans.smash_bash.resources.Inputs;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.*;
import com.fisherevans.smash_bash.tools.GraphicFunctions;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class LoadingState extends SmashBashState {
    private final static int FONT_SIZE = Config.SPRITE_SIZES[1];
    private final static int FONT_BIG_SIZE = Config.SPRITE_SIZES[3];
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
        return SmashBash.STATE_LOADING;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _font = new AngelCodeFont(Fonts.FONT_FOLDER + FONT_SIZE + "/stark.fnt", Fonts.FONT_FOLDER + FONT_SIZE + "/stark_0.png");
        _fontBig = new AngelCodeFont(Fonts.FONT_FOLDER + FONT_BIG_SIZE + "/stark.fnt", Fonts.FONT_FOLDER + FONT_BIG_SIZE + "/stark_0.png");

        _color = new Color(1f, 1f, 1f);

        _title = Settings.getString("game.name").toUpperCase();
        _loadingMessage = Settings.getString("loading.prefix");
        _currentlyLoading = Settings.getString("loading.resource.images");

        _bottomRight = new Image("res/img/loading/bottomRight.png", false, Image.FILTER_LINEAR);
        _bottomRight = _bottomRight.getScaledCopy(SmashBash.container.getWidth()*0.75f/_bottomRight.getWidth());
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.drawImage(_bottomRight, SmashBash.width()-_bottomRight.getWidth(), SmashBash.height()-_bottomRight.getHeight());

        graphics.setColor(_color);

        graphics.setFont(_fontBig);
        graphics.drawString(_title,
                (SmashBash.container.getWidth() - _fontBig.getWidth(_title)) / 2f,
                (SmashBash.container.getHeight() - _fontBig.getLineHeight()) / 3f);

        graphics.setFont(_font);
        graphics.drawString(_loadingMessage + _currentlyLoading,
                Config.SPRITE_SIZES[0],
                SmashBash.container.getHeight() - _font.getLineHeight() - Config.SPRITE_SIZES[0]);

        graphics.setColor(new Color(0f, 0f, 0f, _fade));
        graphics.fillRect(0, 0, SmashBash.width(), SmashBash.height());
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
                        case 0: _currentlyLoading = Settings.getString("loading.resource.images"); break;
                        case 1: Images.load(); break;
                        case 2: _currentlyLoading = Settings.getString("loading.resource.fonts"); break;
                        case 3: Fonts.load(); break;
                        case 4: _currentlyLoading = Settings.getString("loading.resource.entities"); break;
                        case 5: Entities.load(); break;
                        case 6: _currentlyLoading = Settings.getString("loading.resource.lights"); break;
                        case 7: Lights.load(); break;
                        case 8: _currentlyLoading = Settings.getString("loading.resource.maps"); break;
                        case 9: Maps.load(); break;
                        case 10: _currentlyLoading = Settings.getString("loading.resource.characters"); break;
                        case 11: Characters.init(); break;
                        case 12: _currentlyLoading = Settings.getString("loading.resource.inputs"); break;
                        case 13: Inputs.load(); break;
                        case 14: _currentlyLoading = Settings.getString("loading.resource.states"); break;
                        case 15: GraphicFunctions.init(); break;
                        case 16: _currentlyLoading = Settings.getString("loading.resource.graphicFunctions"); break;
                        case 17: Config.init(); break;
                        case 18: _currentlyLoading = Settings.getString("loading.resource.config"); break;
                        default: {
                            StartState startState = new StartState();
                            startState.init();
                            SmashBash.game.addState(startState);
                            _currentlyLoading = Settings.getString("loading.complete");
                            // END THE LOADING
                            _loadState = LoadState.SWITCH_STATE; break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _loadStage++;
                break;
            }
            case SWITCH_STATE: {
                SmashBash.game.enterState(SmashBash.STATE_START, new FadeOutTransition(), new FadeInTransition());
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
