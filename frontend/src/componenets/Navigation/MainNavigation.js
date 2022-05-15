import { NavLink } from "react-router-dom"
import AuthContext from "../../context/auth-context"
import "./MainNavigation.css"




const MainNavigation = (props) => (

    <AuthContext.Consumer>
        {(context) => {
            return (<header className="main-navigation">
                <div className="main-navigation__logo">
                    <h1>EasyEvent</h1>
                </div>
                <nav className="main-navigation__items">
                    <ul>
                        {!context.token&&(<li><NavLink to="/auth">登録/ログイン</NavLink></li>)}
                        <li><NavLink to="/events">イベント</NavLink></li>

                        {!context.token&&(<li><NavLink to="/bookings">予約</NavLink></li>)}

                    </ul>
                </nav>
            </header>)
        }


        }

    </AuthContext.Consumer>
)

export default MainNavigation;