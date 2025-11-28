import { useState, useEffect, useRef } from "react";

import "./MustDrink.css"

function MustDrinkList() {
    const [players, setPlayers] = useState([]);

    const audioRef = useRef(null);
    
    useEffect(() => {
    audioRef.current = new Audio("/sound.mp3"); 
    audioRef.current.loop = false;
    const fetchData = async () => {
      try {
        const res = await fetch("http://localhost:4567/mustDrink");
        if (!res.ok) throw new Error("Request failed");
        const data = await res.json();
        if (Array.isArray(data) && data.length > 0) {
          setPlayers(data);
          if (audioRef.current) {
            audioRef.current.currentTime = 0;
            audioRef.current.play();
          }

        } else {
          setPlayers([]); 
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();

    const intervalId = setInterval(fetchData, 60 * 1000);

    return () => clearInterval(intervalId);
  }, []);


  return (players.length > 0 &&(
    <div id = "drink-box">
      <ul id = "drink-list">
        {players.map((name) => (
          <li key={name}>{name}</li>
        ))}
      </ul>
    </div>
  ));
}

export default MustDrinkList;
