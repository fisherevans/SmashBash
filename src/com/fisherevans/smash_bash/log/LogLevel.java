package com.fisherevans.smash_bash.log;

/**
 * Author: Fisher Evans
 * Date: 2/16/14
 */
public enum LogLevel {
    Debug(0),
    Info(1),
    Error(2),
    Off(999);

    public final int rank;
    LogLevel(int rank) {
        this.rank = rank;
    }
}
