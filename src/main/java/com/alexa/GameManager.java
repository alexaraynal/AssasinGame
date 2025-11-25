package com.alexa;

import java.util.*;

public class GameManager {

    //List of players
    private final List<Player> activePlayers = new ArrayList<>();
    //List of objects
    private final List<GameObject> objects = new ArrayList<>();
    //List of places
    private final List<Place> places = new ArrayList<>();
    //List of assignments
    private final List<Assignment> currentAssignments = new ArrayList<>();
    private final Random rnd = new Random();

    public void addPlayer(Player p){ activePlayers.add(p); }
    public List<Player> getActivePlayers(){ return activePlayers; }

    public void setupObjects(List<String> names){
        objects.clear();
        names.forEach(n -> objects.add(new GameObject(n)));
    }

    public void setupPlaces(List<String> names){
        places.clear();
        names.forEach(n -> places.add(new Place(n)));
    }

    public void assignTargets() {
        currentAssignments.clear();

        if(activePlayers.size() < 2) return;

        List<Player> shuffled = new ArrayList<>(activePlayers);
        Collections.shuffle(shuffled, rnd);

        for (int i = 0; i < shuffled.size(); i++) {
            Player killer = shuffled.get(i);
            Player target = shuffled.get((i + 1) % shuffled.size());
            GameObject obj = objects.get(rnd.nextInt(objects.size()));
            Place place = places.get(rnd.nextInt(places.size()));

            killer.setTarget(target);
            currentAssignments.add(new Assignment(killer, target, obj, place));
        }
    }

    public Assignment getAssignmentForPlayer(String name){
        return currentAssignments.stream()
            .filter(a -> a.getPlayer().getName().equals(name)
                      || a.getTarget().getName().equals(name))
            .findFirst().orElse(null);
    }

    public void confirmKill(Assignment a) {
        a.getPlayer().addPoints(20);
        a.getTarget().deductPoints(10);

        a.getTarget().setCooldown(true);

        assignTargets();
    }

    public List<Player> getLeaderboard() {
        List<Player> sorted = new ArrayList<>(activePlayers);
        sorted.sort((p1,p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        return sorted;
    }

    public void leavePartyCooldown(String playerName) {
        for (Player p : activePlayers) {
            if (p.getName().equals(playerName)) {
                p.setCooldown(true);  // mark them inactive
                break;
            }
        }
        // Reassign targets for others if necessary
        assignTargets();
    }

    // Remove a player completely from the game
    public void removePlayerByName(String playerName) {

        // Find the player first
        for (Player p : activePlayers) {
            if (p.getName().equals(playerName)) {
                // Remove any assignments involving them\
                activePlayers.remove(p);
                currentAssignments.removeIf(a -> 
                a.getPlayer().equals(p) || a.getTarget().equals(p));
                // Reassign targets for remaining players
                assignTargets();
                break;
            }
        }
        
    }


}

