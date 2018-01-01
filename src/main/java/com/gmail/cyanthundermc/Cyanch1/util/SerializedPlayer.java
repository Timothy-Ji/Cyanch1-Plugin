package com.gmail.cyanthundermc.Cyanch1.util;

public class SerializedPlayer {
    private String inventory;
    private String armor;
    private String location;
    private float experience;
    private String gameMode;

    public SerializedPlayer(String inventory, String armor, String location, float experience, String gameMode) {
        this.inventory = inventory;
        this.armor = armor;
        this.location = location;
        this.experience = experience;
        this.gameMode = gameMode;
    }

    public String getInventory() {
        return inventory;
    }

    public String getArmor() {
        return armor;
    }

    public String getLocation() {
        return location;
    }

    public float getExperience() {
        return experience;
    }

    public String getGameMode() {
        return gameMode;
    }
}