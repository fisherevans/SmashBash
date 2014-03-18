package com.fisherevans.smash_bash.tools;

import com.fisherevans.smash_bash.resources.Fonts;
import com.fisherevans.smash_bash.resources.Images;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class GraphicFunctions {
    public static Image _keyImage;
    public static String _keyHelpPrefix = "Press  ";

    public static void init() {
        _keyImage = Images.getImage("gui/inputs/key");
    }

    public static void drawKey(Graphics gfx, Color color, String key, float x, float y, Font font) {
        float size = font.getLineHeight()*1.25f;
        gfx.setFont(font);
        gfx.setColor(color);
        _keyImage.draw(x, y, size, size, color);
        gfx.drawStringCentered(key, x + size/2f, y + size/2f);
    }

    public static void drawHelpKey(Graphics gfx, Color color, String key, String does, int x, int y, int size) {
        Font font = Fonts.getFont(size);
        gfx.setFont(font);
        float dy = size*0.125f;
        gfx.setColor(color);
        gfx.drawString(_keyHelpPrefix, x, y + dy);
        float dx = gfx.getFont().getWidth(_keyHelpPrefix);
        drawKey(gfx, color, key, x + dx, y, font);
        dx +=  size*1.5f;
        gfx.drawString(does, x + dx, y + dy);
    }
}
