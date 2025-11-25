package com.alexa;

public class Player {
    private final String name;
    private Player target;
    private boolean onCooldown = false;
    private int points = 0;

    public Player(String name) { this.name = name; }
    public String getName() { return name; }

    public Player getTarget() { return target; }
    public void setTarget(Player t) { this.target = t; }

    public boolean isOnCooldown() { return onCooldown; }
    public void setCooldown(boolean c) { this.onCooldown = c; }

    public int getPoints() { return points; }
    public void addPoints(int p) { points += p; }
    public void deductPoints(int p) { points -= p; }
}

