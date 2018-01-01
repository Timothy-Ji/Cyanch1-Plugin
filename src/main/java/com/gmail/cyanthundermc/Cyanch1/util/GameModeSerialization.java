package com.gmail.cyanthundermc.Cyanch1.util;

import org.bukkit.GameMode;

public class GameModeSerialization {
    public static String getStringFromGameMode(GameMode gameMode) {
        return gameMode.name();
    }

    public static GameMode getGameModeFromString(String str) {
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode.name().equalsIgnoreCase(str))
                return gameMode;
        }

        return GameMode.SURVIVAL;
    }
}
