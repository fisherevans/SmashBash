package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;

import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.tools.MathUtil;

/**
 * Author: Fisher Evans
 * Date: 2/11/14
 */
public class Camera {
    public static final float DEFAULT_CAMERA_PADDING = 2f*3f;
    public static final float DEFAULT_SLIDE_SPEED = 7.5f;
    public static final float DEFAULT_ZOOM_SPEED = 3.5f;
    public static final float DEFAULT_ZOOM_DELTA_SCALE = 0.75f;

    private Vector _targetPosition, _currentPosition;
    private float _maxZoom, _targetZoom, _currentZoom, _minZoom;
    private PlayState _playState;

    public Camera(PlayState playState, float maxZoom, float minZoom) {
        _playState = playState;
        _maxZoom = maxZoom;
        _minZoom = minZoom;
        calculateTargetPosition();
        _currentPosition = _targetPosition.getCopy();
        _currentZoom = _targetZoom;
    }

    public void update(float delta) {
        Vector positionDelta = _targetPosition.getCopy().subtract(_currentPosition).scale(DEFAULT_SLIDE_SPEED * delta);
        _currentPosition.add(positionDelta);
        float zoomDelta = _targetZoom-_currentZoom;
        _currentZoom += zoomDelta*DEFAULT_ZOOM_SPEED*delta;
    }

    public void calculateTargetPosition() {
        Vector topLeft = null, bottomRight = null;
        boolean first = true;
        float px, py;
        Rectangle characterR = null;
        for(GameCharacter character:_playState.getCharacters()) {
            characterR = character.getBody();
            px = MathUtil.clamp(0, characterR.getCenterX(), _playState.getBaseMap().getWidth());
            py = MathUtil.clamp(0, characterR.getCenterY(), _playState.getBaseMap().getHeight());
            if(first) {
                topLeft = new Vector(px, py);
                bottomRight = new Vector(px, py);
                first = false;
            } else {
                if(px < topLeft.getX())
                    topLeft.setX(px);
                else if(px > bottomRight.getX())
                    bottomRight.setX(px);

                if(py < bottomRight.getY())
                    bottomRight.setY(py);
                else if(py > topLeft.getY())
                    topLeft.setY(py);
            }
        }
        _targetPosition = new Vector((topLeft.getX()+bottomRight.getX())/2f,
                (topLeft.getY()+bottomRight.getY())/2f);

        float xZoom = WIP.container.getWidth()/(bottomRight.getX()-topLeft.getX()+DEFAULT_CAMERA_PADDING);
        float yZoom = WIP.container.getHeight()/(topLeft.getY()-bottomRight.getY()+DEFAULT_CAMERA_PADDING);
        _targetZoom = Math.min(yZoom, xZoom);
        _targetZoom *= DEFAULT_ZOOM_DELTA_SCALE;
        _targetZoom = Math.min(_targetZoom, _maxZoom);
        _targetZoom = Math.max(_targetZoom, _minZoom);
    }

    public Vector getCurrentPosition() {
        return _currentPosition;
    }

    public float getCurrentZoom() {
        return _currentZoom;
    }

    public float getMaxZoom() {
        return _maxZoom;
    }

    public float getMinZoom() {
        return _minZoom;
    }
}
