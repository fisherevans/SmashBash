package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;

import com.fisherevans.wipgame.Main;
import com.fisherevans.wipgame.game.states.play.characters.Character;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class Camera {
    public static final float DEFAULT_CAMERA_PADDING = 2f*4f;

    private Vector _targetPosition, _currentPosition;
    private float _maxZoom, _targetZoom, _currentZoom;
    private PlayState _playState;

    public Camera(PlayState playState, float maxZoom) {
        _playState = playState;
        _maxZoom = maxZoom;
        calculateTargetPosition();
        _currentPosition = _targetPosition.getCopy();
        _currentZoom = _targetZoom;
    }

    public void update(float delta) {
        Vector positionDelta = _targetPosition.getCopy().subtract(_currentPosition).scale(5f * delta);
        _currentPosition.add(positionDelta);
        float zoomDelta = _targetZoom-_currentZoom;
        _currentZoom += zoomDelta*3f*delta;
    }

    public void calculateTargetPosition() {
        Vector topLeft = null, bottomRight = null;
        boolean first = true;
        float px, py;
        Rectangle characterR = null;
        for(Character character:_playState.getCharacters()) {
            characterR = character.getBody();
            px = characterR.getCenterX();
            py = characterR.getCenterY();
            if(first) {
                topLeft = new Vector(px, py);
                bottomRight = new Vector(px, py);
                first = false;
            } else {
                if(px < topLeft.getX() && px > 0)
                    topLeft.setX(px);
                else if(px > bottomRight.getX() && px < _playState.getMap().getWidth())
                    bottomRight.setX(px);

                if(py < bottomRight.getY() && py > 0)
                    bottomRight.setY(py);
                else if(py > topLeft.getY() && py < _playState.getMap().getHeight())
                    topLeft.setY(py);
            }
        }
        _targetPosition = new Vector((topLeft.getX()+bottomRight.getX())/2f,
                (topLeft.getY()+bottomRight.getY())/2f);

        float xZoom = Main.gameContainer.getWidth()/(bottomRight.getX()-topLeft.getX()+DEFAULT_CAMERA_PADDING);
        float yZoom = Main.gameContainer.getHeight()/(topLeft.getY()-bottomRight.getY()+DEFAULT_CAMERA_PADDING);
        _targetZoom = Math.min(yZoom, xZoom);
        _targetZoom = Math.min(_targetZoom, _maxZoom);
    }

    public Vector getCurrentPosition() {
        return _currentPosition;
    }

    public float getCurrentZoom() {
        return _currentZoom;
    }
}
