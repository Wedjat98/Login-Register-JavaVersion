import { NavLink } from "react-router-dom"
import "./MainNavigation.css"



const MainNavigation = (props) => (
    <header className="main-navigation">
        <div className="main-navigation__logo">
            <h1>EasyEvent</h1>
        </div>
        <nav className="main-navigation__items">
            <ul>
                <li><NavLink to="/auth">登録</NavLink></li>
                <li><NavLink to="/events">イベント</NavLink></li>
                <li><NavLink to="/booking">予約</NavLink></li>
            </ul>
        </nav>
    </header> 
)

export default MainNavigation;