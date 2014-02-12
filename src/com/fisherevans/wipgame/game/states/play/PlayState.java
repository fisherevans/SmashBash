package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.World;
import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.wipgame.game.Game;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.characters.CharacterDirection;
import com.fisherevans.wipgame.game.states.play.characters.CharacterState;
import com.fisherevans.wipgame.game.states.play.characters.PlayerController;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fisherevans.wipgame.game.states.play.characters.Character;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class PlayState extends WIPState {
    public static final float DEFAULT_GRAVITY = -30f;

    private TiledMap _map;
    private World _world;
    private List<Character> _characters;
    private Camera _camera;

    private Map<Integer, Image> _ciFalling;
    private Map<Integer, Image> _ciStrafe;

    @Override
    public int getID() {
        return Game.STATE_PLAY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _map = new TiledMap("res/maps/test.tmx");
        _world = new World(DEFAULT_GRAVITY);
        generateCollisionBodies();

        _characters = new LinkedList<>();
        Character c;

        c = new Character(new Rectangle(13f, _map.getHeight()-13f, 1f, 2f), new Color(0.2f, 0.6f, 0.9f));
        c.setController(new PlayerController(c, 0));
        _characters.add(c);

        c = new Character(new Rectangle(40f, _map.getHeight()-13f, 1f, 2f), new Color(0.9f, 0.4f, 0.2f));
        c.setController(new PlayerController(c, 1));
        _characters.add(c);

        for(Character character:_characters) {
            _world.add(character.getBody());
        }

        _camera = new Camera(this, 128f);

        _ciStrafe = new HashMap<>();
        _ciStrafe.put(32, Images.getImage("test/character/base-idle-32"));
        _ciStrafe.put(64, Images.getImage("test/character/base-idle-64"));
        _ciStrafe.put(128, Images.getImage("test/character/base-idle-128"));

        _ciFalling = new HashMap<>();
        _ciFalling.put(32, Images.getImage("test/character/base-falling-32"));
        _ciFalling.put(64, Images.getImage("test/character/base-falling-64"));
        _ciFalling.put(128, Images.getImage("test/character/base-falling-128"));
    }

    private void generateCollisionBodies() {

        int collisionLayerId = _map.getLayerIndex("collision");
        int[][] tiles = new int[_map.getHeight()][_map.getWidth()];
        int tile;
        for(int y = 0;y < _map.getHeight();y++) {
            for(int x = 0;x < _map.getWidth();x++) {
                tiles[y][x] = _map.getTileId(x, y, collisionLayerId);
            }
        }
        for(int y = 0;y < _map.getHeight();y++) {
            float width = 0;
            for(int x = 0;x <= _map.getWidth();x++) {
                tile = x < tiles[y].length ? tiles[y][x] : -1;
                if(tile != 0) {
                    width++;
                } else if(width > 0) {
                    if(width > 1) {
                        _world.add(new Rectangle(x-width, _map.getHeight()-y, width, 1f, true));
                        for(int back = 0;back <= width;back++) {
                            if(x-back < tiles[y].length)
                                tiles[y][x-back] = 0;
                        }
                    }
                    width = 0;
                }
            }
        }
        for(int x = 0;x < _map.getWidth();x++) {
            float height = 0;
            for(int y = 0;y <= _map.getHeight();y++) {
                tile = y < tiles.length ? tiles[y][x] : 0;
                if(tile != 0) {
                    height++;
                } else if(height > 0) {
                    _world.add(new Rectangle(x, _map.getHeight()-y+1, 1f, height, true));
                    for(int back = 0;back <= height;back++) {
                        if(y-back < tiles.length)
                            tiles[y-back][x] = 0;
                    }
                    height = 0;
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setFont(Fonts.getFont(Fonts.HUGE));
        graphics.setColor(Color.white);
        //graphics.drawString("Play", 10, 10);

        float zoom = _camera.getCurrentZoom();

        graphics.drawString(zoom + "", 10, 10);

        float screenWidth = gameContainer.getWidth()/zoom;
        float screenHeight = gameContainer.getHeight()/zoom;

        float defaultCX = screenWidth/2f;
        float defaultCY = _map.getHeight()-(screenHeight/2f);
        int shiftX = (int)(-1*(_camera.getCurrentPosition().getX()-defaultCX)*zoom);
        int shiftY = (int)((_camera.getCurrentPosition().getY()-defaultCY-0.5f)*zoom);

        //_map.render((int)_player.getCenterX()*64, (int)(_map.getHeight()-_player.getCenterY())*64);
        float  mapScale = zoom/_map.getTileHeight();
        graphics.scale(mapScale, mapScale);
        _map.render((int) (shiftX / mapScale), (int) (shiftY / mapScale));
        graphics.scale(1f / mapScale, 1f / mapScale);

        int size = 32;
        if(zoom >= 48)
            size = 64;
        if(zoom >= 96)
            size = 128;

        Image ci;
        float shift;
        Rectangle characterR;
        for(Character character:_characters) {
            graphics.setColor(character.getColor());
            characterR = character.getBody();
            if(character.getState() == CharacterState.FALLING)
                ci = _ciFalling.get(size);
            else
                ci = _ciStrafe.get(size);
            shift = (ci.getWidth()/6f)*(zoom/size);
            if(character.getDirection() == CharacterDirection.LEFT) {
                ci.draw((characterR.getBottomLeft().getX())*zoom+shiftX - shift,
                        (_map.getHeight()-characterR.getBottomLeft().getY()-characterR.getHeight()+1f)*zoom+shiftY - shift,
                        zoom*characterR.getWidth() + shift*2,
                        zoom*characterR.getHeight() + shift*2);
            } else {
                ci.draw((characterR.getBottomLeft().getX())*zoom+shiftX - shift + ci.getWidth()*(zoom/size),
                        (_map.getHeight()-characterR.getBottomLeft().getY()-characterR.getHeight()+1f)*zoom+shiftY - shift,
                        -1*(zoom*characterR.getWidth() + shift*2),
                        zoom*characterR.getHeight() + shift*2);
            }
        }

        /*for(Rectangle r:_world.getRectangles()) {
            if(r.isStatic())
                graphics.setColor(Color.red);
            else
                graphics.setColor(Color.green);

            graphics.drawRect((r.getBottomLeft().getX())*zoom+shiftX,
                    (_map.getHeight()-r.getBottomLeft().getY()-r.getHeight()+1f)*zoom+shiftY,
                    zoom*r.getWidth()-1, zoom*r.getHeight()-1);
        }*/
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        float delta = i/1000f;

        for(Character character:_characters) {
            character.update(delta);
        }

        _world.step(delta);
        _camera.calculateTargetPosition();
        _camera.update(delta);
    }

    public List<Character> getCharacters() {
        return _characters;
    }

    public TiledMap getMap() {
        return _map;
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        for(Character character:_characters) {
            if(character.getController() != null)
                character.getController().keyDown(key, inputSource);
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {
        for(Character character:_characters) {
            if(character.getController() != null)
                character.getController().keyUp(key, inputSource);
        }
    }
}
