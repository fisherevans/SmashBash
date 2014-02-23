package com.fisherevans.wipgame.game.states.ready;

import com.fisherevans.wipgame.Config;
import com.fisherevans.wipgame.game.game_config.CharacterDefinition;
import com.fisherevans.wipgame.game.game_config.PlayerProfile;
import com.fisherevans.wipgame.game.states.play.characters.CharacterSprite;
import com.fisherevans.wipgame.input.Key;
import com.fisherevans.wipgame.resources.Fonts;
import com.fisherevans.wipgame.resources.Sprites;
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
    }

    public void render(Graphics graphics, float x, float y, float width, float height) {
        Font font = Fonts.getFont(Fonts.LARGE);
        graphics.setFont(font);
        float centerX = x + width/2f;

        graphics.drawStringCentered(_readyState.name(), centerX, y + font.getLineHeight());
        if(_readyState == ReadyPlayerState.NotPlaying)
            return;

        graphics.drawStringCentered(_profile.getCharacterDefinition().getDisplayName(), centerX, font.getLineHeight() * 3);

        float imageAreaHeight = height - font.getLineHeight()*5;
        float imageDrawYCenter = y + font.getLineHeight()*5 + imageAreaHeight/2f;
        Image image = Sprites.getCharacterSprites(_profile.getCharacterDefinition().getName()).get(Config.largestSize()).getIdle();
        image = image.getScaledCopy((width*0.666f)/image.getWidth());
        graphics.drawImageCentered(image, centerX, imageDrawYCenter, _profile.getColor());
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
        _profile.setCharacterDefinition(shiftSelection(_profile.getCharacterDefinition(), shift, PlayerProfile.CHARACTERS));
    }

    private void shiftColor(int shift) {
        _profile.setColor(shiftSelection(_profile.getColor(), shift, PlayerProfile.COLORS));
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
