package com.fisherevans.wipgame.game.util.menu.options;

import com.fisherevans.wipgame.game.util.menu.MenuOption;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class RunnableOption extends MenuOption {
    private Runnable _runnable;

    public RunnableOption(String displayName, Runnable runnable) {
        super(displayName);
        _runnable = runnable;
    }

    @Override
    public void action() {
        _runnable.run();
    }
}
