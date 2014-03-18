package com.fisherevans.wipgame.game.states.command;

import com.fisherevans.wipgame.game.OverlayState;
import com.fisherevans.wipgame.game.TypingState;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.command.commandObjects.*;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Fisher Evans
 * Date: 3/8/14
 */
public class CommandState extends OverlayState implements TypingState {
    public static final float flashSpeed = 0.5f;

    public static final Color WHITE = Color.white;
    public static final Color GREY = Color.lightGray;
    public static final Color RED = new Color(1f, 0.5f, 0.5f);
    public static final Color GREEN = new Color(0.5f, 1f, 0.5f);
    public static final Color BLUE = new Color(0.5f, 0.5f, 1f);
    public static final Color YELLOW = new Color(1f, 1f, 0.5f);

    public static Log log = new Log(CommandState.class);

    private static List<ConsoleText> _console = new LinkedList<ConsoleText>();
    private static String _inputLine = "";
    public static boolean acceptInput = false;

    private static Map<String, Object> _variables = new HashMap<String, Object>();
    private static Map<String, CommandObject> _commands = new HashMap<String, CommandObject>();
    private static List<String> _history = new ArrayList<String>();

    private static int _cursor = 0;
    private static int _currentHistory = 0;

    private UnicodeFont font;
    private static float flashTime = 0;
    private static boolean flash = false;

    static {
        addCommand(new Echo());
        addCommand(new VariableSet());
        addCommand(new VariableDelete());
        addCommand(new VariablePrint());
        addCommand(new Clear());
        addCommand(new WhatIs());
        addCommand(new Exit());
        addCommand(new Event());
        addCommand(new LogStdOut());
        addCommand(new LogCommand());
        addCommand(new LogAllCommand());
    }

    public CommandState(WIPState overlayedState) {
        super(overlayedState);
        try {
            font = new UnicodeFont("/res/fonts/mono.ttf", 14, false, false);
            font.addAsciiGlyphs();
            font.addGlyphs(400, 600);
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();
        } catch(Exception e) {
            System.out.println("failed to load console font");
            e.printStackTrace();
            System.exit(1);
        }
        print("Use 'help' to view all available commands.", GREY);
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {
        acceptInput = true;
        while(_console.size()*font.getLineHeight() > WIP.height())
            _console.remove(0);
    }

    @Override
    public void update(float delta) throws SlickException {
        flashTime += delta;
        if(flashTime >= flashSpeed) {
            flashTime -= flashSpeed;
            flash = !flash;
        }
    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        getOverlayedState().render(graphics);

        graphics.setColor(new Color(0f, 0f, 0f, 0.7f));
        graphics.fillRect(0, 0, WIP.width(), WIP.height());
        graphics.setFont(font);

        ConsoleText text;
        for(int id = _console.size()-1;id >= 0;id--) {
            text = _console.get(id);
            graphics.setColor(text.getColor());
            graphics.drawString(text.getText(), 10, WIP.height() - font.getLineHeight()*(3+_console.size()-id));
        }

        graphics.setColor(Color.white);
        graphics.drawRect(8, WIP.height() - font.getLineHeight()*2 - 2, WIP.width()-16, font.getLineHeight()+4);
        graphics.drawString(_inputLine, 10, WIP.height() - font.getLineHeight()*2);

        if(flash) {
            float x = font.getWidth(_inputLine.substring(0, _cursor));
            graphics.drawLine(10+x, WIP.height() - font.getLineHeight()*2-2, 10+x, WIP.height() - font.getLineHeight()+2);
        }
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
        acceptInput = false;
    }

    @Override
    public int getID() {
        return WIP.STATE_COMMAND;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Menu)
            WIP.enterState(getOverlayedState());
    }

    @Override
    public void keyUp(Key key, int inputSource) {
    }

    @Override
    public void typed(String text) {
        _inputLine = _inputLine.substring(0, _cursor) + text + _inputLine.substring(_cursor);
        _cursor += text.length();
    }

    @Override
    public void keyEnter() {
        processCommand(_inputLine);
        _inputLine = "";
        _cursor = 0;
    }

    @Override
    public void keyBackspace() {
        if(_cursor <= 0)
            return;

        _inputLine = _inputLine.substring(0,_cursor-1) + _inputLine.substring(_cursor);
        _cursor--;
    }

    @Override
    public void keyDelete() {
        if(_cursor >= _inputLine.length())
            return;
        _inputLine = _inputLine.substring(0,_cursor) + _inputLine.substring(_cursor+1);
    }

    @Override
    public void keyArrowLeft() {
        _cursor--;
        if(_cursor < 0)
            _cursor = 0;
        flash = true;
        flashTime = 0;
    }

    @Override
    public void keyArrowRight() {
        _cursor++;
        if(_cursor > _inputLine.length())
            _cursor = _inputLine.length();
        flash = true;
        flashTime = 0;
    }

    @Override
    public void keyArrowUp() {
        if(_currentHistory <= 0)
            return;
        _currentHistory--;
        _inputLine = _history.get(_currentHistory);
        _cursor = _inputLine.length();
    }

    @Override
    public void keyArrowDown() {
        if(_currentHistory >= _history.size())
            return;
        _currentHistory++;
        if(_currentHistory == _history.size())
            _inputLine = "";
        else
            _inputLine = _history.get(_currentHistory);
        _cursor = _inputLine.length();
    }

    public static void processCommand(String commandRaw) {
        commandRaw = commandRaw.trim();
        if(_history.isEmpty() || !commandRaw.equals(_history.get(_history.size()-1)))
            _history.add(commandRaw);
        _currentHistory = _history.size();
        print("> " + commandRaw, WHITE);
        commandRaw = replaceVariables(commandRaw);
        for(CommandObject command:_commands.values()) {
            if(commandRaw.startsWith(command.getPrefix())) {
                command.execute(commandRaw);
                return;
            }
        }
        if(commandRaw.startsWith("help ")) {
            String name = new CommandString(commandRaw, "help ").next();
            CommandObject commandObject = _commands.get(name);
            if(commandObject == null)
                print(name + " is an unknown command. Use 'help' to view all commands.", RED);
            else
                commandObject.printHelp();
        } else if(commandRaw.equals("help")) {
            print("Argument types: [optional] (required)", BLUE);
            print("Use 'help (command)' to see usage information.", BLUE);
            print("Use variables in commands using the notation: <variable_name>", BLUE);
            print("Below are all available commands:", BLUE);
            CommandObject command;
            for(String commandPrefix:new TreeSet<String>(_commands.keySet())) {
                command = _commands.get(commandPrefix);
                if(command != null) {
                    print(commandPrefix, GREY);
                }
            }
        } else
            print("Invalid command! Use 'help' tp view all commands.", RED);
    }

    public static void print(String text, Color color) {
        _console.add(new ConsoleText(text, color));
        log.debug(text);
    }

    public static void logPrint(String text) {
        _console.add(new ConsoleText(text, YELLOW));
    }

    public static String replaceVariables(String text) {
        Pattern p = Pattern.compile("<([^>]+)>");
        Matcher m = p.matcher(text);

        while(m.find()) {
            for(int id = 1;id <= m.groupCount();id++) {
                text = text.replaceAll("<" + m.group(id) + ">", getVariable(m.group(id)));
            }
        }

        return text;
    }

    public static String getVariable(String key) {
        Object value = _variables.get(key);
        return value == null ? "$" + key : value.toString();
    }

    public static Map<String, Object> getVariables() {
        return _variables;
    }

    public static void addCommand(CommandObject command) {
        _commands.put(command.getPrefix(), command);
    }

    public static void clear() {
        _console.clear();
    }

    private static class ConsoleText {
        private String _text;
        private Color _color;

        private ConsoleText(String text) {
            this(text, GREY);
        }

        private ConsoleText(String text, Color color) {
            _text = text;
            _color = color;
        }

        private String getText() {
            return _text;
        }

        private Color getColor() {
            return _color;
        }
    }
}
