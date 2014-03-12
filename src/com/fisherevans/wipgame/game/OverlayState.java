package com.fisherevans.wipgame.game;

/**
 * Author: Fisher Evans
 * Date: 3/12/14
 */
public abstract class OverlayState extends WIPState {
    private WIPState _overlayedState;

    public OverlayState(WIPState overlayedState) {
        _overlayedState = overlayedState;
    }

    public WIPState getOverlayedState() {
        return _overlayedState;
    }

    public static OverlayState create(Class clazz, WIPState overlayedState) throws Exception {
        OverlayState newOverlay = (OverlayState) clazz.getConstructor(WIPState.class).newInstance(overlayedState);
        return newOverlay;
    }
}
