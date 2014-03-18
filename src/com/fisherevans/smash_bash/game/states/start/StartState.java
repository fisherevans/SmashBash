package com.fisherevans.smash_bash.game.states.start;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.game.game_config.GameSettings;
import com.fisherevans.smash_bash.game.states.ready.ReadyState;
import com.fisherevans.smash_bash.game.util.menu.Menu;
import com.fisherevans.smash_bash.game.util.menu.options.QuitOption;
import com.fisherevans.smash_bash.game.util.menu.options.RunnableOption;
import com.fisherevans.smash_bash.game.util.menu.settings.MapProfileSetting;
import com.fisherevans.smash_bash.game.util.menu.settings.NumberSetting;
import com.fisherevans.smash_bash.game.util.menu.settings.ObjectSetting;
import com.fisherevans.smash_bash.game.util.menu.settings.TimeSetting;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.*;
import com.fisherevans.smash_bash.tools.GraphicFunctions;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class StartState extends SmashBashState {
    private Menu _menu;

    private NumberSetting _lives, _health, _time;
    private ObjectSetting<MapSet> _maps;

    private Image _mapPreviewFade, _verticalDownFade;
    private MapPreviewDisplay _mapPreview;
    private List<MapPreviewDisplay> _fadingPreviews = new ArrayList<MapPreviewDisplay>();

    @Override
    public int getID() {
        return SmashBash.STATE_START;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _maps = new MapProfileSetting(Settings.getString("start.map"), Maps.getMapSet(Maps.getMapCodes()[0]), Maps.getMapSets());
        _lives = new NumberSetting(Settings.getString("start.lives"), 1, SmashBash.gameSettings.lives, 10);
        _health = new NumberSetting(Settings.getString("start.health"), 50, SmashBash.gameSettings.health, 200, 25);
        _time = new TimeSetting(Settings.getString("start.timeLimit"), Settings.getString("start.timeLimit.minutes"), 1, SmashBash.gameSettings.time, 11);

        _menu = new Menu(Config.normalSize, Config.normalSize, Menu.Orientation.Left, true, true, SmashBash.width()/2f, Fonts.getFont(Config.normalSize));
        _menu.add(new RunnableOption(Settings.getString("start.play"), new Runnable() {
            @Override
            public void run() {
                SmashBash.gameSettings.map = _maps.getSelected().getCode();
                SmashBash.gameSettings.lives = _lives.getCurrent();
                SmashBash.gameSettings.health = _health.getCurrent();
                SmashBash.gameSettings.time = _time.getCurrent();

                SmashBash.enterNewState(new ReadyState());
            }
        }));
        _menu.add(_maps);
        _menu.add(_lives);
        _menu.add(_health);
        _menu.add(_time);
        _menu.add(new QuitOption(Settings.getString("start.exit")));
        _menu.setTitle(Settings.getString("start.title"), Fonts.getFont(Config.hugeSize));

        _mapPreview = new MapPreviewDisplay(Images.getImage(_maps.getSelected().preview));
        _mapPreviewFade = Images.getImage("gui/fade_right");

        _verticalDownFade = Images.getImage("gui/fade_down");
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        _menu.render(graphics, 0f, SmashBash.height()/2f);

        _mapPreview.render(graphics);
        for(int id = _fadingPreviews.size()-1;id >= 0;id--)
            _fadingPreviews.get(id).render(graphics);
        _mapPreviewFade.draw(SmashBash.width() / 2f, 0f, SmashBash.width() / 4f, SmashBash.height());

        float fadeSize = Config.largeSize*2f;
        _verticalDownFade.draw(0, 0, SmashBash.width()/2f, fadeSize);
        _verticalDownFade.getFlippedCopy(false, true).draw(0, SmashBash.height()-fadeSize, SmashBash.width()/2f, fadeSize);

        GraphicFunctions.drawHelpKey(graphics, new Color(1f, 1f, 1f, 0.5f),
                Settings.getString("key.f1"), Settings.getString("start.helpMenu"),
                Config.normalSize * 2, 10, Config.smallSize);
    }

    @Override
    public void update(float delta) {
        _menu.update(delta);
        for(int id = 0;id < _fadingPreviews.size();id++) {
            _fadingPreviews.get(id).update(delta);
            if(_fadingPreviews.get(id).isDone())
                _fadingPreviews.remove(id--);
        }
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    public GameSettings getGameSettings() {
        return new GameSettings();
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        _menu.keyDown(key);
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

    public void updateCurrentMap() {
        _fadingPreviews.add(_mapPreview);
        _mapPreview = new MapPreviewDisplay(Images.getImage(_maps.getSelected().preview));
    }

    private class MapPreviewDisplay {
        public static final float FADE_SPEED = 2.5f;

        private Image _preview;
        private float _alpha;

        private MapPreviewDisplay(Image preview) {
            _preview = preview;
            _alpha = 1f;
        }

        public void update(float delta) {
            _alpha -= delta*FADE_SPEED;
            if(_alpha < 0)
                _alpha = 0;
        }

        public void render(Graphics graphics) {
            float widthRatio = (SmashBash.width()/2f)/_preview.getWidth();
            float heightRatio = SmashBash.height()/_preview.getHeight();
            Image scaledPreview = _preview.getScaledCopy(Math.max(widthRatio, heightRatio));
            graphics.drawImage(scaledPreview, SmashBash.width() / 2f,
                    (SmashBash.height() - scaledPreview.getHeight()) / 2f, getColor());
        }

        public boolean isDone() {
            return _alpha <= 0;
        }

        public Image getPreview() {
            return _preview;
        }

        public Color getColor() {
            return new Color(1f, 1f, 1f, _alpha*_alpha);
        }
    }
}
