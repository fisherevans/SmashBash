package com.fisherevans.wipgame.game;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class PlayerProfile {
    private int _input;
    private String _character;

    public PlayerProfile(int input, String character) {
        _input = input;
        _character = character;
    }

    public int getInput() {
        return _input;
    }

    public void setInput(int input) {
        _input = input;
    }

    public String getCharacter() {
        return _character;
    }

    public void setCharacter(String character) {
        _character = character;
    }
}
