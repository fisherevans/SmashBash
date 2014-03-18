package com.fisherevans.smash_bash.game.util.menu.settings;

import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.states.start.StartState;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public class MapProfileSetting<T> extends ObjectSetting {

    public MapProfileSetting(String displayName, T selected, T[] objects) {
        super(displayName, selected, objects);
        updatePreview();
    }

    @Override
    public void previous() {
        super.previous();
        updatePreview();
    }

    @Override
    public void next() {
        super.next();
        updatePreview();
    }

    @Override
    public void select(Object object) {
        super.select(object);
        updatePreview();
    }

    private void updatePreview() {
        if(SmashBash.currentState() instanceof StartState) {
            ((StartState) SmashBash.currentState()).updateCurrentMap();
        }
    }
}
