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
    public boolean action() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        try {
            ConfirmState confirmState = new ConfirmState(WIP.game.getCurrentState(), "Are you sure you want to quit?", runnable);
            confirmState.init(WIP.container, WIP.game);
            WIP.game.addState(confirmState);
            WIP.game.enterState(confirmState.getID());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
