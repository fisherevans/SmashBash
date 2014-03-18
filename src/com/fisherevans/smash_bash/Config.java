package com.fisherevans.smash_bash;

import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.resources.Settings;
import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Config {
    public static final Integer[] SPRITE_SIZES = { 16, 32, 48, 64, 96, 128 };

    public static int smallSize;
    public static int normalSize;
    public static int largeSize;
    public static int hugeSize;

    public static Color highlightColor;

    public static void init() {
        initSizes();
        initColors();
    }

    private static void initSizes() {
        int baseSizeId = SmashBash.width() > 1000 ? 1 : 0;
        smallSize = SPRITE_SIZES[baseSizeId];
        normalSize = SPRITE_SIZES[baseSizeId+1];
        largeSize = SPRITE_SIZES[baseSizeId+2];
        hugeSize = SPRITE_SIZES[baseSizeId+3];
    }

    private static void initColors() {
        highlightColor = Settings.getColor("config.colors.highlight");
    }
}
