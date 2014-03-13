package com.fisherevans.wipgame.game.util.menu.options;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.confirm.ConfirmState;
import com.fisherevans.wipgame.game.util.menu.MenuOption;
import com.fisherevans.wipgame.resources.Settings;

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
        ConfirmState.enter(WIP.currentState(), Settings.getString("confirm.quitText"), new Runnable() {
            @Override
            public void run() {
                WIP.exitGame();
            }
        });
    }
}
