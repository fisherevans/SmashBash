package com.fisherevans.wipgame.game.states.play.characters;

import com.fisherevans.fizzics.components.Rectangle;
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

    private Rectangle _body;
    private Controller _controller;

    private Map<Integer, CharacterSprite> _characterSprites;

    private CharacterState _state;
    private CharacterDirection _direction;

    public Character(Rectangle body, String characterSpriteName) {
        _body = body;

        _state = CharacterState.IDLE;
        _direction = CharacterDirection.RIGHT;

        _characterSprites = Sprites.getCharacterSprites(characterSpriteName);
    }

    public void update(float delta) {
        if(_controller != null)
            _controller.update(delta);
    }

    public Image getImage(int size) {
        Image image = null;
        switch(getState()) {
            case FALLING: image = _characterSprites.get(size).getFalling(); break;
            case STRAFING: image = _characterSprites.get(size).getWalking1(); break;
            case IDLE: image = _characterSprites.get(size).getWalking2(); break;
        }
        if(getDirection() == CharacterDirection.LEFT) {
            image = image.getFlippedCopy(true, false);
        }
        return image;
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
}
