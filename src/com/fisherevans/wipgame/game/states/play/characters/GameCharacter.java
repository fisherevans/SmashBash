package com.fisherevans.wipgame.game.states.play.characters;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import com.fisherevans.wipgame.game.states.play.GameObject;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.game.states.play.combat_elements.Skill;
import com.fisherevans.wipgame.game.states.play.object_controllers.CharacterController;
import com.fisherevans.wipgame.log.Log;
import com.fisherevans.wipgame.resources.Characters;
import com.fisherevans.wipgame.resources.Sprites;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class GameCharacter extends GameObject {
    public static final float ANIMATIONS_PER_SECOND = 8f;

    public static final Log log = new Log(GameCharacter.class);

    private String _name;

    private CharacterController _controller;

    private Map<Integer, CharacterSprite> _characterSprites;
    private Color _color;

    private CharacterState _state, _lastState;

    private float _stateLength;

    private Integer _lives, _maxHealth, _health;

    private PlayState _playState;

    private CharacterAction _currentAction;

    private float _currentActionDuration;

    private Skill _primarySkill, _secondarySkill;

    private CharacterDefinition _definition;

    public GameCharacter(PlayState playState, String name, Color color, Rectangle body, CharacterDefinition definition) {
        super(body);

        _playState = playState;
        _name = name;
        _color = color;
        _state = CharacterState.IDLE;
        _lastState = _state;
        _stateLength = 0f;
        _definition = definition;
        _characterSprites = _definition.getSprites();

        setDirectionBasedOnVelocity(false);

        _lives = WIP.gameSettings.lives;
        _maxHealth = (int)(WIP.gameSettings.health*_definition.getHealthScale());
        _health = _maxHealth;

        try {
            _primarySkill = (Skill) _definition.getPrimarySkill().getConstructor(GameObject.class).newInstance(this);
        } catch(Exception e) {
            log.error("Failed to load primary skill for: " + definition.getCode());
            log.error(e.toString());
        }
        try {
            _secondarySkill = (Skill) _definition.getSecondarySkill().getConstructor(GameObject.class).newInstance(this);
        } catch(Exception e) {
            log.error("Failed to load secondary skill for: " + definition.getCode());
            log.error(e.toString());
        }
    }

    @Override
    public void updateObject(float delta) {
        if(_primarySkill != null)
            _primarySkill.update(delta);
        if(_secondarySkill != null)
            _secondarySkill.update(delta);

        _currentActionDuration += delta;
        if(_currentAction != null && _currentAction.getDuration() < _currentActionDuration)
            _currentAction = null;

        if(_controller != null && !isDead() && !PlayState.current.isGameOver())
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
                case CROUCHED: image = getSpriteCopy(size, SpriteType.Crouched); break;
            }
        }
        image.setImageColor(_color.r, _color.g, _color.b);
        return image;
    }

    @Override
    public void destroyObject() {

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

    public void adjustHealth(int healthDelta) {
        if(healthDelta < 0)
            damage(-1*healthDelta);
        else
            heal(healthDelta);
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

    public void heal(Integer heal) {
        _health += heal;
        if(_health > _maxHealth)
            _health = _maxHealth;
    }

    public void kill() {
        damage(_health);
    }

    public void revive() {
        _health = _maxHealth;
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

    public Skill getSecondarySkill() {
        return _secondarySkill;
    }

    public void setSecondarySkill(Skill secondarySkill) {
        _secondarySkill = secondarySkill;
    }

    public Skill getPrimarySkill() {
        return _primarySkill;
    }

    public void setPrimarySkill(Skill primarySkill) {
        _primarySkill = primarySkill;
    }

    public Color getColor() {
        return _color;
    }

    public Integer getMaxHealth() {
        return _maxHealth;
    }

    public CharacterDefinition getDefinition() {
        return _definition;
    }
}
