package com.fisherevans.wipgame.game.states.ready;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import com.fisherevans.wipgame.game.game_config.PlayerProfile;
import com.fisherevans.wipgame.graphics.CharacterSprite;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Characters;
import com.fisherevans.wipgame.resources.Fonts;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterSelector {
    private int _inputSource;
    private PlayerProfile _profile;
    private ReadyPlayerState _readyState;

    public CharacterSelector(int inputSource) {
        _profile = new PlayerProfile(inputSource);
        _inputSource = inputSource;
        _readyState = ReadyPlayerState.NotPlaying;
        _profile.setSpriteId(0);
    }

    public void render(Graphics graphics, float x, float y, float width, float height) {
        Font font = Fonts.getFont(Config.largeSize);
        graphics.setFont(font);
        float centerX = x + width/2f;

        graphics.drawStringCentered(_readyState.name(), centerX, y + font.getLineHeight()*2);
        if(_readyState == ReadyPlayerState.NotPlaying)
            return;

        graphics.drawStringCentered(_profile.getCharacterDefinition().name, centerX, font.getLineHeight() * 4);

        float imageAreaHeight = height - font.getLineHeight()*5;
        float imageDrawYCenter = y + font.getLineHeight()*5 + imageAreaHeight/2f;
        Image image = _profile.getCharacterDefinition().getSprite(_profile.getSpriteId())
                .get(Config.hugeSize).getFrame(CharacterSprite.Type.Idle);
        graphics.drawImageCentered(image, centerX, imageDrawYCenter, Color.white);
    }

    public void keyDown(Key key) {
        switch(key) {
            case Select:
                _readyState = _readyState.next();
                return;
            case Back:
                _readyState = _readyState.prev();
                return;
        }
        if(_readyState == ReadyPlayerState.Selecting) {
            switch(key) {
                case Up:
                    shiftCharacter(1);
                    break;
                case Down:
                    shiftCharacter(-1);
                    break;
                case Left:
                    shiftColor(-1);
                    break;
                case Right:
                    shiftColor(1);
                    break;
            }
        }
    }

    private void shiftCharacter(int shift) {
        _profile.setSpriteId(0);
        _profile.setCharacterDefinition(shiftSelection(_profile.getCharacterDefinition(), shift, Characters.getCharacterDefinitions()));
    }

    private void shiftColor(int shift) {
        _profile.setSpriteId(shiftSelection(_profile.getSpriteId(), shift, _profile.getCharacterDefinition().getSpriteIds()));
    }

    private static <T> T shiftSelection(T current, int shift, T[] list) {
        int id;
        for(id = 0;id < list.length && !list[id].equals(current);id++);
        return list[shiftIndex(id, shift, list.length)];
    }

    private static int shiftIndex(int current, int shift, int length) {
        if(shift > length)
            shift %= length;
        current += shift;
        if(current < 0)
            current += length;
        else if(current >= length)
            current -= length;
        return current;
    }

    public int getInputSource() {
        return _inputSource;
    }

    public PlayerProfile getProfile() {
        return _profile;
    }

    public ReadyPlayerState getReadyState() {
        return _readyState;
    }
}
