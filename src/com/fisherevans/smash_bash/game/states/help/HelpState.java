package com.fisherevans.smash_bash.game.states.help;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.OverlayState;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.input.InputController;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.Fonts;
import com.fisherevans.smash_bash.resources.Images;
import com.fisherevans.smash_bash.resources.Inputs;
import com.fisherevans.smash_bash.resources.Settings;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class HelpState extends OverlayState {
    private Font _font;

    private int _count;
    private float _boxSize, _qPadding, _hPadding, _padding;
    private float _startX, _startY;
    private float _lineHeight;

    private String _title, _footer;

    public HelpState(SmashBashState overlayedState) {
        super(overlayedState);
        _count = Inputs.controllers.values().size();
        _font = Fonts.getFont(Config.normalSize);
        _lineHeight = _font.getLineHeight();
        _padding = Config.largeSize;
        _hPadding = _padding/2f;
        _qPadding = _padding/4f;
        float maxSize = (SmashBash.width()-(_count*_padding))/_count;
        _boxSize = Math.min(256, maxSize);
        _startX = (_padding + (maxSize- _boxSize)*_count)/2f;
        _startY = (SmashBash.height()-_lineHeight*2- _boxSize)/2f;

        _title = Settings.getString("helpMenu.title");
        _footer = Settings.getString("helpMenu.footer");
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {
    }

    @Override
    public int getID() {
        return SmashBash.STATE_HELP;
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void update(float delta) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        getOverlayedState().render(graphics);
        graphics.setColor(new Color(0f, 0f, 0f, 0.45f));
        graphics.fillRect(0, 0, SmashBash.width(), SmashBash.height());

        graphics.setFont(Fonts.getStrokedFont(Config.largeSize));
        graphics.setColor(new Color(1f, 1f, 1f));
        graphics.drawStringCentered(_title,
                SmashBash.width()/2f, graphics.getFont().getLineHeight()*0.65f);

        graphics.setFont(_font);
        float x = _startX;
        Image image;
        for(InputController controller: Inputs.controllers.values()) {
            image = Images.getImage(controller.getHelpImageKey());
            graphics.setColor(new Color(0f, 0f, 0f, 0.45f));
            graphics.fillRect(x-_qPadding, +_startY, _boxSize + _qPadding*2f, _boxSize + _lineHeight*2f);
            image.draw(x, _startY + _lineHeight*1.5f, _boxSize, _boxSize);
            graphics.setColor(Color.white);
            graphics.drawStringCentered(controller.getName(), x + _boxSize/2f, _startY + _lineHeight*0.75f);
            x += _padding + _boxSize;
        }

        graphics.setFont(Fonts.getStrokedFont(Config.smallSize));
        graphics.setColor(new Color(1f, 1f, 1f, 0.75f));
        graphics.drawStringCentered(_footer,
                SmashBash.width()/2f, SmashBash.height() - graphics.getFont().getLineHeight());
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {
        SmashBash.enterState(getOverlayedState());
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
