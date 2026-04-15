import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const emptyForm = {
  username: '',
  email: '',
  password: '',
};

export default function SignupPage() {
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);
  const navigate = useNavigate();
  const { signup } = useAuth();

  async function handleSubmit(event) {
    event.preventDefault();
    setError('');
    setBusy(true);

    try {
      await signup(form);
      navigate('/feed', { replace: true });
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="auth-layout">
      <section className="panel auth-panel wide-panel">
        <p className="eyebrow">Register</p>
        <h1>Build your DayDream space.</h1>
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
            <span>Email</span>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={(event) => setForm((current) => ({ ...current, email: event.target.value }))}
              required
            />
          </label>
          <label>
            <span>Password</span>
            <input
              name="password"
              type="password"
              minLength="8"
              value={form.password}
              onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
              required
            />
          </label>
          {error ? <div className="error-banner">{error}</div> : null}
          <button type="submit" className="primary-button" disabled={busy}>
            {busy ? 'Creating account...' : 'Sign up'}
          </button>
        </form>
        <p className="muted">
          Already registered? <Link to="/login">Go to login</Link>
        </p>
      </section>
    </div>
  );
}