package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.states.play.lights.LightSettings;
import com.fisherevans.wipgame.log.Log;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Lights {
    public static Log log = new Log(Lights.class);
    private static final String CONTROLLER_PACKAGE = "com.fisherevans.wipgame.game.states.play.lights.controllers.";
    public static Map<Integer, LightSettings> _lightSettingsById;
    public static Map<String, LightSettings> _lightSettingsByName;

    public static void load() {
        _lightSettingsById = new HashMap<Integer, LightSettings>();
        _lightSettingsByName = new HashMap<String, LightSettings>();
        LightSettings lightSettings;
        for(Settings.Setting setting:Settings.getSetting("lights.define").getChildren()) {
            try {
                lightSettings = new LightSettings(
                        setting.getName(),
                        setting.getChild("id").integerValue(),
                        Images.getImage(setting.getChild("image").stringValue()),
                        setting.getChild("radius").floatValue(),
                        setting.getChild("color").colorValue(),
                        setting.getChild("controller").classValue()
                );
                _lightSettingsById.put(lightSettings.getId(), lightSettings);
                _lightSettingsByName.put(setting.getName(), lightSettings);
            } catch (Exception e) {
                log.error("Failed to load light: " + setting.toString());
                log.error(e.toString());
            }
        }
    }

    public static LightSettings getLightSettings(int lightId) {
        return _lightSettingsById.get(lightId);
    }

    public static LightSettings getLightSettingsByName(String lightName) {
        return _lightSettingsByName.get(lightName);
    }
}
