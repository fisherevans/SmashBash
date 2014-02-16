package com.fisherevans.wipgame.log;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Log {
    public static LogLevel level = LogLevel.Info;

    private Class _clazz;

    public Log(Class clazz) {
        _clazz = _clazz;
    }

    public Log(Object object) {
        _clazz = object.getClass();
    }

    public void debug(String message) {
        System.out.printf("[Debug - %s] %s", _clazz.getSimpleName(), message);
    }

    public void info(String message) {
        System.out.printf("[Info - %s] %s", _clazz.getSimpleName(), message);
    }

    public void error(String message) {
        System.err.printf("[Error - %s] %s", _clazz.getSimpleName(), message);
    }
}
