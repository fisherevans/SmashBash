package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.Color;

import java.io.File;
import java.util.*;

/**
 * Author: Fisher Evans
 * Date: 3/11/14
 */
public class Settings {
    public static final String SETTINGS_FILE = "res/settings.prop";
    public static final String COMMENT_PREFIX = "#";
    public static final Log log = new Log(Settings.class);

    private static Map<String, Setting> _parents;
    private static Setting _rootSetting;

    public static void init() {
        _parents = new HashMap<>();
        _rootSetting = new Setting("root");
        try {
            Scanner in = new Scanner(new File(SETTINGS_FILE));
            String line, type, key, valueString;
            while(in.hasNextLine()) {
                line = in.nextLine().trim();
                if(line.length() == 0 || line.startsWith(COMMENT_PREFIX))
                    continue;
                try {
                    type = line.substring(0, line.indexOf(":")).trim();
                    key = line.substring(line.indexOf(":")+1, line.indexOf("=")).trim();
                    valueString = line.substring(line.indexOf("=")+1).trim();
                    getSetting(key).setValue(generateValue(type, valueString));
                } catch(Exception e) {
                    log.error("Failed to read setting line: " + line);
                    log.error(e.toString());
                }
            }
        } catch(Exception e) {
            log.error("Failed to load settings!");
            log.error(e.toString());
        }
    }

    public static Setting getSetting(String key) {
        key = key.trim();
        if(key == null || key.length() == 0)
            return null;
        Setting setting = _rootSetting;
        for(String name:key.split(" *\\. *"))
            setting = setting.getChild(name);
        return setting;
    }

    private static Object generateValue(String type, String stringValue) throws Exception {
        Object value = null;
        switch(type.toLowerCase()) {
            case "string":
                value = stringValue;
                break;
            case "boolean":
                value = new Boolean(stringValue);
                break;
            case "float":
                value = new Float(stringValue);
                break;
            case "integer":
                value = new Integer(stringValue);
                break;
            case "double":
                value = new Double(stringValue);
                break;
            case "color":
                String[] comps = stringValue.split(",");
                Color color = new Color(new Float(comps[0]), new Float(comps[1]), new Float(comps[2]));
                if(comps.length > 3)
                    color.a = new Float(comps[3]);
                value = color;
                break;
            case "class":
                value = Class.forName(stringValue);
                break;
        }
        return value;
    }

    public static String getString(String key) {
        return getSetting(key).stringValue();
    }

    public static Boolean getBoolean(String key) {
        return getSetting(key).booleanValue();
    }

    public static Integer getInteger(String key) {
        return getSetting(key).integerValue();
    }

    public static Float getFloat(String key) {
        return getSetting(key).floatValue();
    }

    public static Double getDouble(String key) {
        return getSetting(key).doubleValue();
    }

    public static Color getColor(String key) {
        return getSetting(key).colorValue();
    }

    public static Class getClass(String key) {
        return getSetting(key).classValue();
    }

    public static class Setting {
        private String _name;
        private Object _value;
        private Class _clazz;
        private Map<String, Setting> _children;

        public Setting(String name) {
            _name = name;
            _value = null;
            _clazz = Object.class;

            _children = new HashMap<>();
        }

        public Collection<Setting> getChildren() {
            return _children.values();
        }

        public Setting getChild(String name) {
            Setting child =  _children.get(name);
            if(child == null) {
                child = new Setting(name);
                addChild(child);
            }
            return child;
        }

        public void addChild(Setting child) {
            _children.put(child.getName(), child);
        }

        public void removeChild(String name) {
            if(_children.containsKey(name))
                removeChild(_children.get(name));
        }

        public void removeChild(Setting setting) {
            _children.remove(setting);
        }

        public boolean isNull() {
            return _value == null;
        }

        public void setValue(Object value) {
            _value = value;
            _clazz = value.getClass();
        }

        public String getName() {
            return _name;
        }

        public Object getValue() {
            return _value;
        }

        public Class getClazz() {
            return _clazz;
        }

        public String stringValue() {
            return (String)_value;
        }

        public Boolean booleanValue() {
            return (Boolean)_value;
        }

        public Integer integerValue() {
            return (Integer)_value;
        }

        public Float floatValue() {
            return (Float)_value;
        }

        public Double doubleValue() {
            return (Double)_value;
        }

        public Color colorValue() {
            return (Color)_value;
        }

        public Class classValue() {
            return (Class)_value;
        }

        @Override
        public String toString() {
            return getName() + ":" + getValue().toString();
        }
    }
}
