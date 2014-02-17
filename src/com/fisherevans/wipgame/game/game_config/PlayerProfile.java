package com.fisherevans.wipgame.game.game_config;

import org.newdawn.slick.Color;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class PlayerProfile {
    public static String[] CHARACTERS = { "base" };
    public static Color[] COLORS = { Color.white, Color.cyan, Color.blue, Color.red, Color.darkGray };

    private int _input;
    private String _character;
    private Color _color;
    private boolean _ready;

    public PlayerProfile(int input) {
        _input = input;
        _character = CHARACTERS[0];
        _color = COLORS[0];
        _ready = false;
    }

    public int getInput() {
        return _input;
    }

    public String getCharacter() {
        return _character;
    }

    public void setCharacter(String character) {
        _character = character;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public boolean isReady() {
        return _ready;
    }

    public void setReady(boolean ready) {
        _ready = ready;
    }
}
