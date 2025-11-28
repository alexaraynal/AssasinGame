import { Link } from 'react-router';
import './Navbar.css';

export default function Navbar() {
  return (
    <nav className="navbar">
      <Link to="/" className='nav-logo'>Assassin Game</Link>
      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/leaderboard">Leaderboard</Link></li>
        <li><Link to="/join">Join Game</Link></li>
        <li><Link to="/leave">Leave Game</Link></li>
        <li><Link to="/assign">Generate Mission</Link></li>
        <li><Link to="/register">Register Kill</Link></li>
      </ul>
    </nav>
  );
}
