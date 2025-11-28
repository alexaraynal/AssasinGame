package com.alexa;

import java.util.*;

public class GameManager {

    private final List<Player> activePlayers = new ArrayList<>();
    private final List<GameObject> objects = new ArrayList<>();
    private final List<Place> places = new ArrayList<>();
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
            System.out.println(killer.getName() + " must kill " + target.getName());
        }
    }
    
    public void assignToPlayer(Player killer)
    {
        if(activePlayers.size() < 2) return;
        Player target;

        do{
            target = activePlayers.get(rnd.nextInt(activePlayers.size()));

        }
        while(target == killer);

        GameObject obj = objects.get(rnd.nextInt(objects.size()));
        Place place = places.get(rnd.nextInt(places.size()));
        killer.setTarget(target);
        currentAssignments.add(new Assignment(killer, target, obj, place));
    }

    public Assignment getAssignmentForPlayer(String name){
        return currentAssignments.stream()
            .filter(a -> a.getPlayer().getName().equals(name))
            .findFirst().orElse(null);
    }

    public Assignment getAssignmentForPlayerOrTarget(String name){
        return currentAssignments.stream()
            .filter(a -> a.getPlayer().getName().equals(name)
                      || a.getTarget().getName().equals(name))
            .findFirst().orElse(null);
    }

    public void confirmKill(Assignment a) {
        a.getPlayer().addPoints(20);
        a.getPlayer().addKill();

        a.getTarget().deductPoints(10);
        a.getTarget().addDeath();

        assignToPlayer(a.getPlayer());
    }

    public List<Player> getLeaderboard() {
        List<Player> sorted = new ArrayList<>(activePlayers);
        sorted.sort((p1,p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        return sorted;
    }

    public void removePlayerByName(String playerName) 
    {
        Optional<Player> toRemove = activePlayers.stream()
                                                .filter(p -> p.getName().equals(playerName))
                                                .findFirst();

        if (!toRemove.isPresent()) return;
        activePlayers.remove(toRemove.get());
        currentAssignments.removeIf(a -> 
            a.getPlayer().equals(toRemove.get()) || a.getTarget().equals(toRemove.get())
        );
        Optional<Player> prevTarget = activePlayers.stream()
                                                .filter(p -> p.getTarget().equals(toRemove.get()))
                                                .findFirst();          
        if(prevTarget.isPresent())
        {
            System.out.println(prevTarget.get().getName());
            assignToPlayer(prevTarget.get());                                                                                  
        }
    }

    public List<Player> mustDrinkPlayers()
    {
        int s = activePlayers.size();
        return getLeaderboard().subList(s<3 ? 0 : s-3, activePlayers.size());
    }

    public void newPlayer(Player p)
    {
        activePlayers.add(p);
        assignToPlayer(p);
    }
}

