package com.fisherevans.wipgame.game.states.play.entities;

import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class EntitySprite {
    private final Image[] _frames;
    private final float _perFrame, _totalAnimationTime;

    public EntitySprite(Image image) {
        _frames = new Image[] { image };

        _perFrame = Float.MAX_VALUE;
        _totalAnimationTime = _perFrame;
    }

    public EntitySprite(Image spriteSheet, int count, float perFrame) {
        _frames = new Image[count];
        int spriteWidth = spriteSheet.getWidth()/count;
        int spriteHeight = spriteSheet.getHeight();
        for(int id = 0;id < count;id++)
            _frames[id] = spriteSheet.getSubImage(spriteWidth*id, 0, spriteWidth, spriteHeight);

        _perFrame = perFrame;
        _totalAnimationTime = _perFrame*_frames.length;
    }

    public Image getSprite() {
        return _frames[0];
    }

    public Image getSprite(float duration) {
        duration %= _totalAnimationTime;
        return _frames[(int)(duration/_perFrame)];
    }

    public float getPerFrame() {
        return _perFrame;
    }

    public float getTotalAnimationTime() {
        return _totalAnimationTime;
    }
}
