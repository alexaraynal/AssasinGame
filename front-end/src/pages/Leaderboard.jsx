import LeaderboardCmp from "../components/Leaderboard/LeaderboardCmp";
import MustDrinkList from "../components/Leaderboard/MustDrink";
import Navbar from "../components/Navbar/Navbar";
import "./Leaderboard.css"

export default function Leaderboard()
{
    return(
        <>
            <Navbar/>
            <MustDrinkList/>
            <LeaderboardCmp/>
        </>
    )
}