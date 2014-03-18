package com.fisherevans.smash_bash.game.states.command;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public abstract class CommandObject {
    private String _prefix;

    public CommandObject(String prefix) {
        _prefix = prefix;
    }

    public String getPrefix() {
        return _prefix;
    }

    public final void execute(String commandLine) {
        if(!execute(new CommandString(commandLine, _prefix)))
            printRed("Invalid command arguments. Use 'help " + _prefix + "' to see how to use this command.");
    }

    public abstract boolean execute(CommandString c);

    public abstract void printHelp();

    public void printWhite(String text) {
        CommandState.print(text, CommandState.WHITE);
    }

    public void printGrey(String text) {
        CommandState.print(text, CommandState.GREY);
    }

    public void printGreen(String text) {
        CommandState.print(text, CommandState.GREEN);
    }

    public void printRed(String text) {
        CommandState.print(text, CommandState.RED);
    }

    public void printBlue(String text) {
        CommandState.print(text, CommandState.BLUE);
    }
}
