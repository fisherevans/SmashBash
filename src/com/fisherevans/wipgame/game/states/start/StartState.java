package com.fisherevans.wipgame.game.states.start;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.game_config.GameSettings;
import com.fisherevans.wipgame.game.game_config.MapProfile;
import com.fisherevans.wipgame.game.states.ready.ReadyState;
import com.fisherevans.wipgame.game.util.menu.Menu;
import com.fisherevans.wipgame.game.util.menu.options.QuitOption;
import com.fisherevans.wipgame.game.util.menu.options.RunnableOption;
import com.fisherevans.wipgame.game.util.menu.settings.MapProfileSetting;
import com.fisherevans.wipgame.game.util.menu.settings.NumberSetting;
import com.fisherevans.wipgame.game.util.menu.settings.ObjectSetting;
import com.fisherevans.wipgame.game.util.menu.settings.TimeSetting;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.resources.Maps;
import com.fisherevans.wipgame.tools.GraphicFunctions;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class StartState extends WIPState {
    private Menu _menu;

    private NumberSetting _lives, _health, _time;
    private ObjectSetting<MapProfile> _maps;

    private Image _mapPreviewFade, _verticalDownFade;
    private MapPreviewDisplay _mapPreview;
    private List<MapPreviewDisplay> _fadingPreviews = new ArrayList<MapPreviewDisplay>();

    @Override
    public int getID() {
        return WIP.STATE_START;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _maps = new MapProfileSetting<MapProfile>("Map", new MapProfile(WIP.gameSettings.map), Maps.getProfiles());
        _lives = new NumberSetting("Lives", 1, WIP.gameSettings.lives, 10);
        _health = new NumberSetting("Health", 50, WIP.gameSettings.health, 200, 25);
        _time = new TimeSetting("Time Limit", "Minutes", 1, WIP.gameSettings.time, 11);

        _menu = new Menu(Config.getNormalSize(), Config.getNormalSize(), Menu.Orientation.Left, true, true, WIP.width()/2f, Fonts.getFont(Config.getNormalSize()));
        _menu.add(new RunnableOption("Play", new Runnable() {
            @Override
            public void run() {
                WIP.gameSettings.map = _maps.getSelected().getName();
                WIP.gameSettings.lives = _lives.getCurrent();
                WIP.gameSettings.health = _health.getCurrent();
                WIP.gameSettings.time = _time.getCurrent();

                WIP.enterNewState(new ReadyState());
            }
        }));
        _menu.add(_maps);
        _menu.add(_lives);
        _menu.add(_health);
        _menu.add(_time);
        _menu.add(new QuitOption("Exit to Desktop"));
        _menu.setTitle("Main Menu", Fonts.getFont(Config.getTitleSize()));

        _mapPreview = new MapPreviewDisplay(_maps.getSelected().getPreviewImage());
        _mapPreviewFade = Images.getImage("gui/fade_right");

        _verticalDownFade = Images.getImage("gui/fade_down");
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        _menu.render(graphics, 0f, WIP.height()/2f);

        _mapPreview.render(graphics);
        for(int id = _fadingPreviews.size()-1;id >= 0;id--)
            _fadingPreviews.get(id).render(graphics);
        _mapPreviewFade.draw(WIP.width() / 2f, 0f, WIP.width() / 4f, WIP.height());

        float fadeSize = Config.getTitleSize()*2f;
        _verticalDownFade.draw(0, 0, WIP.width()/2f, fadeSize);
        _verticalDownFade.getFlippedCopy(false, true).draw(0, WIP.height()-fadeSize, WIP.width()/2f, fadeSize);

        GraphicFunctions.drawHelpKey(graphics, new Color(1f, 1f, 1f, 0.5f),
                "F1", "to open the Controls Help Menu",
                Config.getNormalSize() * 2, 10, Config.getSmallSize());
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
        _mapPreview = new MapPreviewDisplay(_maps.getSelected().getPreviewImage());
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
            float widthRatio = (WIP.width()/2f)/_preview.getWidth();
            float heightRatio = WIP.height()/_preview.getHeight();
            Image scaledPreview = _preview.getScaledCopy(Math.max(widthRatio, heightRatio));
            graphics.drawImage(scaledPreview, WIP.width() / 2f,
                    (WIP.height() - scaledPreview.getHeight()) / 2f, getColor());
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
