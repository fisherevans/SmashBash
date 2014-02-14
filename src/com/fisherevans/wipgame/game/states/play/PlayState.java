package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.World;
import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.wipgame.Main;
import com.fisherevans.wipgame.game.Game;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.characters.PlayerController;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.resources.Sprites;
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
    public static final float DEFAULT_GRAVITY = -37f;

    private Image _gfxImage;
    private Graphics _gfx;

    private TiledMap _map;
    private World _world;
    private List<Character> _characters;
    private Camera _camera;

    private float _screenHeight, _screenWidth;

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

        c = new Character(this, "WASD", Color.orange, new Rectangle(10f, _map.getHeight()-13f, 1f, 2f), "base", 10, 100);
        c.setController(new PlayerController(c, 0));
        _characters.add(c);

        c = new Character(this, "Arrows", Color.cyan, new Rectangle(43f, _map.getHeight()-13f, 1f, 2f), "base", 10, 100);
        c.setController(new PlayerController(c, 1));
        _characters.add(c);

        for(Character character:_characters) {
            character.getBody().setResolveWithStaticOnly(true);
            _world.add(character.getBody());
        }

        _camera = new Camera(this, 128f, 16f);

        _gfxImage = new Image(gameContainer.getWidth(), gameContainer.getHeight());
        _gfx = _gfxImage.getGraphics();
    }

    private void generateCollisionBodies() {

        int collisionLayerId = _map.getLayerIndex("collision");
        int[][] tiles = new int[_map.getHeight()][_map.getWidth()];
        int tile;
        for(int y = 0;y < _map.getHeight();y++) {
            for(int x = 0;x < _map.getWidth();x++) {
                tiles[y][x] = _map.getTileId(x, _map.getHeight()-y-1, collisionLayerId);
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
                        _world.add(new Rectangle(x-width, y, width, 1f, true));
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
                    _world.add(new Rectangle(x, y-height, 1f, height, true));
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
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics fboGraphics) throws SlickException {
        Graphics.setCurrent(_gfx);
        _gfx.clear();
        _gfx.setColor(Color.darkGray);
        _gfx.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
        _gfx.setFont(Fonts.getFont(Fonts.HUGE));
        _gfx.setColor(Color.white);

        float zoom = _camera.getCurrentZoom();

        _screenWidth = gameContainer.getWidth()/zoom;
        _screenHeight = gameContainer.getHeight()/zoom;

        float defaultCX = _screenWidth/2f;
        float defaultCY = _map.getHeight()-(_screenHeight/2f);
        float shiftX = -1*(_camera.getCurrentPosition().getX()-defaultCX)*zoom;
        float shiftY = (_camera.getCurrentPosition().getY()-defaultCY-0.5f)*zoom;
        int startX = (int)(_camera.getCurrentPosition().getX()-_screenWidth/2f);
        int startY = (int)(_camera.getCurrentPosition().getY()+_screenHeight/2f);

        Image bs = Images.getImage("backgrounds/backsplash/test");
        float  bsScale = ((float)(Math.max(gameContainer.getHeight(), gameContainer.getWidth())))/((float)(bs.getWidth()));
        bsScale *= zoom/_camera.getMinZoom()/4f > 1f ? zoom/_camera.getMinZoom()/4f : 1f;
        //graphics.scale(bsScale, bsScale);
        //bs.drawCentered(gameContainer.getWidth()/2f/bsScale, gameContainer.getHeight()/2f/bsScale);
        //graphics.scale(1f / bsScale, 1f / bsScale);

        //_map.render((int)_player.getCenterX()*64, (int)(_map.getHeight()-_player.getCenterY())*64);

        drawMapLayer(zoom, startX, startY, shiftX, shiftY, _map.getLayerIndex("collision"));

        int size = Sprites.CHARACTER_SPRITE_SIZES[0];
        for(int sizeId = 1;sizeId < Sprites.CHARACTER_SPRITE_SIZES.length;sizeId++) {
            if(zoom >= Sprites.CHARACTER_SPRITE_SIZES[sizeId-1]) size = Sprites.CHARACTER_SPRITE_SIZES[sizeId];
        }

        Image ci;
        float padding;
        Rectangle characterR;
        for(Character character:_characters) {
            characterR = character.getBody();
            ci = character.getImage(size);
            padding = (size*Sprites.PADDING_PERCENTAGE)*(zoom/size);
            ci.draw((characterR.getBottomLeft().getX())*zoom+shiftX - padding,
                    (_map.getHeight()-characterR.getBottomLeft().getY()-characterR.getHeight()+1f)*zoom+shiftY - padding,
                    zoom*characterR.getWidth() + padding*2,
                    zoom*characterR.getHeight() + padding*2);
        }

        if(Main.DEBUG) {
            for(Rectangle r:_world.getRectangles()) {
                if(r.isStatic())
                    _gfx.setColor(Color.red);
                else
                    _gfx.setColor(Color.green);

                _gfx.drawRect((r.getBottomLeft().getX())*zoom+shiftX,
                        (_map.getHeight()-r.getBottomLeft().getY()-r.getHeight()+1f)*zoom+shiftY,
                        zoom*r.getWidth()-1, zoom*r.getHeight()-1);
            }

            _gfx.setColor(Color.lightGray);
            _gfx.setFont(Fonts.getFont(Fonts.SMALL));
            Character c;
            for(int i = 0;i < _characters.size();i++) {
                c = _characters.get(i);
                _gfx.drawString("Player " + c.getName(), 300, 10 + 40*i);
                _gfx.drawString("Lives " + c.getLives(), 460, 10 + 40*i);
                _gfx.drawString("Health " + c.getHealth(), 560, 10 + 40*i);
            }

            _gfx.drawString("Zoom: " + zoom, 10, 10);
            _gfx.drawString("Res: " + size , 10, 30);
            _gfx.drawString("Start: " + startX + ", " + startY, 10, 60);
            _gfx.drawString("Cam  : " + (int)_camera.getCurrentPosition().getX() + ", " + (int)_camera.getCurrentPosition().getY(), 10, 80);
        }
        _gfx.flush();
        fboGraphics.drawImage(_gfxImage, 0, 0);
        Graphics.setCurrent(fboGraphics);
    }

    private void drawMapLayer(float zoom, int startX, int startY, float shiftX, float shiftY, int layerId)
    {
        // NORMALIZE SHIFT TO AVOID TEARING
        //xShift = GFX.filterDrawPosition(xShift, DisplayManager.getBackgroundScale());
        //yShift = GFX.filterDrawPosition(yShift, DisplayManager.getBackgroundScale());

        Image tile;
        for(int y = startY+1;y >= startY-_screenHeight-1;y--)
        {
            for(int x = startX;x <= startX+_screenWidth+1;x++) // FOR EACH TIME ON THE SCREEN
            {
                if(x > 0 && x < _map.getWidth()
                        && y > 0 && y < _map.getHeight()) {
                    tile = _map.getTileImage(x, (_map.getHeight()-y-1), layerId); // GET THE TILE FOR THAT LAYER | v- IF IT'S NOT NULL AND IT'S IN THE RENDER DISTANCE
                    if(tile != null)
                        tile.draw(shiftX + x*zoom, shiftY + (_map.getHeight()-y)*zoom, zoom, zoom);
                    //GFX.drawImageCentered(x*TILE_SIZE + xShift, y*TILE_SIZE + yShift, tile); // DRAW THE TILE WITH THE X AND Y SHIFT
                }
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        i = Math.min(i, 100);
        float delta = i/1000f;

        for(Character character:_characters) {
            character.update(delta);
            if(character.getBody().getCenterY() <= -_map.getHeight())
                character.kill();
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

    public void characterDied(Character character) {

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
