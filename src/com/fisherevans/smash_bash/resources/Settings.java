package com.fisherevans.smash_bash.resources;

import com.fisherevans.smash_bash.log.Log;
import org.newdawn.slick.Color;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Fisher Evans
 * Date: 3/11/14
 */
public class Settings {
    public enum DataType { String, Boolean, Integer, Float, Double, Color, Class }

    public static final String SETTINGS_FILE = "res/settings.prop";
    public static final String COMMENT_PREFIX = "#";
    public static final String VAR_WALLS = "%";
    public static final Pattern VAR_PATTERN = Pattern.compile(VAR_WALLS + "((\\.?[a-zA-Z0-9]+)+)" + VAR_WALLS);
    public static final Log log = new Log(Settings.class);

    private static Setting _rootSetting;

    public static void init() {
        _rootSetting = new Setting("root");
        try {
            Scanner in = new Scanner(new File(SETTINGS_FILE));
            String line, type, key, valueString;
            while(in.hasNextLine()) {
                line = in.nextLine().replaceAll("^ +", "");
                if(line.length() == 0 || line.startsWith(COMMENT_PREFIX))
                    continue;
                try {
                    type = line.substring(0, line.indexOf(":")).trim();
                    key = line.substring(line.indexOf(":")+1, line.indexOf("=")).trim();
                    valueString = replaceVars(line.substring(line.indexOf("=") + 1).replaceAll("^ +", ""));
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
        switch(DataType.valueOf(type)) {
            case String:
                value = stringValue;
                break;
            case Boolean:
                value = new Boolean(stringValue);
                break;
            case Float:
                value = new Float(stringValue);
                break;
            case Integer:
                value = new Integer(stringValue);
                break;
            case Double:
                value = new Double(stringValue);
                break;
            case Color:
                String[] comps = stringValue.split(",");
                Color color = new Color(new Float(comps[0]), new Float(comps[1]), new Float(comps[2]));
                if(comps.length > 3)
                    color.a = new Float(comps[3]);
                value = color;
                break;
            case Class:
                value = Class.forName(stringValue);
                break;
        }
        return value;
    }

    private static String replaceVars(String text) {
        Matcher m = VAR_PATTERN.matcher(text);
        while(m.find()) {
            Setting setting = getSetting(m.group().replaceAll(VAR_WALLS, ""));
            if(!setting.isNull())
                text = text.replaceAll(m.group(), setting.getValue().toString());
        }
        return text;
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

    public static void populate(Setting parent, Object box) {
        Field field;
        for(Settings.Setting setting:parent.getChildren()) {
            try {
                field = box.getClass().getField(setting.getName());
                field.set(box, setting.getValue());
            } catch (Exception e) {
                log.error("Failed populating property: " + setting.getName());
                log.error(e.toString());
            }
        }
    }

    public static void replaceNulls(Object base, Object into) {
        for(Field field:base.getClass().getFields()) {
            try {
                if(field.get(into) == null)
                    field.set(into, field.get(base));
            } catch (Exception e) {
                log.error("Failed updating null property from base: " + field.getName());
                log.error(e.toString());
            }
        }
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

            _children = new HashMap<String, Setting>();
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
