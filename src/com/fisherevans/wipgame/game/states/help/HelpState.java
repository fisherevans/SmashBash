package com.fisherevans.wipgame.game.states.help;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.OverlayState;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.input.InputController;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.resources.Inputs;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class HelpState extends OverlayState {
    public static int MAX_HORZ = 4;
    public HelpState(WIPState overlayedState) {
        super(overlayedState);
    }

    @Override
    public int getID() {
        return WIP.STATE_HELP;
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
        graphics.fillRect(0, 0, WIP.width(), WIP.height());

        graphics.setFont(Fonts.getStrokedFont(Config.getTitleSize()));
        graphics.setColor(new Color(1f, 1f, 1f));
        graphics.drawStringCentered("Control Menu",
                WIP.width()/2f, graphics.getFont().getLineHeight()*0.65f);

        graphics.setFont(Fonts.getFont(Config.getNormalSize()));
        float lh = graphics.getFont().getLineHeight();
        float padding = Config.getTitleSize();
        float qp = padding/4f;
        int count = Inputs.controllers.values().size();
        float maxSize = (WIP.width()-(count*padding))/count;
        float size = Math.min(256, maxSize);
        float x = (padding + (maxSize-size)*count)/2f;
        float y = (WIP.height()-lh*2-size)/2f;
        Image image;
        for(InputController controller: Inputs.controllers.values()) {
            image = Images.getImage(controller.getHelpImageKey());
            graphics.setColor(new Color(0f, 0f, 0f, 0.45f));
            graphics.fillRect(x-qp, y, size + qp*2f, size + lh*2f);
            image.draw(x, y + lh*1.5f, size, size);
            graphics.setColor(Color.white);
            graphics.drawStringCentered(controller.getName(), x + size/2f, y + lh*0.75f);
            x += padding + size;
        }

        graphics.setFont(Fonts.getStrokedFont(Config.getSmallSize()));
        graphics.setColor(new Color(1f, 1f, 1f, 0.75f));
        graphics.drawStringCentered("Press any key to return...",
                WIP.width()/2f, WIP.height() - graphics.getFont().getLineHeight());
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {
        WIP.enterState(getOverlayedState());
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
