package com.fisherevans.wipgame.game.states.play.combat_elements;

import com.fisherevans.wipgame.game.states.play.GameObject;

/**
 * Author: Fisher Evans
 * Date: 3/4/14
 */
public abstract class Area {
    private boolean _inclusive;

    public Area(boolean inclusive) {
        _inclusive = inclusive;
    }

    public boolean isInclusive() {
        return _inclusive;
    }

    public abstract boolean inArea(GameObject gameObject);
}
