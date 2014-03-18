package com.fisherevans.smash_bash.game;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public abstract class OverlayState extends SmashBashState {
    private SmashBashState _overlayedState;

    public OverlayState(SmashBashState overlayedState) {
        _overlayedState = overlayedState;
    }

    public SmashBashState getOverlayedState() {
        return _overlayedState;
    }

    public static OverlayState create(Class clazz, SmashBashState overlayedState) throws Exception {
        OverlayState newOverlay = (OverlayState) clazz.getConstructor(SmashBashState.class).newInstance(overlayedState);
        return newOverlay;
    }
}
