package com.fisherevans.wipgame.game.states.play.entities;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.game.states.play.lights.Light;
import com.fisherevans.wipgame.game.states.play.lights.LightSettings;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Lights;
import com.fisherevans.wipgame.resources.Sprites;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/28/14
 */
public class Entity extends GameObject {
    public static final float DEFAULT_WIDTH = 0.1f;
    public static final float DEFAULT_HEIGHT = 0.1f;

    public static Log log = new Log(Entity.class);

    private Map<Integer, EntitySprite> _entitySprites;
    private float _lifeTime, _lifeSpan;
    private boolean _dead = false;
    private Color _color;
    private float _spriteScale = 1f;
    private Light _light = null;

    public Entity(Rectangle body, String entitySpriteName) {
        this(body, entitySpriteName, 0f, Color.white);
    }

    public Entity(Rectangle body, String entitySpriteName, float lifeSpan) {
        this(body, entitySpriteName, lifeSpan, Color.white);
    }

    public Entity(Rectangle body, String entitySpriteName, float lifeSpan, Color color) {
        super(body);
        _entitySprites = Sprites.getEntitySprites(entitySpriteName);
        if(lifeSpan == 0) {
            _lifeSpan = _entitySprites.get(Config.SIZES[0]).getTotalAnimationTime();
        } else
            _lifeSpan = lifeSpan;
        _lifeTime = 0;
        _color = color;
    }

    public void attachNewLight(String lightName) {
        attachNewLight(Lights.getLightSettingsByName(lightName));
    }

    public void attachNewLight(LightSettings lightSettings) {
        if(lightSettings == null)
            log.error("Failed to create light in " + this.getClass().getName());
        else
            attachLight(new Light(lightSettings, new Vector(getBody().getCenterX(), getBody().getCenterY())));
    }

    public void attachLight(Light light) {
        detachLight();
        _light = light;
        PlayState.current.getLightManager().getLights().add(light);
    }

    public void detachLight() {
        if(_light != null) {
            PlayState.current.getLightManager().getLights().remove(_light);
        }
    }

    @Override
    public void updateObject(float delta) {
        _lifeTime += delta;
        if(_lifeSpan > 0 &&  _lifeTime >= _lifeSpan && !_dead) {
            _dead = true;
            PlayState.current.removeGameObject(this);
            destroy();
        }
        if(!_dead) {
            if(_light != null)
                _light.setPosition(new Vector(getBody().getCenterX(), getBody().getCenterY()));
            updateEntity(delta);
        }
    }

    public void updateEntity(float delta) {

    }

    public final void destroy() {
        PlayState.current.removeGameObject(this);
        detachLight();
        destroyEntity();
    }

    public void destroyEntity() {

    }

    @Override
    public Image getImage(int size) {
        EntitySprite sprite = _entitySprites.get(size);
        Image image = sprite.getSprite(_lifeTime).getScaledCopy(_spriteScale);
        image.setImageColor(_color.r, _color.g, _color.b);
        return image;
    }

    public static Rectangle getCenteredBody(float centerX, float centerY) {
        return getCenteredBody(centerX, centerY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static Rectangle getCenteredBody(float centerX, float centerY, float width, float height) {
        Rectangle body = new Rectangle(centerX + width/2f, centerY + height/2f, width, height);
        return body;
    }

    public float getLifeSpan() {
        return _lifeSpan;
    }

    public void setLifeSpan(float lifeSpan) {
        _lifeSpan = lifeSpan;
    }

    public boolean isDead() {
        return _dead;
    }

    public void setDead(boolean dead) {
        _dead = dead;
    }

    public float getSpriteScale() {
        return _spriteScale;
    }

    public void setSpriteScale(float spriteScale) {
        _spriteScale = spriteScale;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }
}
