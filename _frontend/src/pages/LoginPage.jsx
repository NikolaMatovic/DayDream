import { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();

  const nextPath = location.state?.from || '/feed';

  async function handleSubmit(event) {
    event.preventDefault();
    setError('');
    setBusy(true);

    try {
      await login(form);
      navigate(nextPath, { replace: true });
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="auth-layout">
      <section className="panel auth-panel">
        <p className="eyebrow">Login</p>
        <h1>Welcome back.</h1>
        <p className="muted">The backend expects username and password for login.</p>
        <form className="auth-form" onSubmit={handleSubmit}>
          <label>
            <span>Username</span>
            <input
              name="username"
              value={form.username}
              onChange={(event) => setForm((current) => ({ ...current, username: event.target.value }))}
              required
            />
          </label>
          <label>
            <span>Password</span>
            <input
              name="password"
              type="password"
              value={form.password}
              onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
              required
            />
          </label>
          {error ? <div className="error-banner">{error}</div> : null}
          <button type="submit" className="primary-button" disabled={busy}>
            {busy ? 'Signing in...' : 'Login'}
          </button>
        </form>
        <p className="muted">
          Need an account? <Link to="/signup">Create one</Link>
        </p>
      </section>
    </div>
  );
}