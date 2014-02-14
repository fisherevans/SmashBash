package com.fisherevans.wipgame.game.states.play.characters;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.resources.CharacterSprite;
import com.fisherevans.wipgame.resources.Sprites;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class Character {
    public static final float DEFAULT_JUMP_VEL = 12f;
    public static final float DEFAULT_JUMP_MAX_TIME = 0.25f;
    public static final float DEFAULT_X_ACC = 70f;
    public static final float DEFAULT_X_DE_ACC = 4f;
    public static final float DEFAULT_X_ACC_AIR = 30f;
    public static final float DEFAULT_X_MAX_MOVE = 8f;

    public static final float ANIMATIONS_PER_SECOND = 8f;

    private String _name;

    private Rectangle _body;
    private Controller _controller;

    private Map<Integer, CharacterSprite> _characterSprites;
    private Color _color;

    private CharacterState _state, _lastState;
    private CharacterDirection _direction;

    private float _stateLength;

    private Integer _lives, _startHealth, _health;

    private PlayState _playState;

    public Character(PlayState playState, String name,  Color color, Rectangle body, String characterSpriteName, Integer lives, Integer health) {
        _playState = playState;

        _name = name;
        _color = color;
        _body = body;

        _state = CharacterState.IDLE;
        _lastState = _state;
        _direction = CharacterDirection.RIGHT;

        _stateLength = 0f;

        _characterSprites = Sprites.getCharacterSprites(characterSpriteName);

        _lives = lives;
        _startHealth = health;
        _health = _startHealth;
    }

    public void update(float delta) {
        if(_controller != null && !isDead())
            _controller.update(delta);

        if(getState() == _lastState)
            _stateLength += delta;
        _lastState = getState();
    }

    public Image getImage(int size) {
        Image image = null;
        switch(getState()) {
            case FALLING: image = _characterSprites.get(size).getFalling().copy(); break;
            case STRAFING: {
                switch((int)((_stateLength*ANIMATIONS_PER_SECOND)%4f)) {
                    case 0: image = _characterSprites.get(size).getWalking1().copy(); break;
                    case 1: image = _characterSprites.get(size).getWalking2().copy(); break;
                    case 2: image = _characterSprites.get(size).getWalking3().copy(); break;
                    default: image = _characterSprites.get(size).getWalking2().copy(); break;
                }
                break;
            }
            case IDLE: image = _characterSprites.get(size).getIdle().copy(); break;
        }
        if(getDirection() == CharacterDirection.LEFT) {
            image = image.getFlippedCopy(true, false);
        }
        image.setImageColor(_color.r, _color.g, _color.b);
        return image;
    }

    public void damage(Integer damage) {
        _health -= damage;
        if(_health <= 0) {
            _health = 0;
            getBody().setStatic(true);
            _playState.characterDied(this);
        }
    }

    public void kill() {
        damage(_health);
    }

    public Rectangle getBody() {
        return _body;
    }

    public Controller getController() {
        return _controller;
    }

    public void setController(Controller controller) {
        _controller = controller;
    }

    public CharacterState getState() {
        return _state;
    }

    public void setState(CharacterState state) {
        _state = state;
    }

    public CharacterDirection getDirection() {
        return _direction;
    }

    public void setDirection(CharacterDirection direction) {
        _direction = direction;
    }

    public Integer getLives() {
        return _lives;
    }

    public Integer getHealth() {
        return _health;
    }

    public boolean isDead() {
        return _health <= 0;
    }

    public String getName() {
        return _name;
    }
}
