package com.fisherevans.wipgame.graphics;

import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 3/17/14
 */
public class EntitySprite extends Sprite {
    private float _perFrame, _totalAnimationTime;

    public EntitySprite(Image baseImage, int count, float perFrame) {
        super(baseImage, 0, 0, baseImage.getWidth()/count, baseImage.getHeight(), count);

        _perFrame = perFrame;
        _totalAnimationTime = count*_perFrame;
    }

    public Image getSprite() {
        return getFrame(0);
    }

    public Image getSprite(float duration) {
        duration %= _totalAnimationTime;
        return getFrame((int)(duration/_perFrame));
    }

    public float getPerFrame() {
        return _perFrame;
    }

    public float getTotalAnimationTime() {
        return _totalAnimationTime;
    }
}
