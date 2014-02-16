package com.fisherevans.wipgame.game.states.play.lights;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.resources.Lights;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/14/14
 */
public class LightManager {
    private PlayState _playState;

    private Color _baseColor;
    private Image _lightFBO = null;
    private Graphics _gfx = null;

    private List<Light> _lights;

    public LightManager(PlayState playState, Color baseColor, int width, int height) {
        _playState = playState;
        _baseColor = baseColor;

        try {
            _lightFBO = new Image(width, height);
            _gfx = _lightFBO.getGraphics();
        } catch (SlickException e) {
            e.printStackTrace();
            System.exit(1);
        }

        _lights = new LinkedList<>();
        TiledMap map = playState.getBaseMap();
        int lightsLayer = map.getLayerIndex("lights");
        int[] tileData;
        LightSettings lightSettings;
        for(int y = 0;y < map.getHeight();y++) {
            for(int x = 0;x < map.getWidth();x++) {
                tileData = map.getTileData(x, map.getHeight()-y-1, lightsLayer);
                if(tileData[0] >= 0 && tileData[1] >= 0) {
                    lightSettings = Lights.getLightSettings(tileData[1]);
                    _lights.add(new Light(lightSettings,
                            new Vector(x + 0.5f, (map.getHeight()-y) + 0.5f)));
                }
            }
        }
    }

    public void update(float delta) {
        for(Light lightEntity:_lights) {
            lightEntity.getLightController().update(delta);
        }
    }

    public void render(Graphics gameGraphics, float zoom, float screenWidth, float screenHeight, int startX, int startY, float shiftX, float shiftY) {
        Graphics.setCurrent(_gfx);
        _gfx.clear();

        _gfx.setColor(_baseColor);
        _gfx.fillRect(0, 0, _lightFBO.getWidth(), _lightFBO.getHeight());

        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        float lx, ly, radius;
        Image lightImage;
        for(Light light:_lights) {
            lx = light.getPosition().getX();
            ly = light.getPosition().getY();
            radius = light.getRadius();
            if(lx + radius > startX && lx - radius < startX + screenWidth
                    && ly + radius > startY - screenHeight && ly - radius < startY) {
                lightImage = light.getImage();
                lightImage.draw((lx-radius)*zoom + shiftX, (ly-radius)*zoom + shiftY, radius*2*zoom, radius*2*zoom, light.getColor());
            }
        }
        _gfx.setDrawMode(Graphics.MODE_NORMAL);

        _gfx.flush();
        GL14.glBlendFuncSeparate(GL11.GL_DST_COLOR, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ONE);
        gameGraphics.drawImage(_lightFBO, 0, 0);
        gameGraphics.setDrawMode(Graphics.MODE_NORMAL);
        Graphics.setCurrent(gameGraphics);
    }
}
