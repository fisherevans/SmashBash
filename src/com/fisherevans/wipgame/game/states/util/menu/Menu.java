package com.fisherevans.wipgame.game.states.util.menu;

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
    private List<MenuOption> _options;
    private int _currentSelection;

    private int _padding;
    private Orientation _orientation;
    private boolean _centerOnSelected;
    private boolean _vertical;
    private AngelCodeFont _font;

    public Menu(int padding, Orientation orientation, boolean centerOnSelected, boolean vertical, AngelCodeFont font) {
        _padding = padding;
        _orientation = orientation;
        _centerOnSelected = centerOnSelected;
        _vertical = vertical;
        _font = font;

        _options = new ArrayList<>();
        _currentSelection = 0;
    }

    public void render(Graphics gfx, float center, float y) {
        float textHeight = _font.getHeight("|#8"), width;
        int id = 0;
        String text;
        gfx.setFont(_font);
        for(MenuOption option:_options) {
            if(id == _currentSelection)
                gfx.setColor(new Color(0.2f, 0.4f, 0.9f));
            else
                gfx.setColor(new Color(1f, 1f, 1f, 0.75f));
            text = option.getDisplayName();
            width = _font.getWidth(text);
            gfx.drawString(text, center - width/2f, y + ((textHeight+_padding)*id));
            id++;
        }
    }

    public void add(MenuOption menuOption) {
        _options.add(menuOption);
    }

    public void keyDown(Key key) {
        if(_vertical) {
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
            }
        } else {
            switch(key) {
                case Down:
                    if(getSelection() instanceof MenuSetting)
                        ((MenuSetting) getSelection()).next();
                    break;
                case Up:
                    if(getSelection() instanceof MenuSetting)
                        ((MenuSetting) getSelection()).previous();
                    break;
                case Left:
                    _currentSelection--;
                    break;
                case Right:
                    _currentSelection++;
                    break;
            }
        }
        switch(key) {
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

    public enum Orientation { Left, Center, Right }
}
