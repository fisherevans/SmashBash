package com.fisherevans.wipgame.game.states.play.characters;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.object_controllers.CharacterController;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Sprites;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class Character extends GameObject {
    public static final float DEFAULT_JUMP_VEL = 12f;
    public static final float DEFAULT_JUMP_MAX_TIME = 0.25f;
    public static final float DEFAULT_X_ACC = 70f;
    public static final float DEFAULT_X_DE_ACC = 4f;
    public static final float DEFAULT_X_ACC_AIR = 30f;
    public static final float DEFAULT_X_MAX_MOVE = 8f;

    public static final float ANIMATIONS_PER_SECOND = 8f;

    public static final Log log = new Log(Character.class);

    private String _name;

    private CharacterController _controller;

    private Map<Integer, CharacterSprite> _characterSprites;
    private Color _color;

    private CharacterState _state, _lastState;

    private float _stateLength;

    private Integer _lives, _startHealth, _health;

    private PlayState _playState;

    private CharacterAction _currentAction;

    private float _currentActionDuration;

    public Character(PlayState playState, String name,  Color color, Rectangle body, String characterSpriteName, Integer lives, Integer health) {
        super(body);

        _playState = playState;

        _name = name;
        _color = color;

        _state = CharacterState.IDLE;
        _lastState = _state;

        _stateLength = 0f;

        _characterSprites = Sprites.getCharacterSprites(characterSpriteName);

        _lives = lives;
        _startHealth = health;
        _health = _startHealth;
    }

    @Override
    public void updateObject(float delta) {
        _currentActionDuration += delta;
        if(_currentAction != null && _currentAction.getDuration() < _currentActionDuration)
            _currentAction = null;

        if(_controller != null && !isDead())
            _controller.update(delta);

        if(getState() == _lastState)
            _stateLength += delta;
        _lastState = getState();
    }

    @Override
    public Image getImage(int size) {
        Image image = null;
        if(_currentAction != null) {
            image = getSpriteCopy(size, _currentAction.getSpriteType());
        } else {
            switch(getState()) {
                case FALLING: image = getSpriteCopy(size, SpriteType.Falling); break;
                case STRAFING: {
                    switch((int)((_stateLength*ANIMATIONS_PER_SECOND)%4f)) {
                        case 0: image = getSpriteCopy(size, SpriteType.Walking1); break;
                        case 1: image = getSpriteCopy(size, SpriteType.Walking2); break;
                        case 2: image = getSpriteCopy(size, SpriteType.Walking3); break;
                        default: image = getSpriteCopy(size, SpriteType.Walking2); break;
                    }
                    break;
                }
                case IDLE: image = getSpriteCopy(size, SpriteType.Idle); break;
            }
        }
        image.setImageColor(_color.r, _color.g, _color.b);
        return image;
    }

    public Image getSpriteCopy(int size, SpriteType type) {
        CharacterSprite characterSprite = _characterSprites.get(size);
        if(characterSprite == null)
            return null;
        Image sprite = characterSprite.getSprite(type);
        if(sprite == null)
            return null;
        return sprite.copy();
    }

    public void damage(Integer damage) {
        _health -= damage;
        if(_health <= 0) {
            _health = 0;
            getBody().setStatic(true);
            _lives--;
            _playState.characterDied(this);
        }
    }

    public void kill() {
        damage(_health);
    }

    public void revive() {
        _health = _startHealth;
        getBody().setStatic(false);
        getBody().setVelocity(new Vector(0f, 0f));
        getBody().setAcceleration(new Vector(0f, 0f));
    }

    public boolean acceptInput() {
        if(_currentAction == null)
            return true;
        return !_currentAction.getPreventInput();
    }

    public void setCurrentAction(CharacterAction newAction) {
        _currentAction = newAction;
        _currentActionDuration = 0f;
    }

    public CharacterAction getCurrentAction() {
        return _currentAction;
    }

    public CharacterController getController() {
        return _controller;
    }

    public void setController(CharacterController controller) {
        _controller = controller;
    }

    public CharacterState getState() {
        return _state;
    }

    public void setState(CharacterState state) {
        _state = state;
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
