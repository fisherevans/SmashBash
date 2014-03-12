package com.fisherevans.wipgame.game.states.play;

import com.fisherevans.fizzics.World;
import com.fisherevans.fizzics.components.Rectangle;
import com.fisherevans.fizzics.components.Vector;
import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.PlayerProfile;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.pause.PauseState;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.combat_elements.AreaEffect;
import com.fisherevans.wipgame.game.states.play.combat_elements.skills.BombSkill;
import com.fisherevans.wipgame.game.states.play.combat_elements.skills.CrazyLaserSkill;
import com.fisherevans.wipgame.game.states.play.combat_elements.skills.LaserSkill;
import com.fisherevans.wipgame.game.states.play.entities.Entity;
import com.fisherevans.wipgame.game.states.play.object_controllers.PlayerController;
import com.fisherevans.wipgame.game.states.play.lights.Light;
import com.fisherevans.wipgame.game.states.play.lights.LightManager;
import com.fisherevans.wipgame.game.states.results.ResultsState;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.input.XBoxController;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Images;
import com.fisherevans.wipgame.resources.Maps;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class PlayState extends WIPState {
    public static final float DEFAULT_GRAVITY = -37f;
    public static final int DEFAULT_LIGHT_R = 255;
    public static final int DEFAULT_LIGHT_G = 255;
    public static final int DEFAULT_LIGHT_B = 255;
    public static final String DEFAULT_BACKGROUND = "background/test";

    public static PlayState current = null;

    private World _world;
    private List<Vector> _respawnPoints;
    private List<GameCharacter> _characters, _deadCharacters;
    private List<GameObject> _gameObjects, _gameObjectsToAdd, _gameObjectsToRemove;
    private List<AreaEffect> _areaEffects;
    private Camera _camera;

    private float _screenHeight, _screenWidth;

    private TiledMap _baseMap;

    private float _gravity;
    private Color _lightColor;
    private Image _backgroundImage;

    private LightManager _lightManager;

    private float _timeLeft;

    private boolean _gameOver = false;

    @Override
    public int getID() {
        return WIP.STATE_PLAY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _baseMap = Maps.getSizedMap(WIP.gameSettings.map, Maps.BASE);

        _gravity = Float.parseFloat(_baseMap.getMapProperty("gravity", "" + DEFAULT_GRAVITY));
        _lightColor = new Color(Integer.parseInt(_baseMap.getMapProperty("light_r", "" + DEFAULT_LIGHT_R)),
                Integer.parseInt(_baseMap.getMapProperty("light_g", "" + DEFAULT_LIGHT_B)),
                Integer.parseInt(_baseMap.getMapProperty("light_b", "" + DEFAULT_LIGHT_G)));
        _backgroundImage = Images.getImage(_baseMap.getMapProperty("background", DEFAULT_BACKGROUND));

        _world = new World(_gravity);
        generateCollisionBodies();


        _gameObjects = new LinkedList<>();
        _gameObjectsToAdd = new LinkedList<>();
        _gameObjectsToRemove = new LinkedList<>();
        _deadCharacters = new LinkedList<>();
        _characters = new LinkedList<>();
        GameCharacter c;
        boolean weapon = true;
        for(PlayerProfile profile:WIP.gameSettings.players) {
            c = new GameCharacter(this, "Player " + profile.getInput(), profile.getColor(), new Rectangle(19f + profile.getInput(), _baseMap.getHeight()-9f, 1f, 2f),
                    profile.getCharacterDefinition().getName(), WIP.gameSettings.lives, WIP.gameSettings.health);
            c.setController(new PlayerController(c, profile.getInput()));
            c.setPrimarySkill(new LaserSkill(c));
            if(weapon)
                c.setSecondarySkill(new CrazyLaserSkill(c));
            else
                c.setSecondarySkill(new BombSkill(c));
            weapon =! weapon;
            _characters.add(c);
            _gameObjects.add(c);
        }

        for(GameObject gameObject:_gameObjects) {
            gameObject.getBody().setResolveWithStaticOnly(true);
            _world.add(gameObject.getBody());
        }

        _camera = new Camera(this, 128f, 16f);

        _lightManager = new LightManager(this, _lightColor, gameContainer.getWidth(), gameContainer.getHeight());

        _timeLeft = WIP.gameSettings.time*60;

        _areaEffects = new LinkedList<>();
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {
        current = this;
        XBoxController.useUDAnalog = false;
    }

    private void generateCollisionBodies() {

        int collisionLayerId = _baseMap.getLayerIndex("collision");
        int[][] tiles = new int[_baseMap.getHeight()][_baseMap.getWidth()];
        int tile;
        for(int y = 0;y < _baseMap.getHeight();y++) {
            for(int x = 0;x < _baseMap.getWidth();x++) {
                tiles[y][x] = _baseMap.getTileId(x, _baseMap.getHeight()-y-1, collisionLayerId);
                //System.out.print(tiles[y][x] + ", ");
            }
            //System.out.println();
        }
        Rectangle r;
        float friction = 0f;
        for(int y = 0;y < _baseMap.getHeight();y++) {
            float width = 0;
            for(int x = 0;x <= _baseMap.getWidth();x++) {
                tile = x < tiles[y].length ? tiles[y][x] : -1;
                if(tile == 1) {
                    width++;
                } else if(width > 0) {
                    if(width > 1) {
                        r = new Rectangle(x-width, y, width, 1f, true);
                        r.setFriction(friction);
                        _world.add(r);
                        for(int back = 0;back <= width;back++) {
                            if(x-back < tiles[y].length)
                                tiles[y][x-back] = 0;
                        }
                    }
                    width = 0;
                }
            }
        }
        for(int x = 0;x < _baseMap.getWidth();x++) {
            float height = 0;
            for(int y = 0;y <= _baseMap.getHeight();y++) {
                tile = y < tiles.length ? tiles[y][x] : 0;
                if(tile == 1) {
                    height++;
                } else if(height > 0) {
                    r = new Rectangle(x, y-height, 1f, height, true);
                    r.setFriction(friction);
                    _world.add(r);
                    for(int back = 0;back <= height;back++) {
                        if(y-back < tiles.length)
                            tiles[y-back][x] = 0;
                    }
                    height = 0;
                }
            }
        }
        _respawnPoints = new LinkedList<>();
        for(int x = 0;x < _baseMap.getWidth();x++) {
            for(int y = 0;y <= _baseMap.getHeight();y++) {
                tile = y < tiles.length ? tiles[y][x] : 0;
                if(tile == 2) {
                    _respawnPoints.add(new Vector(x, y));
                }
            }
        }
    }

    @Override
    public void render(Graphics gfx) throws SlickException {
        Graphics.setCurrent(gfx);
        gfx.clear();
        gfx.setColor(Color.darkGray);
        gfx.fillRect(0, 0, WIP.container.getWidth(), WIP.container.getHeight());
        gfx.setFont(Fonts.getFont(Fonts.HUGE));
        gfx.setColor(Color.white);

        float zoom = _camera.getCurrentZoom();

        int size = Config.SIZES[0];
        for(int sizeId = 1;sizeId < Config.SIZES.length;sizeId++) {
            if(zoom >= Config.SIZES[sizeId-1]) size = Config.SIZES[sizeId];
        }

        _screenWidth = WIP.container.getWidth()/zoom;
        _screenHeight = WIP.container.getHeight()/zoom;

        float defaultCX = _screenWidth/2f;
        float defaultCY = _baseMap.getHeight()-(_screenHeight/2f);
        float shiftX = -1*(_camera.getCurrentPosition().getX()-defaultCX)*zoom;
        float shiftY = (_camera.getCurrentPosition().getY()-defaultCY-0.5f)*zoom;
        int startX = (int)(_camera.getCurrentPosition().getX()-_screenWidth/2f);
        int startY = (int)(_camera.getCurrentPosition().getY()+_screenHeight/2f);

        float  bsScale = ((float)(Math.max(WIP.container.getHeight(), WIP.container.getWidth())))/((float)(_backgroundImage.getWidth()));
        bsScale *= zoom/_camera.getMinZoom()/4f > 1f ? zoom/_camera.getMinZoom()/4f : 1f;
        gfx.scale(bsScale, bsScale);
        _backgroundImage.drawCentered(WIP.width()/2f/bsScale, WIP.height()/2f/bsScale);
        gfx.scale(1f / bsScale, 1f / bsScale);

        //_map.render((int)_player.getCenterX()*64, (int)(_map.getHeight()-_player.getCenterY())*64);

        //drawMapLayer(size, zoom, startX, startY, shiftX, shiftY, _baseMap.getLayerIndex("collision"));
        //drawMapLayer(size, zoom, startX, startY, shiftX, shiftY, _baseMap.getLayerIndex("lights"));
        drawMapLayer(size, zoom, startX, startY, shiftX, shiftY, _baseMap.getLayerIndex("background"));
        drawMapLayer(size, zoom, startX, startY, shiftX, shiftY, _baseMap.getLayerIndex("foreground"));

        Image image;
        float zoomScale;
        Rectangle body;
        for(GameObject gameObject:_gameObjects) {
            body = gameObject.getBody();
            image = gameObject.getImage(size);
            zoomScale = zoom/size;
            gfx.drawImageCentered(image,
                    body.getCenterX()*zoom + shiftX,
                    (_baseMap.getHeight() - body.getCenterY() + 1f) * zoom + shiftY,
                    image.getWidth()*gameObject.getImageFlipScale()*zoomScale,
                    image.getHeight()*zoomScale);
        }
        for(GameCharacter character:_characters) {
            body = character.getBody();
            float health = character.getHealth()/((float)character.getMaxHealth());
            float s1 = character.getPrimarySkill() == null ? 0f : character.getPrimarySkill().getCharge();
            float s2 = character.getSecondarySkill() == null ? 0f : character.getSecondarySkill().getCharge();
            float baseX = body.getCenterX()*zoom + shiftX - zoom/2f;
            float baseY = (_baseMap.getHeight() - body.getCenterY() + 1f) * zoom + shiftY - zoom - 50;

            gfx.setColor(Color.blue.darker().darker());
            gfx.fillRect(baseX, baseY + 20, zoom, 10 - 1);
            gfx.setColor(Color.red.darker().darker());
            gfx.fillRect(baseX, baseY, zoom, 10 - 1);
            gfx.setColor(Color.green.darker().darker());
            gfx.fillRect(baseX, baseY + 10, zoom, 10 - 1);

            gfx.setColor(Color.red);
            gfx.fillRect(baseX, baseY, zoom * health, 10);
            gfx.setColor(Color.green);
            gfx.fillRect(baseX, baseY + 10, zoom * s1, 10);
            gfx.setColor(Color.blue);
            gfx.fillRect(baseX, baseY + 20, zoom * s2, 10);
        }

        _lightManager.render(gfx, zoom, _camera, _screenWidth, _screenHeight, shiftX, shiftY);

        gfx.setColor(Color.white);
        gfx.setFont(Fonts.getStrokedFont(Fonts.TINY));
        GameCharacter c;
        for(int i = 0;i < _characters.size();i++) {
            c = _characters.get(i);
            gfx.drawString(c.getName(), 300, 10 + 30*i);
            gfx.drawString("Lives " + c.getLives(), 460, 10 + 30*i);
            gfx.drawString("Health " + c.getHealth(), 560, 10 + 30*i);
            if(c.getPrimarySkill() != null)
                gfx.drawString("Primary " + c.getPrimarySkill().getCharge(), 660, 10 + 30*i);
            if(c.getSecondarySkill() != null)
                gfx.drawString("Secondary " + c.getSecondarySkill().getCharge(), 760, 10 + 30*i);
        }

        gfx.setFont(Fonts.getStrokedFont(Fonts.LARGE));
        gfx.drawString(String.format("%d:%02d", (int)(_timeLeft/60), (int)(_timeLeft%60f)), 20, 20);

        if(_gameOver) {
            gfx.setFont(Fonts.getStrokedFont(Fonts.HUGE));
            gfx.drawStringCentered("WINNER", WIP.width()/2f, WIP.height()/3f);
        }

        if(WIP.debug) {
            gfx.setColor(new Color(1f, 1f, 1f, 0.5f));
            for(Light l:_lightManager.getLights()) {
                gfx.drawOval((l.getPosition().getX() - l.getRadius())*zoom+shiftX,
                        (_baseMap.getHeight() - l.getPosition().getY()+1-l.getRadius())*zoom+shiftY,
                        zoom*l.getRadius()*2, zoom*l.getRadius()*2);
                gfx.fillOval((l.getPosition().getX() - 0.25f)*zoom+shiftX,
                        (_baseMap.getHeight() - l.getPosition().getY()+1-0.25f)*zoom+shiftY,
                        zoom*0.5f, zoom*0.5f);
            }

            for(Rectangle r:_world.getRectangles()) {
                if(r.isStatic())
                    gfx.setColor(Color.red);
                else
                    gfx.setColor(Color.green);

                gfx.drawRect((r.getBottomLeft().getX())*zoom+shiftX,
                        (_baseMap.getHeight()-r.getBottomLeft().getY()-r.getHeight()+1f)*zoom+shiftY,
                        zoom*r.getWidth()-1, zoom*r.getHeight()-1);
            }

            gfx.setColor(Color.blue);
            gfx.drawLine(WIP.width()/2f, 0f, WIP.width()/2f, WIP.height());
            gfx.drawLine(0f, WIP.height()/2f, WIP.width(), WIP.height()/2f);

            gfx.drawString("Zoom: " + zoom, 10, 10);
            gfx.drawString("Res: " + size , 10, 30);
            gfx.drawString("Start: " + startX + ", " + startY, 10, 60);
            gfx.drawString("Cam  : " + (int)_camera.getCurrentPosition().getX() + ", " + (int)_camera.getCurrentPosition().getY(), 10, 80);
            gfx.drawString("FPS  : " + WIP.container.getFPS(), 10, 100);
        }
        gfx.flush();
    }

    private void drawMapLayer(int size, float zoom, int startX, int startY, float shiftX, float shiftY, int layerId)
    {
        TiledMap map = Maps.getSizedMap(WIP.gameSettings.map, size);
        Image tile;
        for(int y = startY+1;y >= startY-_screenHeight-1;y--)
        {
            for(int x = startX;x <= startX+_screenWidth+1;x++) // FOR EACH TIME ON THE SCREEN
            {
                if(x > 0 && x < map.getWidth()
                        && y > 0 && y < map.getHeight()) {
                    tile = map.getTileImage(x, (map.getHeight()-y-1), layerId); // GET THE TILE FOR THAT LAYER | v- IF IT'S NOT NULL AND IT'S IN THE RENDER DISTANCE
                    if(tile != null)
                        tile.draw(shiftX + x*zoom, shiftY + (map.getHeight()-y)*zoom, zoom, zoom);
                    //GFX.drawImageCentered(x*TILE_SIZE + xShift, y*TILE_SIZE + yShift, tile); // DRAW THE TILE WITH THE X AND Y SHIFT
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        delta = Math.min(delta, 0.1f);
        delta = _gameOver ? delta/16f : delta;

        if(!_gameOver) {
            _timeLeft -= delta;
            if(_timeLeft <= 0)
                endGame();

            if(!_gameObjectsToAdd.isEmpty()) {
                for(GameObject obj:_gameObjectsToAdd) {
                    _gameObjects.add(obj);
                    _world.add(obj.getBody());
                }
                _gameObjectsToAdd.clear();
            }

            if(!_gameObjectsToRemove.isEmpty()) {
                for(GameObject obj:_gameObjectsToRemove) {
                    _gameObjects.remove(obj);
                    _world.remove(obj.getBody());
                }
                _gameObjectsToRemove.clear();
            }

            if(!_areaEffects.isEmpty()) {
                for(AreaEffect effect:_areaEffects) {
                    for(GameCharacter character:_characters)
                        effect.process(character);
                }
                _areaEffects.clear();
            }

            _world.step(delta);

            _lightManager.update(delta);
        }

        for(GameObject gameObject:_gameObjects) {
            gameObject.update(delta);
            if(!_gameOver) {
                if(gameObject.getBody().getCenterX() <= -_baseMap.getWidth() ||
                        gameObject.getBody().getCenterX() >= _baseMap.getWidth()*2 ||
                        gameObject.getBody().getCenterY() <= -_baseMap.getHeight()) {
                    if(gameObject instanceof GameCharacter)
                        ((GameCharacter)(gameObject)).kill();
                    else if(gameObject instanceof Entity)
                        ((Entity)gameObject).destroy();
                    else
                        removeGameObject(gameObject);
                }
            }
        }

        _camera.calculateTargetPosition();
        _camera.update(delta);
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
        XBoxController.useUDAnalog = true;
    }

    public List<GameCharacter> getCharacters() {
        return _characters;
    }

    public TiledMap getBaseMap() {
        return _baseMap;
    }

    public void characterDied(GameCharacter character) {
        if(character.getLives() > 0) {
            character.revive();
            character.getBody().setBottomLeft(new Vector(11f, _baseMap.getHeight()-15f));
        } else {
            removeGameObject(character);
            _deadCharacters.add(character);
            _characters.remove(character);
            if(_characters.size() == 1)
                _gameOver = true;
        }
    }

    public void removeGameObject(GameObject gameObject) {
        _gameObjectsToRemove.add(gameObject);
    }

    public void addGameObject(GameObject gameObject) {
        _gameObjectsToAdd.add(gameObject);
    }

    public void addAreaEffect(AreaEffect effect) {
        _areaEffects.add(effect);
    }

    public void endGame() {
        WIP.enterNewState(new ResultsState());
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Menu) {
            WIP.enterNewState(new PauseState(this));
        } else if(!_gameOver) {
            for(GameCharacter character:_characters) {
                if(character.getController() != null && character.acceptInput()) {
                    if(character.getController().getInputSource() == inputSource) {
                        if(key == Key.Select && character.getPrimarySkill() != null)
                            character.getPrimarySkill().useSkill();
                        else if(key == Key.Back && character.getSecondarySkill() != null)
                            character.getSecondarySkill().useSkill();

                        character.getController().down(key);
                    }
                }
            }
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {
        for(GameCharacter character:_characters) {
            if(character.getController() != null && character.getController().getInputSource() == inputSource && !_gameOver)
                character.getController().up(key);
        }
    }

    public LightManager getLightManager() {
        return _lightManager;
    }
}
