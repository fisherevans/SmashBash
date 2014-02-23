package com.fisherevans.wipgame.game.game_config;

/**
 * Author: Fisher Evans
 * Date: 2/23/14
 */
public class CharacterDefinition {
    String _name, _displayName;

    public CharacterDefinition(String name, String displayName) {
        _name = name;
        _displayName = displayName;
    }

    public String getName() {
        return _name;
    }

    public String getDisplayName() {
        return _displayName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CharacterDefinition)
            return ((CharacterDefinition) obj).getDisplayName().equals(_displayName)
                    && ((CharacterDefinition) obj).getName().equals(_name);
        else
            return false;
    }
}
