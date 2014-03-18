package com.fisherevans.smash_bash.game.states.ready;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public enum ReadyPlayerState {
    NotPlaying,
    Selecting,
    Ready;

    public ReadyPlayerState prev() {
        switch(this) {
            case Selecting:
                return NotPlaying;
            case Ready:
                return Selecting;
            default:
                return NotPlaying;
        }
    }

    public ReadyPlayerState next() {
        switch(this) {
            case NotPlaying:
                return Selecting;
            case Selecting:
                return Ready;
            default:
                return Ready;
        }
    }
}
