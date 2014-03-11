package com.fisherevans.wipgame;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.tools.MathUtil;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Config {
    public static final Integer[] SIZES = { 16, 32, 48, 64, 96, 128 };
    public static final Color HIGHLIGHT = new Color(0.2f, 0.4f, 0.9f);

    public static Integer largestSize() {
        return SIZES[SIZES.length-1];
    }

    public static Integer getTitleSize() {
        return getRatioSize(200);
    }

    public static Integer getNormalSize() {
        return getRatioSize(400);
    }

    public static Integer getSmallSize() {
        return getRatioSize(600);
    }

    public static Integer getRatioSize(float ratio) {
        return SIZES[(int)MathUtil.clamp(0, WIP.height()/ratio, SIZES.length-1)];
    }
}
