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
import java.util.List;

/**
 * Author: Fisher Evans
 * Date: 2/10/14
 */
public class ReadyState extends WIPState {
    public static final int PLAYERS_NEEDED = 2;
    private List<PlayerProfileState> _players;
    @Override
    public int getID() {
        return WIP.STATE_READY;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        _players = new ArrayList<>();
        for(Integer input: Inputs.getInputSources())
            if(input != Inputs.GLOBAL_INPUT)
                _players.add(new PlayerProfileState(new PlayerProfile(input)));
    }

    @Override
    public void enterState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(Graphics graphics) throws SlickException {
        graphics.setFont(Fonts.getFont(Fonts.HUGE));
        graphics.setColor(Color.white);
        graphics.drawString("Ready", 10, 10);

        graphics.setFont(Fonts.getFont(Fonts.SMALL));
        PlayerProfileState state;
        for(int y = 0;y < _players.size();y++) {
            state = _players.get(y);
            graphics.setColor(Color.white);
            graphics.drawString("Player " + state.getProfile().getInput(), 300, 10 + 50*y);
            graphics.setColor(Color.darkGray);
            if(state.getReadyState() != ReadyPlayerState.NotPlaying) {
                if(state.getReadyState() == ReadyPlayerState.Selecting)
                    graphics.drawString("Selecting", 420, 10 + 50 * y);
                else if(state.getReadyState() == ReadyPlayerState.Ready)
                    graphics.drawString("Ready", 420, 10 + 50 * y);
                graphics.setColor(state.getSelectingState() == SelectingPlayerState.Character && state.getReadyState() == ReadyPlayerState.Selecting ? Config.HIGHLIGHT : Color.lightGray);
                graphics.drawString(state.getProfile().getCharacter(), 580, 10 + 50 * y);
                if(state.getSelectingState() == SelectingPlayerState.Color && state.getReadyState() == ReadyPlayerState.Selecting) {
                    graphics.setColor(Config.HIGHLIGHT);
                    graphics.fillRect(670, (10 + 50 * y) - 3, 38, 38);
                }
                graphics.setColor(state.getProfile().getColor());
                graphics.fillRect(673, (10 + 50 * y), 32, 32);
            } else {
                graphics.drawString("Not Playing - Press Select [" + Inputs.getPhysicalKey(Key.Select, state.getProfile().getInput()) + "] to Play", 420, 10 + 50*y);
            }
        }
        graphics.setColor(Color.lightGray);
        if(allState(ReadyPlayerState.Ready)) {
            graphics.drawString("Press Select to Begin Play!", 300, 10 + 50*_players.size());
        } else {
            graphics.drawString("Need at least " + PLAYERS_NEEDED + " Players READY to Play...", 300, 10 + 50*_players.size());
        }
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void leaveState(GameContainer container, StateBasedGame game) throws SlickException {

    }

    public PlayerProfileState getInputState(int inputSource) {
        for(PlayerProfileState profileState:_players)
            if(profileState.getProfile().getInput() == inputSource)
                return profileState;

        return null;
    }

    private boolean allState(ReadyPlayerState state) {
        for(PlayerProfileState profileState:_players)
            if(profileState.getReadyState() != state)
                return false;
        return true;
    }

    private boolean meetPlayCount() {
        int ready = 0;
        for(PlayerProfileState profileState:_players)
            if(profileState.getReadyState() == ReadyPlayerState.Ready)
                ready++;
        return ready >= PLAYERS_NEEDED;
    }

    @Override
    public void keyDown(Key key, int inputSource) {
        if(inputSource == Inputs.GLOBAL_INPUT)
            return;
        PlayerProfileState state = getInputState(inputSource);
        switch(key) {
            case Select: {
                if(allState(ReadyPlayerState.Ready) && meetPlayCount()) {
                    goToPlayState();
                } else {
                    switch(state.getReadyState()) {
                        case NotPlaying:
                            state.setReadyState(ReadyPlayerState.Selecting);
                            break;
                        case Selecting:
                            state.setReadyState(ReadyPlayerState.Ready);
                            break;
                        case Ready:

                            break;
                    }
                }
                break;
            }
            case Back: {
                if(allState(ReadyPlayerState.NotPlaying)) {
                    WIP.game.enterState(WIP.STATE_START);
                } else {
                    switch(state.getReadyState()) {
                        case NotPlaying:

                            break;
                        case Selecting:
                            state.setReadyState(ReadyPlayerState.NotPlaying);
                            break;
                        case Ready:
                            state.setReadyState(ReadyPlayerState.Selecting);
                            break;
                    }
                }
                break;
            }
            case Left: {
                if(state.getReadyState() == ReadyPlayerState.Ready || state.getReadyState() == ReadyPlayerState.NotPlaying)
                    break;
                switch (state.getSelectingState()) {
                    case Character:
                        break;
                    case Color:
                        state.setSelectingState(SelectingPlayerState.Character);
                        break;
                }
                break;
            }
            case Right: {
                if(state.getReadyState() == ReadyPlayerState.Ready || state.getReadyState() == ReadyPlayerState.NotPlaying)
                    break;
                switch (state.getSelectingState()) {
                    case Character:
                        state.setSelectingState(SelectingPlayerState.Color);
                        break;
                    case Color:
                        break;
                }
                break;
            }
            case Up: {
                if(state.getReadyState() == ReadyPlayerState.Ready || state.getReadyState() == ReadyPlayerState.NotPlaying)
                    break;
                int currentId = 0;
                switch (state.getSelectingState()) {
                    case Character:
                        for(int id = 0;id < PlayerProfile.CHARACTERS.length;id++) {
                            if(PlayerProfile.CHARACTERS[id].equals(state.getProfile().getCharacter())) {
                                currentId = id;
                                break;
                            }
                        }
                        currentId++;
                        currentId %= PlayerProfile.CHARACTERS.length;
                        state.getProfile().setCharacter(PlayerProfile.CHARACTERS[currentId]);
                        break;
                    case Color:
                        for(int id = 0;id < PlayerProfile.COLORS.length;id++) {
                            if(PlayerProfile.COLORS[id].equals(state.getProfile().getColor())) {
                                currentId = id;
                                break;
                            }
                        }
                        currentId++;
                        currentId %= PlayerProfile.COLORS.length;
                        state.getProfile().setColor(PlayerProfile.COLORS[currentId]);
                        break;
                }
                break;
            }
            case Down: {
                if(state.getReadyState() == ReadyPlayerState.Ready || state.getReadyState() == ReadyPlayerState.NotPlaying)
                    break;
                int currentId = 0;
                switch (state.getSelectingState()) {
                    case Character:
                        for(int id = 0;id < PlayerProfile.CHARACTERS.length;id++) {
                            if(PlayerProfile.CHARACTERS[id].equals(state.getProfile().getCharacter())) {
                                currentId = id;
                                break;
                            }
                        }
                        currentId--;
                        currentId += currentId < 0 ? PlayerProfile.CHARACTERS.length : 0;
                        state.getProfile().setCharacter(PlayerProfile.CHARACTERS[currentId]);
                        break;
                    case Color:
                        for(int id = 0;id < PlayerProfile.COLORS.length;id++) {
                            if(PlayerProfile.COLORS[id].equals(state.getProfile().getColor())) {
                                currentId = id;
                                break;
                            }
                        }
                        currentId--;
                        currentId += currentId < 0 ? PlayerProfile.COLORS.length : 0;
                        state.getProfile().setColor(PlayerProfile.COLORS[currentId]);
                        break;
                }

                break;
            }
        }
    }

    private void goToPlayState() {
        List<PlayerProfile> profiles = new ArrayList<>(_players.size());
        for(PlayerProfileState player:_players)
            if(player.getReadyState() == ReadyPlayerState.Ready)
                profiles.add(player.getProfile());
        WIP.gameSettings.players = profiles;
        WIP.enterNewState(new PlayState(), new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void keyUp(Key key, int inputSource) {

    }

    private class PlayerProfileState {
        private PlayerProfile _profile;
        private ReadyPlayerState _readyState;
        private SelectingPlayerState _selectingState;

        public PlayerProfileState(PlayerProfile profile) {
            _profile = profile;
            _readyState = ReadyPlayerState.NotPlaying;
            _selectingState = SelectingPlayerState.Character;
        }

        private PlayerProfile getProfile() {
            return _profile;
        }

        private ReadyPlayerState getReadyState() {
            return _readyState;
        }

        private void setReadyState(ReadyPlayerState readyState) {
            _readyState = readyState;
        }

        private SelectingPlayerState getSelectingState() {
            return _selectingState;
        }

        private void setSelectingState(SelectingPlayerState selectingState) {
            _selectingState = selectingState;
        }
    }

    private enum ReadyPlayerState {
        NotPlaying,
        Selecting,
        Ready
    }

    private enum SelectingPlayerState {
        Character,
        Color
    }
}
