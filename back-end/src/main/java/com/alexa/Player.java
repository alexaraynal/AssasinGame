package com.alexa;

public class Player {
    private final String name;
    private Player target;
    private int points = 0;

    private int kills = 0, deaths = 0;

    public Player(String name) { this.name = name; }
    public String getName() { return name; }

    public Player getTarget() { return target; }
    public void setTarget(Player t) { this.target = t; }

    public int getPoints() { return points; }
    public void addPoints(int p) { points += p; }
    public void deductPoints(int p) { points -= p; }

    public void addKill(){kills++;}
    public void addDeath(){deaths++;}

    public int getKills(){return kills;}
    public int getDeaths(){return deaths;}
}

