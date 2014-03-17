package com.fisherevans.wipgame.graphics;

import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 3/17/14
 */
public class Sprite {
    private Image[] _frames;
    private Image _baseImage;
    private int _x, _y;
    private int _width, _height, _count;

    public Sprite(Image baseImage, int x, int y, int width, int height, int count) {
        _baseImage = baseImage;
        _x = x;
        _y = y;
        _width = width;
        _height = height;
        _count = count;

        _frames = new Image[_count];
        for(int id = 0;id < _count;id++)
            _frames[id] = _baseImage.getSubImage(_x + _width*id, _y, _width, _height);
    }

    public Image getFrame(int id) {
        if(id >= 0 && id < _count)
            return _frames[id];
        else
            return null;
    }

    public Image[] getFrames() {
        return _frames;
    }

    public Image getBaseImage() {
        return _baseImage;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public int getCount() {
        return _count;
    }
}
