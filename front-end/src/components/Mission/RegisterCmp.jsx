import { useState } from "react";
import "./GenerateMission.css"

export default function RegisterCmp() {
  const [player, setPlayer] = useState("");
  const [isTarget, setIsTarget] = useState(false);
  const [status, setStatus] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault(); 

    try {
      const res = await fetch("http://localhost:4567/confirmKill", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ player, isTarget })
      });

      const data = await res.json();
      setStatus(data);
    } catch (err) {
      console.error(err);
      setStatus({ error: "Request failed" });
    }
  }

  return (
    <section className="generate-wrapper">
      <form className="generate-card" onSubmit={handleSubmit}>
        <h2 className="title">Register Kill</h2>

        <label className="label">Your Name</label>
        <input
          className="input"
          placeholder="Enter your name..."
          value={player}
          onChange={(e) => setPlayer(e.target.value)}
          required
        />

        <label className="label">Report your role in this kill</label>
        <select
          className="input"
          value={isTarget}
          onChange={(e) => setIsTarget(e.target.value === "true")}
        >
          <option value="true">Target</option>
          <option value="false">Assassin</option>
        </select>

        <button className="btn">Submit Kill</button>
      </form>

      {status && (
        <>
        {status.error ? (
          <div className="mission-box" style={{background : "red"}}>
            <p style={{ color: "white" }}>{status.error}</p>
          </div>
        ) : (
          <div className="mission-box">
            <p>{status.message || "Kill registered successfully!"}</p>
          </div>
        )}
        </>
      )}
    </section>
  );
}
