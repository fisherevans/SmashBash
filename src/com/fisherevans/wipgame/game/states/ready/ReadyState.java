package com.fisherevans.wipgame.game.states.ready;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.PlayerProfile;
import com.fisherevans.wipgame.game.WIP;
import com.fisherevans.wipgame.game.WIPState;
import com.fisherevans.wipgame.game.states.play.PlayState;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Inputs;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class ReadyState extends WIPState {
    public static final int PLAYERS_NEEDED = 2;
    public static ReadyState self = null;

    private Map<Integer, CharacterSelector> _players;
    @Override
    public int getID() {
        return WIP.STATE_READY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        self = this;
        _players = new HashMap<>();
        int colorId = 0;
        for(Integer input: Inputs.getInputSources())
            if(input != Inputs.GLOBAL_INPUT)
                _players.put(input, new CharacterSelector(input, colorId++));
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        float perWidth = WIP.width()/_players.size();
        float height = WIP.height() - Fonts.getFont(Fonts.REGULAR).getLineHeight()*2;
        int id = 0;
        for(CharacterSelector player:_players.values()) {
            player.render(graphics, perWidth*id, 0, perWidth, height);
            id++;
        }

        graphics.setFont(Fonts.getFont(Fonts.REGULAR));
        graphics.setColor(Color.lightGray);
        if(meetPlayCount()) {
            graphics.drawStringCentered("Press Select to Begin Play!", WIP.width()/2f, WIP.height()-graphics.getFont().getLineHeight());
        } else {
            graphics.drawStringCentered("Need at least " + PLAYERS_NEEDED + " Players READY to Play...",
                    WIP.width()/2f, WIP.height()-graphics.getFont().getLineHeight());
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    private int countState(ReadyPlayerState state) {
        int count = 0;
        for(CharacterSelector player:_players.values())
            if(player.getReadyState() == state)
                count++;
        return count;
    }

    private boolean meetPlayCount() {
        return countState(ReadyPlayerState.Ready) >= PLAYERS_NEEDED;
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(key == Key.Select && meetPlayCount()) {
            goToPlayState();
            return;
        } else if(key == Key.Back  && countState(ReadyPlayerState.NotPlaying) == _players.size()) {
            WIP.game.enterState(WIP.STATE_START);
            return;
        }

        CharacterSelector player = _players.get(inputSource);
        if(player != null) {
            player.keyDown(key);
        }
    }

    private void goToPlayState() {
        List<PlayerProfile> profiles = new ArrayList<>(_players.size());
        for(CharacterSelector player:_players.values())
            if(player.getReadyState() == ReadyPlayerState.Ready)
                profiles.add(player.getProfile());
        WIP.gameSettings.players = profiles;
        WIP.enterNewState(new PlayState(), new FadeOutTransition(), new FadeInTransition());
    }

    public boolean isColorTaken(Color color, CharacterSelector exclude) {
        for(CharacterSelector characterSelector:_players.values())
            if(characterSelector != exclude && characterSelector.getProfile().getColor().equals(color))
                return true;
        return false;
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

}
