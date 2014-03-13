package com.fisherevans.wipgame.game.util.menu.options;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.confirm.ConfirmState;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.game.util.menu.MenuOption;
import com.fisherevans.wipgame.resources.Settings;

/**
 * Author: Fisher Evans
 * Date: 2/15/14
 */
public class EndGameOption extends MenuOption {
    public EndGameOption(String displayName) {
        super(displayName);
    }

    @Override
    public void action() {
        ConfirmState.enter(WIP.currentState(), Settings.getString("confirm.endGameText"), new Runnable() {
            @Override
            public void run() {
                WIP.enterNewState(new StartState());
            }
        });
    }
}
