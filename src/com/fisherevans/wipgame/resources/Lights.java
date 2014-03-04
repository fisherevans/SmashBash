package com.fisherevans.wipgame.resources;

import com.fisherevans.wipgame.game.states.play.lights.LightSettings;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class Lights {
    private static final String CONTROLLER_PACKAGE = "com.fisherevans.wipgame.game.states.play.lights.controllers.";
    public static Map<Integer, LightSettings> _lightSettings;
    public static Map<String, LightSettings> _lightSettingsByName;

    public static void load() {
        _lightSettings = new HashMap<>();
        String lightImage, lightSize, lightColor, controllerClass, name;
        String[] lightColorComponents;
        LightSettings settings;
        for(int lightId = 0;true;lightId++) {
            try {
                lightImage = Messages.get("light." + lightId + ".image");
                lightSize = Messages.get("light." + lightId + ".radius");
                lightColor = Messages.get("light." + lightId + ".color");
                controllerClass = Messages.get("light." + lightId + ".controller");
                name = Messages.get("light." + lightId + ".name");
                lightColorComponents = lightColor.split(",");
                settings = new LightSettings(
                        Images.getImage(lightImage),
                        Float.parseFloat(lightSize),
                        new Color(
                                Integer.parseInt(lightColorComponents[0]),
                                Integer.parseInt(lightColorComponents[1]),
                                Integer.parseInt(lightColorComponents[2])
                        ),
                        Class.forName(CONTROLLER_PACKAGE + controllerClass),
                        name);
                _lightSettings.put(lightId, settings);
                _lightSettingsByName.put(name, settings);
            } catch (Exception e) {
                break;
            }
        }
    }

    public static LightSettings getLightSettings(int lightId) {
        return _lightSettings.get(lightId);
    }

    public static LightSettings getLightSettingsByName(String lightName) {
        return _lightSettingsByName.get(lightName);
    }
}
