package com.alexa;

import java.util.Map;

public class Assignment {
    private final Player player;
    private final Player target;
    private final GameObject object;
    private final Place place;

    private boolean confirmedByPlayer = false;
    private boolean confirmedByTarget = false;

    public Assignment(Player player, Player target, GameObject object, Place place) {
        this.player = player;
        this.target = target;
        this.object = object;
        this.place = place;
    }

    public void confirmByPlayer() { confirmedByPlayer = true; }
    public void confirmByTarget() { confirmedByTarget = true; }
    public boolean isConfirmed() { return confirmedByPlayer && confirmedByTarget; }

    public Player getPlayer() { return player; }
    public Player getTarget() { return target; }
    public GameObject getObject() { return object; }
    public Place getPlace() { return place; }

    public Map<String, Object> toDto() {
        return Map.of(
            "player", player.getName(),
            "target", target.getName(),
            "object", object.getName(),
            "place", place.getName(),
            "confirmedByPlayer", confirmedByPlayer,
            "confirmedByTarget", confirmedByTarget
        );
    }
}

