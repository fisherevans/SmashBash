package com.fisherevans.wipgame.game.states.play.lights;

import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.play.Camera;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.log.Log;
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
    public static final float COLOR_SCALE_SPEED = 8f;

    public static Log log = new Log(LightManager.class);

    private PlayState _playState;

    private Color _baseColor;
    private Image _lightFBO = null;
    private Graphics _gfx = null;

    private List<Light> _lights;

    private float _currentColorScale, _targetColorScale;

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
        TiledMap map = _playState.getBaseMap();
        int lightsLayer = map.getLayerIndex("lights");
        int[] tileData;
        LightSettings lightSettings;
        for(int y = 0;y < map.getHeight();y++) {
            for(int x = 0;x < map.getWidth();x++) {
                tileData = map.getTileData(x, y, lightsLayer);
                if(tileData[0] >= 0 && tileData[1] >= 0) {
                    lightSettings = Lights.getLightSettings(tileData[1]);
                    if(lightSettings != null)
                        _lights.add(new Light(lightSettings,
                            new Vector(x + 0.5f, (map.getHeight()-y) - 0.5f)));
                    else
                        log.error("Failed to load light: " + tileData[1]);
                }
            }
        }
    }

    public void update(float delta) {
        for(Light lightEntity:_lights) {
            lightEntity.getLightController().update(delta);
        }

        _currentColorScale += (_targetColorScale-_currentColorScale)/2f*delta*COLOR_SCALE_SPEED;
    }

    public void render(Graphics gameGraphics, float zoom, Camera camera, float screenWidth, float screenHeight, float shiftX, float shiftY) {
        Graphics.setCurrent(_gfx);
        _gfx.clear();

        float cx = camera.getCurrentPosition().getX();
        float cy = camera.getCurrentPosition().getY();
        float hw = screenWidth/2f;
        float hh = screenHeight/2f;

        _gfx.setColor(_baseColor.scaleCopy(_currentColorScale));
        _gfx.fillRect(0, 0, _lightFBO.getWidth(), _lightFBO.getHeight());

        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); // overlay
        //GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA, GL11.GL_ONE); // overlay with alpha
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE); // additive
        //GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA, GL11.GL_ONE); // overlay with alpha
        //_currentColorScale = 1f;
        float lx, ly, radius;
        float brightest = getHighestComponent(_baseColor), localBrightest;
        Image lightImage;
        for(Light light:_lights) {
            lx = light.getPosition().getX();
            ly = light.getPosition().getY() - 1f;
            radius = light.getRadius();
            if(lx + radius > cx - hw && lx - radius < cx + hw
                && ly + radius > cy - hh && ly - radius < cy + hh) {
                lightImage = light.getImage();
                lightImage.draw((lx-radius)*zoom + shiftX, (_playState.getBaseMap().getHeight()-ly-radius)*zoom + shiftY,
                        radius*2*zoom, radius*2*zoom, light.getColor().scaleCopy(_currentColorScale));
                localBrightest = getHighestComponent(light.getColor());
                if(localBrightest > brightest)
                    brightest = localBrightest;
            }
        }
        //_targetColorScale = (1f/(brightest+getHighestComponent(_baseColor)));
        _targetColorScale = (1f/brightest);
        _gfx.setDrawMode(Graphics.MODE_NORMAL);

        _gfx.flush();
        if(!WIP.debug)
            GL14.glBlendFuncSeparate(GL11.GL_DST_COLOR, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ONE);
        gameGraphics.drawImage(_lightFBO, 0, 0);
        gameGraphics.setDrawMode(Graphics.MODE_NORMAL);
        Graphics.setCurrent(gameGraphics);
    }

    private float getHighestComponent(Color color) {
        float max = 0;
        if(color.r > max)
            max = color.r;
        if(color.g > max)
            max = color.g;
        if(color.b > max)
            max = color.b;
        return max;
    }

    public List<Light> getLights() {
        return _lights;
    }
}
