package com.fisherevans.wipgame.game.util.menu;

import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.tools.MathUtil;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class Menu {
    public static final float SLIDE_SPEED = 15f;

    private List<MenuOption> _options;
    private int _currentSelection;
    private float _slide = 0;

    private int _padding;
    private Orientation _orientation;
    private boolean _centerOnSelected;
    private AngelCodeFont _font;

    public Menu(int padding, Orientation orientation, boolean centerOnSelected, AngelCodeFont font) {
        _padding = padding;
        _orientation = orientation;
        _centerOnSelected = centerOnSelected;
        _font = font;

        _options = new ArrayList<>();
        _currentSelection = 0;
    }

    public void update(float delta) {
        _slide += (_currentSelection-_slide)/2f*delta*SLIDE_SPEED;
    }

    public void render(Graphics gfx, float x, float y) {
        float width;
        int id = 0;
        String text;
        gfx.setFont(_font);
        float startY = getStartYOffset(), drawX = x;
        for(MenuOption option:_options) {
            if(id == _currentSelection)
                gfx.setColor(new Color(0.2f, 0.4f, 0.9f));
            else
                gfx.setColor(new Color(1f, 1f, 1f, 0.75f));
            text = option.getDisplayName();
            width = _font.getWidth(text);
            switch(_orientation) {
                case Left: drawX = x; break;
                case Center: drawX = x - width/2f; break;
            }
            gfx.drawString(text, drawX, y + ((_font.getLineHeight()+_padding)*id) + startY);
            id++;
        }
    }

    public float getStartYOffset() {
        if(_centerOnSelected)
            return -_slide*(_font.getLineHeight()+_padding);
        else
            return 0f;
    }

    public void add(MenuOption menuOption) {
        _options.add(menuOption);
    }

    public void keyDown(Key key) {
        switch(key) {
            case Down:
                _currentSelection++;
                break;
            case Up:
                _currentSelection--;
                break;
            case Left:
                if(getSelection() instanceof MenuSetting)
                    ((MenuSetting) getSelection()).previous();
                break;
            case Right:
                if(getSelection() instanceof MenuSetting)
                    ((MenuSetting) getSelection()).next();
                break;
            case Select:
                getSelection().action();
                break;
            case Back:

                break;
            case Menu:

                break;
        }

        _currentSelection = MathUtil.clamp(0, _currentSelection, _options.size()-1);

    }

    private MenuOption getSelection() {
        return _options.get(_currentSelection);
    }

    public enum Orientation { Left, Center }
}
