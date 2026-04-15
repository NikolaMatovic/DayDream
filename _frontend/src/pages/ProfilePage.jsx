import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { getMyDaydreams } from '../services/api';

export default function ProfilePage() {
  const { user } = useAuth();
  const [stats, setStats] = useState({ total: 0, publicCount: 0, privateCount: 0 });
  const [error, setError] = useState('');

  useEffect(() => {
    async function loadStats() {
      setError('');

      try {
        const dreams = await getMyDaydreams();
        setStats({
          total: dreams.length,
          publicCount: dreams.filter((dream) => dream.visibility === 'PUBLIC').length,
          privateCount: dreams.filter((dream) => dream.visibility === 'PRIVATE').length,
        });
      } catch (requestError) {
        setError(requestError.message);
      }
    }

    loadStats();
  }, []);

  return (
    <div className="profile-layout">
      <section className="panel profile-panel">
        <p className="eyebrow">Profile</p>
        <h1>{user?.username}</h1>
        <p className="muted">This view covers the personal account area using the data the backend currently exposes.</p>
        <div className="stats-grid">
          <article className="stat-card">
            <span>Total daydreams</span>
            <strong>{stats.total}</strong>
          </article>
          <article className="stat-card">
            <span>Public</span>
            <strong>{stats.publicCount}</strong>
          </article>
          <article className="stat-card">
            <span>Private</span>
            <strong>{stats.privateCount}</strong>
          </article>
        </div>
        {error ? <div className="error-banner">{error}</div> : null}
        <div className="profile-meta">
          <span>Username: {user?.username}</span>
          <span>Role: {user?.role}</span>
          <span>User ID: {user?.userId}</span>
        </div>
      </section>
    </div>
  );
}