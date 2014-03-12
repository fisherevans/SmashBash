package com.fisherevans.wipgame.game.game_config;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterDefinition {
    private String _name, _displayName;
    private Class _primarySkill, _secondarySkill;

    public CharacterDefinition(String name, String displayName, Class primarySkill, Class secondarySkill) {
        _name = name;
        _displayName = displayName;
        _primarySkill = primarySkill;
        _secondarySkill = secondarySkill;
    }

    public String getName() {
        return _name;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public Class getPrimarySkill() {
        return _primarySkill;
    }

    public Class getSecondarySkill() {
        return _secondarySkill;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof CharacterDefinition)
                && ((CharacterDefinition) obj).getName().equals(_name);
    }
}
