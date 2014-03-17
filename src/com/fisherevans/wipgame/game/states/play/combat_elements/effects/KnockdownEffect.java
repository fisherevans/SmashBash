package com.fisherevans.wipgame.game.states.play.combat_elements.effects;

import com.fisherevans.wipgame.game.states.play.characters.CharacterAction;
import com.fisherevans.wipgame.game.states.play.characters.GameCharacter;
import com.fisherevans.wipgame.game.states.play.combat_elements.Effect;
import com.fisherevans.wipgame.graphics.CharacterSprite;

/**
 * Author: Fisher Evans
 * Date: 3/17/14
 */
public class KnockdownEffect extends Effect {
    private float _duration;

    public KnockdownEffect(float duration) {
        _duration = duration;
    }

    @Override
    public void doEffect(GameCharacter character) {
        character.setCurrentAction(new CharacterAction(CharacterSprite.Type.Down, _duration, true));
    }
}
