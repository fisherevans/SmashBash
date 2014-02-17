package com.fisherevans.wipgame.game.states.ready;

import com.fisherevans.wipgame.game.PlayerProfile;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class ReadyState extends WIPState {
    private List<Integer> _players;
    @Override
    public int getID() {
        return WIP.STATE_READY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _players = new ArrayList<>();
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.setFont(Fonts.getFont(Fonts.HUGE));
        graphics.setColor(Color.white);
        graphics.drawString("Ready", 10, 10);

        graphics.setFont(Fonts.getFont(Fonts.REGULAR));
        for(int x = 0;x < _players.size();x++) {
            graphics.drawString("Player " + _players.get(x), 300, 10 + 50*x);
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Select) {
            if(_players.contains(inputSource)) {
                if(_players.size() >= 2) {
                    List<PlayerProfile> profiles = new ArrayList<>(_players.size());
                    for(Integer input:_players)
                        profiles.add(new PlayerProfile(input, "base"));
                    WIP.gameSettings.players = profiles;
                    WIP.enterNewState(new PlayState(), new FadeOutTransition(), new FadeInTransition());
                }
            } else {
                _players.add(inputSource);
            }
        } else if(key == Key.Back) {
            _players.remove(new Integer(inputSource));
        }
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }
}
