package com.fisherevans.wipgame.log;

import com.fisherevans.wipgame.game.states.command.CommandState;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Log {
    private static LogLevel defaultLevel = LogLevel.Info;
    public static Map<Class, LogLevel> levels;
    public static PrintWriter writer = null;
    private static PrintStreamSplitter _stdOut, _stdErr;
    public static boolean printStdOut = false;

    static {
        levels = new HashMap<>();
    }

    private Class _clazz;

    public Log(Class clazz) {
        _clazz = clazz;
        if(!levels.containsKey(clazz))
            levels.put(clazz, defaultLevel);
    }

    public void debug(String message) {
        if(levels.get(_clazz).rank <= LogLevel.Debug.rank)
            log(String.format("[Debug] %s: %s", _clazz.getSimpleName(), message));
    }

    public void info(String message) {
        if(levels.get(_clazz).rank <= LogLevel.Info.rank)
            log(String.format("[Info] %s: %s", _clazz.getSimpleName(), message));
    }

    public void error(String message) {
        if(levels.get(_clazz).rank <= LogLevel.Error.rank)
            log(String.format("[Error] %s: %s", _clazz.getSimpleName(), message));
    }

    private void log(String line) {
        if(printStdOut)
            System.out.println(line);
        if(_clazz != CommandState.class)
            CommandState.logPrint(line);
        if(writer != null) {
            writer.println(line);
            writer.flush();
        }
    }

    public void setLevel(LogLevel level) {
        setLevel(_clazz, level);
    }

    public static void setLevel(Class clazz, LogLevel level) {
        levels.put(clazz, level);
    }

    public static void setAllLevel(LogLevel level) {
        for(Class key:levels.keySet())
            levels.put(key, level);
        defaultLevel = level;
    }

    public static Class getClass(String name) {
        for(Class clazz:levels.keySet())
            if(clazz.getSimpleName().toLowerCase().equals(name))
                return clazz;
        return null;
    }

    public static void open() {
        if(writer != null)
            close();
        try {
            writer = new PrintWriter("log.txt", "UTF-8");
            writer.println("LOG START -> " + new Date().toString());
            writer.flush();
            
            stdOut = new PrintStreamSplitter(System.out, writer);
            stdErr = new PrintStreamSplitter(System.err, writer);
            System.setOut(stdOut);
            System.setErr(stdErr);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void close() {
        if(writer == null)
            return;
        try {
            writer.println("LOG END -> " + new Date().toString());
            writer.flush();
            writer.close();
            writer = null;
            
            System.setOut(stdOut.getOriginal());
            System.setErr(stdErr.getOriginal());
            stdOut = null;
            stdErr = null
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
