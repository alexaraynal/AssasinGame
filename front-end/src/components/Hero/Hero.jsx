import { Link } from "react-router";
import "./Hero.css"
export default function Hero() {
    return (
    <section className="hero">
        <h1 className="hero-title">
            Assasin <br /> Game
        </h1>


        <div className="hero-buttons">
            <Link to="/assign">
                <button className="hero-btn primary">
                    Generate mission
                </button>
            </Link>


            <Link to="/register">
                <button variant="outline" className="hero-btn secondary">
                    Register kill
                </button>
            </Link>
        </div>
    </section>
);
}