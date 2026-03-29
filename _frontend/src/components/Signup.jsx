import { useState } from "react";
import { signup } from "../services/api";
import "./Auth.css";

export default function Signup({ onSignupSuccess, onSwitchToLogin }) {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (password.length < 8) {
      setError("Passwort muss mindestens 8 Zeichen haben");
      return;
    }

    if (password !== confirmPassword) {
      setError("Passwoerter stimmen nicht ueberein");
      return;
    }

    setLoading(true);

    try {
      const result = await signup(username, email, password, displayName);
      localStorage.setItem("user", JSON.stringify(result));
      onSignupSuccess(result);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h1>DayDream Registrierung</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label>Anzeigename:</label>
            <input
              type="text"
              value={displayName}
              onChange={(e) => setDisplayName(e.target.value)}
              disabled={loading}
            />
          </div>

          <div className="form-group">
            <label>Passwort:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              minLength={8}
              required
              disabled={loading}
            />
            <small>Mindestens 8 Zeichen</small>
          </div>

          <div className="form-group">
            <label>Passwort wiederholen:</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              disabled={loading}
            />
          </div>

          {error && <p className="error">{error}</p>}

          <button type="submit" disabled={loading}>
            {loading ? "Wird registriert..." : "Registrieren"}
          </button>
        </form>

        <p className="switch-auth">
          Bereits registriert?{" "}
          <a href="#login" onClick={(e) => {
            e.preventDefault();
            onSwitchToLogin();
          }}>
            Jetzt anmelden
          </a>
        </p>
      </div>
    </div>
  );
}
