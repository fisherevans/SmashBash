package com.fisherevans.smash_bash.input;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public interface InputsListener {
    public abstract void keyDown(Key key, int inputSource);
    public abstract void keyUp(Key key, int inputSource);
}
