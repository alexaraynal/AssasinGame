import React, { useEffect, useState } from 'react';
import './Leaderboard.css';

export default function LeaderboardCmp() {
  const [data, setData] = useState([]);

  useEffect(() => {
    fetch('http://localhost:4567/getLeaderboard')
      .then(res => res.json())
      .then(json => setData(json))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="leaderboard-container" id="leaderboard">
      <h2>Leaderboard</h2>
      <table className="leaderboard-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Kills</th>
            <th>Deaths</th>
            <th>Points</th>
          </tr>
        </thead>
        <tbody>
          {data.map((player, idx) => (
            <tr key={idx}>
              <td>{player.name}</td>
              <td>{player.kills}</td>
              <td>{player.deaths}</td>
              <td>{player.points}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
