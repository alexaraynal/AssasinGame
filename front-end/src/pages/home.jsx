import Hero from "../components/Hero/Hero"
import Leaderboard from "../components/Leaderboard/LeaderboardCmp"
import Navbar from "../components/Navbar/Navbar"
import "./home.css"
export default function Main()
{
    return (
        <div id="home">
            <Navbar/>
            <Hero/>
        </div>
    )
}