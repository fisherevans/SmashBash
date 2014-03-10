package com.fisherevans.wipgame.game.util.menu.settings;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.start.StartState;
import com.fisherevans.wipgame.game.util.menu.MenuSetting;
import com.fisherevans.wipgame.tools.MathUtil;

import java.util.ArrayList;
import java.util.List;

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
        if(WIP.currentState() instanceof StartState) {
            ((StartState)WIP.currentState()).updateCurrentMap();
        }
    }
}
