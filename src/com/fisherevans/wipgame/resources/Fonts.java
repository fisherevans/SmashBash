package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Config;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Fonts {
    public final static Integer TINY = Config.SIZES[0];
    public final static Integer SMALL = Config.SIZES[1];
    public final static Integer REGULAR = Config.SIZES[2];
    public final static Integer LARGE = Config.SIZES[3];
    public final static Integer HUGE = Config.SIZES[4];

    public final static String FONT_FOLDER = "res/fonts/";

    private static Map<Integer, AngelCodeFont> _fonts, _strokedFonts;

    private static Font _baseFont;

    public static void load() throws SlickException {
        _fonts = new HashMap<>();
        _strokedFonts = new HashMap<>();
        for(Integer size:Config.SIZES)
            loadFont(size);
    }

    private static void loadFont(Integer size) throws SlickException {
        AngelCodeFont font = new AngelCodeFont(FONT_FOLDER + "stark-" + size + ".fnt", FONT_FOLDER + "stark-" + size + "_0.png");
        AngelCodeFont strokedFont = new AngelCodeFont(FONT_FOLDER + "stark-" + size + "-stroke.fnt", FONT_FOLDER + "stark-" + size + "-stroke_0.png");
        _fonts.put(size, font);
        _strokedFonts.put(size, strokedFont);
    }

    public static AngelCodeFont getFont(int size) {
        return _fonts.get(size);
    }

    public static AngelCodeFont getStrokedFont(int size) {
        return _strokedFonts.get(size);
    }
}
