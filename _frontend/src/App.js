import { useState, useEffect } from 'react';
import './App.css';
import DaydreamList from './components/DaydreamList';
import Login from './components/Login';

function App() {
  const [user, setUser] = useState(null);

  // Prüfe ob User schon angemeldet ist (aus localStorage)
  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      try {
        setUser(JSON.parse(savedUser));
      } catch (e) {
        console.error('Fehler beim Laden des Users:', e);
        localStorage.removeItem('user');
      }
    }
  }, []);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  const handleUnauthorized = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <div className="App">
      {user ? (
        <>
          <header className="app-header">
            <h1>DayDream</h1>
            <div className="user-info">
              <span>Willkommen, {user.displayName || user.username}!</span>
              <button className="logout-btn" onClick={handleLogout}>
                Abmelden
              </button>
            </div>
          </header>
          <DaydreamList onUnauthorized={handleUnauthorized} />
        </>
      ) : (
        <Login onLoginSuccess={handleLoginSuccess} />
      )}
    </div>
  );
}

export default App;