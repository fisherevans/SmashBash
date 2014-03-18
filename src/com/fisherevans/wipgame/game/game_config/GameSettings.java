package com.fisherevans.wipgame.game.game_config;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class GameSettings {
    public List<PlayerProfile> players = new ArrayList<PlayerProfile>();
    public int health = 100;
    public int lives = 5;
    public int time = 6;
    public String map = null;
}
