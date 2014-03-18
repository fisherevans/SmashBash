package com.fisherevans.smash_bash.game.states.ready;

import com.fisherevans.smash_bash.Config;
import com.fisherevans.smash_bash.game.SmashBash;
import com.fisherevans.smash_bash.game.SmashBashState;
import com.fisherevans.smash_bash.game.game_config.PlayerProfile;
import com.fisherevans.smash_bash.game.states.play.PlayState;
import com.fisherevans.smash_bash.input.Key;
import com.fisherevans.smash_bash.resources.Fonts;
import com.fisherevans.smash_bash.resources.Inputs;
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
public class ReadyState extends SmashBashState {
    public static final int PLAYERS_NEEDED = 2;
    public static ReadyState self = null;

    private Map<Integer, CharacterSelector> _players;
    @Override
    public int getID() {
        return SmashBash.STATE_READY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        self = this;
        _players = new HashMap<Integer, CharacterSelector>();
        int colorId = 0;
        for(Integer input: Inputs.controllers.keySet())
            if(input != Inputs.GLOBAL_INPUT)
                _players.put(input, new CharacterSelector(input));
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        float perWidth = SmashBash.width()/_players.size();
        float height = SmashBash.height() - Fonts.getFont(Config.normalSize).getLineHeight()*2;
        int id = 0;
        for(CharacterSelector player:_players.values()) {
            player.render(graphics, perWidth*id, 0, perWidth, height);
            id++;
        }

        graphics.setFont(Fonts.getFont(Config.normalSize));
        graphics.setColor(Color.lightGray);
        if(meetPlayCount()) {
            graphics.drawStringCentered("TODO Press Select to Begin Play!", SmashBash.width()/2f, SmashBash.height()-graphics.getFont().getLineHeight());
        } else {
            graphics.drawStringCentered("TODO Need at least " + PLAYERS_NEEDED + " Players READY to Play...",
                    SmashBash.width()/2f, SmashBash.height()-graphics.getFont().getLineHeight());
        }
        graphics.setFont(Fonts.getFont(Config.smallSize));
        graphics.setColor(Color.lightGray);
        graphics.drawStringCentered("Use Up/Down to choose a character. Use Left/Right to choose a style.", SmashBash.width()/2f, graphics.getFont().getLineHeight());
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
        } else if((key == Key.Back  && countState(ReadyPlayerState.NotPlaying) == _players.size()) || key == Key.Menu) {
            SmashBash.game.enterState(SmashBash.STATE_START);
            return;
        }

        CharacterSelector player = _players.get(inputSource);
        if(player != null) {
            player.keyDown(key);
        }
    }

    private void goToPlayState() {
        List<PlayerProfile> profiles = new ArrayList<PlayerProfile>(_players.size());
        for(CharacterSelector player:_players.values())
            if(player.getReadyState() == ReadyPlayerState.Ready)
                profiles.add(player.getProfile());
        SmashBash.gameSettings.players = profiles;
        SmashBash.enterNewState(new PlayState(), new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

}
