package com.fisherevans.smash_bash.game.states.command;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class CommandString {
    private String _command, _remaining, _lastDel;

    public CommandString(String command, String strip) {
        this(command.substring(strip.length()));
    }

    public CommandString(String command) {
        _command = command.trim();
        _remaining = _command;
        _lastDel = " ";
    }

    public String next() {
        return next(_lastDel);
    }

    public String next(String del) {
        String next = "";
        if(_remaining.contains(del)) {
            int index = _remaining.indexOf(del);
            next = _remaining.substring(0, index);
            _remaining = _remaining.substring(index + del.length());
        } else {
            next = _remaining;
            _remaining = "";
        }
        _lastDel = del;
        return next;
    }

    public String getCommand() {
        return _command;
    }

    public String getRemaining() {
        return _remaining;
    }
}
