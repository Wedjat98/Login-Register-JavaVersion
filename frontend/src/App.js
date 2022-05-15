import './App.css';
import { Component } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import AuthContext from './context/auth-context';
import AuthPage from './pages/Auth';
import EventsPage from './pages/Events'
import BookingsPage from './pages/Booking'
import MainNavigation from './componenets/Navigation/MainNavigation';

class App extends Component {

  state = {
    token: null,
    userId: null,
  }
  login = (token, userId, tokenExpiration) => {
    this.setState({ token: token, userId: userId, tokenExpiration })
  }


  logout = () => {
    this.setState({ token: null, userId: null })
  }

  render() {
    return (
      <BrowserRouter>
        <AuthContext.Provider value={{
          token: this.state.token,
          userId: this.state.userId,
          login: this.login,
          logout: this.logout
        }}>
          <MainNavigation />
          <main className="main-content"></main>
          <Routes>
            {!this.state.token && (<Route path="/" element={<Navigate to="/auth" />} />)}
            {!this.state.token && (<Route path="/auth" element={<AuthPage />}/>)}
            {this.state.token && (<Route path="/" element={<Navigate to="/events" />} />)}
            {this.state.token && (<Route path="/auth" element={<Navigate to="/events" />} />)}
            <Route path="/events" element={<EventsPage />} />
            {this.state.token && (<Route path="/bookings" element={<BookingsPage />} />)}
          </Routes>
        </AuthContext.Provider>
      </BrowserRouter>

    )
  }
}

export default App;
