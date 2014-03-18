package com.fisherevans.smash_bash.game;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public interface TypingState {
    public void typed(String text);

    public void keyEnter();

    public void keyBackspace();

    public void keyDelete();

    public void keyArrowLeft();

    public void keyArrowRight();

    public void keyArrowUp();

    public void keyArrowDown();
}
