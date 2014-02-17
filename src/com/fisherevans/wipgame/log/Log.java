package com.fisherevans.wipgame.log;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Log {
    public static LogLevel level = LogLevel.Info;

    private Class _clazz;

    public Log(Class clazz) {
        _clazz = clazz;
    }

    public void debug(String message) {
        if(level.rank <= LogLevel.Debug.rank)
            System.out.printf("[Debug - %s] %s\n", _clazz.getSimpleName(), message);
    }

    public void info(String message) {
        if(level.rank <= LogLevel.Info.rank)
            System.out.printf("[Info - %s] %s\n", _clazz.getSimpleName(), message);
    }

    public void error(String message) {
        if(level.rank <= LogLevel.Error.rank)
            System.err.printf("[Error - %s] %s\n", _clazz.getSimpleName(), message);
    }
}
