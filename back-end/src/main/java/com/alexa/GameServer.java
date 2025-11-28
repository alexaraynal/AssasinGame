package com.alexa;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class GameServer {

    private static final Gson gson = new Gson();
    private static final GameManager game = new GameManager();

    public static void main(String[] args) {

        port(4567);
        enableCORS("*", "GET,POST,OPTIONS", "Content-Type,Authorization");

        if(!loadLeaderboard())
        {
            game.setupObjects(Arrays.asList("Water Gun", "Spoon", "Socks"));
            game.setupPlaces(Arrays.asList("Kitchen", "Balcony", "Garden"));
            game.addPlayer(new Player("Alexa"));
            game.addPlayer(new Player("Alex"));
            game.addPlayer(new Player("Eliane"));
            game.assignTargets();
        }

        // ----------- ROUTES -----------

        // GET leaderboard
        get("/getLeaderboard", (req, res) -> {
            res.type("application/json");

            return gson.toJson(game.getLeaderboard());
        });

        // GET assignment
        get("/getAssignment", (req, res) -> {
            res.type("application/json");

            String playerName = req.queryParams("player");
            if (playerName == null) {
                res.status(400);
                return gson.toJson(Map.of("error", "player param required"));
            }

            Assignment a = game.getAssignmentForPlayer(playerName);
            if (a == null) {
                res.status(404);
                return gson.toJson(Map.of("error", "assignment not found"));
            }

            return gson.toJson(a.toDto());
        });

        //Get Players (return player list)
        get("/players", (req, res) -> {
            res.type("application/json");
            List<String> names = game.getActivePlayers()
                             .stream()
                             .map(Player::getName)
                             .toList();
            return gson.toJson(names);
        });

        // POST confirm kill
        post("/confirmKill", (req, res) -> {
            res.type("application/json");

            Map<String, Object> body = gson.fromJson(req.body(), Map.class);
            String name = (String) body.get("player");
            boolean isTarget = body.get("isTarget") != null && (Boolean) body.get("isTarget");

            Assignment a = game.getAssignmentForPlayerOrTarget(name);
            if (a == null) {
                res.status(404);
                return gson.toJson(Map.of("error", "assignment not found"));
            }

            if (isTarget) a.confirmByTarget();
            else a.confirmByPlayer();

            if (a.isConfirmed()) game.confirmKill(a);

            return gson.toJson(Map.of("ok", true));
        });

        post("/leave", (req, res) -> {
            res.type("application/json");
            Map<String,Object> body = gson.fromJson(req.body(), Map.class);
            String name = (String) body.get("player");
            Boolean cooldown = (Boolean) body.getOrDefault("cooldown", true);

            if (name == null) {
                res.status(400);
                return gson.toJson(Map.of("error", "player is required"));
            }

            else game.removePlayerByName(name);

            return gson.toJson(Map.of("ok", true));
        });


        // Print leaderboard every 30 min
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("=== Leaderboard ===");
            game.getLeaderboard().forEach(p ->
                System.out.println(p.getName() + ": " + p.getPoints()));
        }, 0, 30, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {dumpLeaderboard();}, 0, 10, TimeUnit.MINUTES);
        System.out.println("Server started on http://localhost:4567");
    }

    private static void dumpLeaderboard() 
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        var players = game.getLeaderboard();

        try (FileWriter writer = new FileWriter("leaderboard.json")) 
        {
            gson.toJson(players, writer);
            System.out.println("Leaderboard snapshot");
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    private static boolean loadLeaderboard() 
    {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("leaderboard.json")) 
        {
            var listType = new TypeToken<List<Player>>() {}.getType();
            List<Player> players = gson.fromJson(reader, listType);
            for(var p : players)
                game.addPlayer(p);
            return true;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return false;
    }


    // -------------------------------
    // CORS helper
    // -------------------------------
    private static void enableCORS(String origin, String methods, String headers) {
        options("/*", (request, response) -> {
            String reqHeaders = request.headers("Access-Control-Request-Headers");
            if (reqHeaders != null) response.header("Access-Control-Allow-Headers", reqHeaders);

            String reqMethod = request.headers("Access-Control-Request-Method");
            if (reqMethod != null) response.header("Access-Control-Allow-Methods", reqMethod);

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}

