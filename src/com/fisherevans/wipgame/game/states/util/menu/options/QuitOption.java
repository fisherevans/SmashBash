package com.fisherevans.wipgame.game.states.util.menu.options;

import com.fisherevans.wipgame.Main;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.confirm.ConfirmState;
import com.fisherevans.wipgame.game.states.util.menu.MenuOption;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class QuitOption extends MenuOption {
    public QuitOption(String displayName) {
        super(displayName);
    }

    @Override
    public void action() {
        ConfirmState.enter(WIP.currentState(), "Are you sure you want to quit?", new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });
    }
}
