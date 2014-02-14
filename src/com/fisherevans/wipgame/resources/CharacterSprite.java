package com.fisherevans.wipgame.resources;

import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class CharacterSprite {
    private final Image _idle;
    private final Image _falling;
    private final Image _walking1, _walking2, _walking3;

    public CharacterSprite(Image idle, Image falling, Image walking1, Image walking2, Image walking3) {
        _idle = idle;
        _falling = falling;
        _walking1 = walking1;
        _walking2 = walking2;
        _walking3 = walking3;
    }

    public Image getIdle() {
        return _idle;
    }

    public Image getFalling() {
        return _falling;
    }

    public Image getWalking1() {
        return _walking1;
    }

    public Image getWalking2() {
        return _walking2;
    }

    public Image getWalking3() {
        return _walking3;
    }
}
