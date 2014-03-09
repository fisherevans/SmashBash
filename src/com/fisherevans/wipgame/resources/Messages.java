package com.fisherevans.wipgame.resources;

import com.sun.deploy.util.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class Messages {
    private static final String GLOBAL_FILE = "res/messages/global.prop";
    private static Map<String, Map<String, String>> _messageMap;
    private static String _defaultLanguage = "eng";

    public static void load() throws FileNotFoundException {
        _messageMap = new HashMap<>();
        for(final File file:new File("res/messages").listFiles()) {
            if(file.isFile() && file.canRead() && file.getName().matches("messages\\.[a-z]+\\.prop")) {
                loadFile(file);
            }
        }
        loadGlobalFile(new File(GLOBAL_FILE));
    }

    private static void loadFile(File file) throws FileNotFoundException {
        Map<String, String> messages = fetchMessages(file);
        String language = file.getName().replace("messages.", "").replace(".prop", "");
        _messageMap.put(language, messages);
    }

    private static void loadGlobalFile(File file) throws FileNotFoundException {
        Map<String, String> messages = fetchMessages(file);
        for(String key:_messageMap.keySet()) {
            _messageMap.get(key).putAll(messages);
        }
    }

    private static Map<String, String> fetchMessages(File file) throws FileNotFoundException {
        Map<String, String> messages = new HashMap<>();
        Scanner scanner = new Scanner(file);
        String line[];
        String key, property;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine().split("=");
            key = line[0];
            property = "";
            for(int i = 1;i < line.length;i++)
                property += line[i] + (i == line.length-1 ? "" : "=");
            messages.put(key, property);
        }
        return messages;
    }

    public static String get(String key) {
        Map<String, String> messages = _messageMap.get(_defaultLanguage);
        if(messages == null || messages.get(key) == null)
            return key;
        return messages.get(key);
    }

    public static boolean exists(String key) {
        return _messageMap.get(_defaultLanguage).containsKey(key);
    }
}
