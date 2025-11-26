import { useState } from "react";
import "./GenerateMission.css"

export default function GenerateMissionCmp() {
  const [name, setName] = useState("");
  const [mission, setMission] = useState(null);

  async function handleGenerate(e) {
    e.preventDefault();
    const res = await fetch(`http://localhost:4567/getAssignment?player=${encodeURIComponent(name)}`);
    const data = await res.json();

    setMission(data);
  }

  return (
    <section className="generate-wrapper">
      <form className="generate-card" onSubmit={handleGenerate}>
        <h2 className="title">Generate Mission</h2>

        <label className="label">Your Name</label>
        <input
          className="input"
          placeholder="Enter your name..."
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        <button className="btn">Generate Mission</button>
      </form>

      {mission && ( mission.error? (<div>
        <div className="mission-box" style={{backgroundColor:"red", color:"white"}}>
          Player not found! 
        </div>
      </div>) : 
        <div className="mission-box">
          <h3>Mission Briefing</h3>
          <p>
            Your mission, if you choose to accept it, is to eliminate
            <span className="highlight"> {mission.target}</span> at
            <span className="highlight"> {mission.place}</span> with
            <span className="highlight"> {mission.object}</span>.
          </p>
        </div>
      )}
    </section>
  );
}
