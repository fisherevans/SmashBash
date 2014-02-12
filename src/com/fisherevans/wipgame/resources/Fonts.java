package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.Log;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Fonts {
    public final static Integer TINY = 8;
    public final static Integer SMALL = 16;
    public final static Integer REGULAR = 24;
    public final static Integer LARGE = 32;
    public final static Integer HUGE = 40;

    private final static Integer[] DEFAULT_FONT_SIZES =
            { TINY, SMALL, REGULAR, LARGE, HUGE };

    public final static String FONT_FILE = "res/fonts/Stark.ttf";

    private static Map<Integer, UnicodeFont> _fonts;

    private static Font _baseFont;

    public static void load() throws SlickException {
        _fonts = new HashMap<>();
        for(Integer size:DEFAULT_FONT_SIZES)
            loadFont(size);
    }

    private static UnicodeFont loadFont(Integer size) throws SlickException {
        Log.d("Loading font " + FONT_FILE + " size: " + size);
        UnicodeFont font = new UnicodeFont(FONT_FILE, size, false, false);
        font.addAsciiGlyphs();
        font.addGlyphs(400, 600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
        _fonts.put(size, font);
        return font;
    }

    public static UnicodeFont getFont(int size) {
        UnicodeFont font = _fonts.get(size);
        try {
            if(font == null)
                return loadFont(size);
            else
                return font;
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return font;
    }
}
