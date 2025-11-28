import { useState } from "react";
import "../Mission/GenerateMission.css"

export default function Leave() {
  const [name, setName] = useState("");
  const [mission, setMission] = useState(null);

  async function handleLeave(e) {
    e.preventDefault();
    try {
      const res = await fetch("http://localhost:4567/leave", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({player :name})
      });

      const data = await res.json();
      setMission(data);
    } 
    catch (err) 
    {
      console.error(err);
      setMission({ error: "Request failed" });
    }

    setMission(data);
  }

  return (
    <section className="generate-wrapper">
      <form className="generate-card" onSubmit={handleLeave}>
        <h2 className="title">Leave the game</h2>

        <label className="label">Your Name</label>
        <input
          className="input"
          placeholder="Enter your name..."
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <button className="btn">Leave</button>
      </form>

      {mission && ( mission.error? (<div>
        <div className="mission-box" style={{backgroundColor:"red", color:"white"}}>
          There was a problem when removing the player! 
        </div>
      </div>) : 
        <div className="mission-box" >
          Player removed successfully! 
        </div>
      )}
    </section>
  );
}
