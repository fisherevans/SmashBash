package com.fisherevans.wipgame.tools;

/**
 * Author: Fisher Evans
 * Date: 2/12/14
 */
public class MathUtil {
    public static float clamp(float min, float x, float max) {
        x = x > min ? x : min;
        x = x < max ? x : max;
        return x;
    }

    public static int clamp(int min, int x, int max) {
        x = x > min ? x : min;
        x = x < max ? x : max;
        return x;
    }
}
