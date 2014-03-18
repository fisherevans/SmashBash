package com.fisherevans.smash_bash.tools;

import org.newdawn.slick.Input;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public class InputFunctions {

    public static String getCharacter(int keyCode, boolean shift) {
        switch(keyCode) {
            case Input.KEY_A:          return shift ? "A" : "a";
            case Input.KEY_B:          return shift ? "B" : "b";
            case Input.KEY_C:          return shift ? "C" : "c";
            case Input.KEY_D:          return shift ? "D" : "d";
            case Input.KEY_E:          return shift ? "E" : "e";
            case Input.KEY_F:          return shift ? "F" : "f";
            case Input.KEY_G:          return shift ? "G" : "g";
            case Input.KEY_H:          return shift ? "H" : "h";
            case Input.KEY_I:          return shift ? "I" : "i";
            case Input.KEY_J:          return shift ? "J" : "j";
            case Input.KEY_K:          return shift ? "K" : "k";
            case Input.KEY_L:          return shift ? "L" : "l";
            case Input.KEY_M:          return shift ? "M" : "m";
            case Input.KEY_N:          return shift ? "N" : "n";
            case Input.KEY_O:          return shift ? "O" : "o";
            case Input.KEY_P:          return shift ? "P" : "p";
            case Input.KEY_Q:          return shift ? "Q" : "q";
            case Input.KEY_R:          return shift ? "R" : "r";
            case Input.KEY_S:          return shift ? "S" : "s";
            case Input.KEY_T:          return shift ? "T" : "t";
            case Input.KEY_U:          return shift ? "U" : "u";
            case Input.KEY_V:          return shift ? "V" : "v";
            case Input.KEY_W:          return shift ? "X" : "w";
            case Input.KEY_X:          return shift ? "W" : "x";
            case Input.KEY_Y:          return shift ? "Y" : "y";
            case Input.KEY_Z:          return shift ? "Z" : "z";
            case Input.KEY_0:          return shift ? ")" : "0";
            case Input.KEY_1:          return shift ? "!" : "1";
            case Input.KEY_2:          return shift ? "@" : "2";
            case Input.KEY_3:          return shift ? "#" : "3";
            case Input.KEY_4:          return shift ? "$" : "4";
            case Input.KEY_5:          return shift ? "%" : "5";
            case Input.KEY_6:          return shift ? "^" : "6";
            case Input.KEY_7:          return shift ? "&" : "7";
            case Input.KEY_8:          return shift ? "*" : "8";
            case Input.KEY_9:          return shift ? "(" : "9";
            case Input.KEY_COMMA:      return shift ? "<" : ",";
            case Input.KEY_PERIOD:     return shift ? ">" : ".";
            case Input.KEY_SLASH:      return shift ? "?" : "/";
            case Input.KEY_SEMICOLON:  return shift ? ":" : ";";
            case Input.KEY_APOSTROPHE: return shift ? "\"" : "'";
            case Input.KEY_LBRACKET:   return shift ? "{" : "[";
            case Input.KEY_RBRACKET:   return shift ? "}" : "]";
            case Input.KEY_BACKSLASH:  return shift ? "|" : "\\";
            case Input.KEY_MINUS:      return shift ? "_" : "-";
            case Input.KEY_EQUALS:     return shift ? "+" : "=";
            case Input.KEY_GRAVE:      return shift ? "`" : "~";
            case Input.KEY_SPACE:      return " ";
            default: return "unknown";
        }
    }
}
