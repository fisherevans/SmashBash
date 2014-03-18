package com.fisherevans.smash_bash.resources;

import com.fisherevans.smash_bash.Config;
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
    public final static String FONT_FOLDER = "res/fonts/angel/";

    private static Map<Integer, AngelCodeFont> _fonts, _strokedFonts;

    private static Font _baseFont;

    public static void load() throws SlickException {
        _fonts = new HashMap<Integer, AngelCodeFont>();
        _strokedFonts = new HashMap<Integer, AngelCodeFont>();
        for(Integer size:Config.SPRITE_SIZES)
            loadFont(size);
    }

    private static void loadFont(Integer size) throws SlickException {
        AngelCodeFont font = new AngelCodeFont(FONT_FOLDER + size + "/stark.fnt", FONT_FOLDER + size + "/stark_0.png");
        AngelCodeFont strokedFont = new AngelCodeFont(FONT_FOLDER + size + "/stark-stroke.fnt", FONT_FOLDER + size + "/stark-stroke_0.png");
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
