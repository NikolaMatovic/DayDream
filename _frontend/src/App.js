import { useState, useEffect } from 'react';
import './App.css';
import DaydreamList from './components/DaydreamList';
import CreateDaydreamPage from './components/CreateDaydreamPage';
import Login from './components/Login';
import Signup from './components/Signup';

function App() {
  const [user, setUser] = useState(null);
  const [authMode, setAuthMode] = useState('login');
  const [page, setPage] = useState('feed');

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
    setPage('feed');
  };

  const handleSignupSuccess = (userData) => {
    setUser(userData);
    setPage('feed');
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
    setPage('feed');
  };

  const handleUnauthorized = () => {
    setUser(null);
    localStorage.removeItem('user');
    setAuthMode('login');
    setPage('feed');
  };

  return (
    <div className="App">
      {user ? (
        <>
          <header className="app-header">
            <h1>DayDream</h1>
            <div className="user-info">
              <button
                className={`nav-btn ${page === 'feed' ? 'nav-btn-active' : ''}`}
                onClick={() => setPage('feed')}
              >
                Feed
              </button>
              <button
                className={`nav-btn ${page === 'create' ? 'nav-btn-active' : ''}`}
                onClick={() => setPage('create')}
              >
                Erstellen
              </button>
              <span>Willkommen, {user.displayName || user.username}!</span>
              <button className="logout-btn" onClick={handleLogout}>
                Abmelden
              </button>
            </div>
          </header>
          <main className="app-content">
            {page === 'create' ? (
              <CreateDaydreamPage
                onUnauthorized={handleUnauthorized}
                onCreated={() => setPage('feed')}
              />
            ) : (
              <DaydreamList onUnauthorized={handleUnauthorized} />
            )}
          </main>
        </>
      ) : authMode === 'signup' ? (
        <Signup
          onSignupSuccess={handleSignupSuccess}
          onSwitchToLogin={() => setAuthMode('login')}
        />
      ) : (
        <Login
          onLoginSuccess={handleLoginSuccess}
          onSwitchToSignup={() => setAuthMode('signup')}
        />
      )}
    </div>
  );
}

export default App;