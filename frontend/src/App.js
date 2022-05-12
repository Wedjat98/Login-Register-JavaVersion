import './App.css';
import { Component } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import AuthPage from './pages/Auth';
import EventsPage from './pages/Events'
import BookingsPage from './pages/Booking'

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/auth"/>} />
          <Route path="/auth" element={<AuthPage/>} />
          <Route path="/login" element={null} />
          <Route path="/register" element={null} />
          <Route path="/events" element={<EventsPage/>} />
          <Route path="/booking" element={<BookingsPage/>} />
        </Routes>
      </BrowserRouter>
    )
  }
}

export default App;