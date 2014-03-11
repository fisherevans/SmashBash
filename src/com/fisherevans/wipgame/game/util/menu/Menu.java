package com.fisherevans.wipgame.game.util.menu;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.util.menu.options.DummyOption;
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
    public static final Color TITLE = Color.white;
    public static final Color SELECTED = new Color(0.2f, 0.4f, 0.9f);
    public static final Color IDLE = new Color(1f, 1f, 1f, 0.75f);

    private List<MenuOption> _options;
    private int _currentSelection;
    private float _slide = 0;

    private float _xPadding, _yPadding;
    private Orientation _orientation;
    private boolean _centerOnSelected, _highlightSelected;
    private float _highlightWidth;
    private AngelCodeFont _font;

    private boolean _hasTitle = false;
    private AngelCodeFont _titleFont = null;

    public Menu(float xPadding, float yPadding, Orientation orientation, boolean centerOnSelected, AngelCodeFont font) {
        this(xPadding, yPadding, orientation, centerOnSelected, false, 0f, font);
    }

    public Menu(float xPadding, float yPadding, Orientation orientation, boolean centerOnSelected, boolean highlightSelected, float highlightWidth, AngelCodeFont font) {
        _xPadding = xPadding;
        _yPadding = yPadding;
        _orientation = orientation;
        _centerOnSelected = centerOnSelected;
        _highlightSelected = highlightSelected;
        _highlightWidth = highlightWidth;
        _font = font;

        _options = new ArrayList<>();
        _currentSelection = 0;
    }

    public void update(float delta) {
        _slide += (_currentSelection-_slide)/2f*delta*SLIDE_SPEED;
    }

    private Color getColor(int id) {
        if(_hasTitle && id == 0)
            return TITLE;
        else if(id == _currentSelection)
            return SELECTED;
        else
            return IDLE;
    }

    public void render(Graphics gfx, float x, float y) {
        float width;
        int id = 0;
        String text;
        float startY = getStartYOffset(), drawX = x;
        if(_highlightSelected) {
            float height = _font.getLineHeight()*1.5f;
            gfx.setColor(Color.white);
            float highlightX = x;
            switch(_orientation) {
                case Left: highlightX = x; break;
                case Center: highlightX = x - _highlightWidth/2f; break;
            }
            gfx.fillRect(highlightX, y - height/2f - ((_slide-_currentSelection)*(_font.getLineHeight()+_yPadding)), _highlightWidth, height);
        }
        for(MenuOption option:_options) {
            gfx.setColor(getColor(id));
            gfx.setFont((_hasTitle && id == 0) ? _titleFont : _font);
            text = option.getDisplayName();
            width = gfx.getFont().getWidth(text);
            switch(_orientation) {
                case Left: drawX = x; break;
                case Center: drawX = x - width/2f; break;
            }
            float finalX = drawX + _xPadding*2;
            float finalY = y + ((gfx.getFont().getLineHeight()+_yPadding)*id) + startY;
            if(_hasTitle && id == 0)
                finalY -= _titleFont.getLineHeight()/2f;
            gfx.drawString(text, finalX, finalY);

            if(option instanceof MenuSetting) {
                MenuSetting setting = (MenuSetting) option;
                if(setting.hasNext())
                    gfx.setColor(id == _currentSelection ? Color.black : Color.white);
                else
                    gfx.setColor(id == _currentSelection ? Color.lightGray : Color.darkGray);
                gfx.drawString(">", x + _highlightWidth - _xPadding - gfx.getFont().getWidth(">"), finalY);
                if(setting.hasPrevious())
                    gfx.setColor(id == _currentSelection ? Color.black : Color.white);
                else
                    gfx.setColor(id == _currentSelection ? Color.lightGray : Color.darkGray);
                gfx.drawString("<", x + _xPadding, finalY);
            }
            id++;
        }
    }

    public float getStartYOffset() {
        if(_centerOnSelected)
            return -_slide*(_font.getLineHeight()+_yPadding) - _font.getLineHeight()/2f;
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

        _currentSelection = MathUtil.clamp(_hasTitle ? 1 : 0, _currentSelection, _options.size()-1);

    }

    public void setTitle(String title, AngelCodeFont titleFont) {
        _options.add(0, new DummyOption(title));
        _titleFont = titleFont;
        _hasTitle = true;
        _currentSelection++;
        _slide++;
    }

    private MenuOption getSelection() {
        return _options.get(_currentSelection);
    }

    public AngelCodeFont getFont() {
        return _font;
    }

    public enum Orientation { Left, Center }
}
