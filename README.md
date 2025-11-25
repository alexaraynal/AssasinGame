🎯 Bday Assassin Game

**Bday Assassin Game** is a fun, web-based multiplayer game built in **Java**. Players are assigned targets, objects, and places, and the goal is to "assassinate" other players virtually. The game tracks points, confirms kills, and maintains a live leaderboard.  

---

## 🛠 Features

- Assigns each player a **target**, an **object**, and a **place**  
- Players confirm kills as either the murderer or the victim  
- **Leaderboard** tracks points for all players  
- Supports **cooldown** for leaving players  
- Fully **RESTful API** for easy frontend integration  
- **Scheduled leaderboard logging** every 30 minutes  

---

## 💻 Technologies

- **Java 17**  
- [**Spark Java**](http://sparkjava.com/) – lightweight HTTP server  
- [**Gson**](https://github.com/google/gson) – JSON serialization  
- **Maven** – project management & dependency handling  

---

## ⚡ Setup & Running

### Prerequisites

- Java JDK 17  
- Maven installed


###Clone & Build

git clone https://github.com/yourusername/bday-assassin-game.git

cd bday-assassin-game

mvn clean package

###Run the Server

mvn exec:java -Dexec.mainClass="com.alexa.GameServer"

Server starts at:
http://localhost:4567

###🔗 API Endpoints
1. Get Leaderboard

##GET /getLeaderboard

Returns JSON array of players with their points

##Example Response:

{ "name":"Alexa","points":20 }, { "name":"Alex","points":10 }, { "name":"Eliane","points":0 }

##2. Get Player Assignment

GET /getAssignment?player=<playerName>

Returns JSON with player's target, object, place, and confirmation status

##Example Response:

{ "player":"Alexa", "target":"Eliane", "object":"Spoon", "place":"Kitchen", "confirmedByPlayer":false, "confirmedByTarget":true }

##3. Confirm Kill

POST /confirmKill

Request JSON:

{ "player": "Alexa", "isTarget": false }

Confirms a kill as either the player or the target. Updates points when both confirm.

##4. Get Players (Dropdown Support)

GET /getPlayers

Returns JSON list of all active player names to populate frontend dropdowns

##5. Remove Player / Cooldown

POST /removePlayer or /leaveCooldown

Request JSON:

{ "player": "Alex" }

Removes a player from the game or sets them on cooldown

###🎮 Frontend Ideas

Display leaderboard in real time

Show a dropdown for players when registering a kill

Allow players to confirm kills using buttons

Display active players and cooldown status

###⚠️ Notes

Scheduled leaderboard logging runs every 30 minutes

Minimum 2 players are required to assign targets

Player names must be unique for proper target assignment

###📄 License

MIT License – free to use, modify, and share
