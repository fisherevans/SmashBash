package com.fisherevans.smash_bash.game.util.menu.options;

import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.states.confirm.ConfirmState;
import com.fisherevans.smash_bash.game.states.start.StartState;
import com.fisherevans.smash_bash.game.util.menu.MenuOption;
import com.fisherevans.smash_bash.resources.Settings;

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
        ConfirmState.enter(SmashBash.currentState(), Settings.getString("confirm.endGameText"), new Runnable() {
            @Override
            public void run() {
                SmashBash.enterNewState(new StartState());
            }
        });
    }
}
