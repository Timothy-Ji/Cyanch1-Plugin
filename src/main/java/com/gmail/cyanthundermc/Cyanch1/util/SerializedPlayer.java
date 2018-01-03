package com.gmail.cyanthundermc.Cyanch1.util;

public class SerializedPlayer {
    private String inventory;
    private String location;
    private float experience;
    private double health;
    private int food;
    private float saturation;
    private boolean isFlying;
    private boolean isGliding;
    private String gameMode;

    public SerializedPlayer(String inventory, String location, float experience, double health, int food, float saturation, boolean isFlying, boolean isGliding, String gameMode) {
        this.inventory = inventory;
        this.location = location;
        this.experience = experience;
        this.health = health;
        this.food = food;
        this.saturation = saturation;
        this.isFlying = isFlying;
        this.isGliding = isGliding;
        this.gameMode = gameMode;
    }

    public String getInventory() {
        return inventory;
    }

    public String getLocation() {
        return location;
    }

    public float getExperience() {
        return experience;
    }

    public double getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getSaturation() {
        return saturation;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public boolean isGliding() {
        return isGliding;
    }

    public String getGameMode() {
        return gameMode;
    }
}
