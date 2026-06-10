import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  adminDeleteDaydream,
  adminDeleteUser,
  adminGetAllDaydreams,
  adminGetAllUsers,
} from '../services/api';

export default function AdminPage() {
  const { isAdmin } = useAuth();
  const navigate = useNavigate();

  const [users, setUsers] = useState([]);
  const [daydreams, setDaydreams] = useState([]);
  const [activeTab, setActiveTab] = useState('users');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAdmin) {
      navigate('/feed', { replace: true });
      return;
    }
    loadData();
  }, [isAdmin]);

  async function loadData() {
    setLoading(true);
    setError(null);
    try {
      const [usersData, dreamsData] = await Promise.all([
        adminGetAllUsers(),
        adminGetAllDaydreams(),
      ]);
      setUsers(usersData ?? []);
      setDaydreams(dreamsData ?? []);
    } catch (err) {
      setError(err.message || 'Failed to load data');
    } finally {
      setLoading(false);
    }
  }

  async function handleDeleteUser(id, username) {
    if (!window.confirm(`Delete user "${username}" and all their daydreams?`)) return;
    try {
      await adminDeleteUser(id);
      setUsers((prev) => prev.filter((u) => u.id !== id));
      setDaydreams((prev) => prev.filter((d) => d.user?.id !== id));
    } catch (err) {
      setError(err.message || 'Failed to delete user');
    }
  }

  async function handleDeleteDaydream(id, title) {
    if (!window.confirm(`Delete daydream "${title}"?`)) return;
    try {
      await adminDeleteDaydream(id);
      setDaydreams((prev) => prev.filter((d) => d.id !== id));
    } catch (err) {
      setError(err.message || 'Failed to delete daydream');
    }
  }

  if (loading) return <p className="page-status">Loading…</p>;

  return (
    <div className="admin-page">
      <h1 className="page-title">Admin Panel</h1>

      {error && <p className="error-message">{error}</p>}

      <div className="admin-tabs">
        <button
          type="button"
          className={`admin-tab${activeTab === 'users' ? ' admin-tab-active' : ''}`}
          onClick={() => setActiveTab('users')}
        >
          Users ({users.length})
        </button>
        <button
          type="button"
          className={`admin-tab${activeTab === 'daydreams' ? ' admin-tab-active' : ''}`}
          onClick={() => setActiveTab('daydreams')}
        >
          Daydreams ({daydreams.length})
        </button>
      </div>

      {activeTab === 'users' && (
        <table className="admin-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th>Created</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.username}</td>
                <td>{u.email}</td>
                <td>{u.role}</td>
                <td>{u.createdAt ? new Date(u.createdAt).toLocaleDateString() : '—'}</td>
                <td>
                  {u.role !== 'ADMIN' && (
                    <button
                      type="button"
                      className="danger-button"
                      onClick={() => handleDeleteUser(u.id, u.username)}
                    >
                      Delete
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {activeTab === 'daydreams' && (
        <table className="admin-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Author</th>
              <th>Visibility</th>
              <th>Mood</th>
              <th>Created</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {daydreams.map((d) => (
              <tr key={d.id}>
                <td>{d.id}</td>
                <td>{d.title}</td>
                <td>{d.user?.username ?? '—'}</td>
                <td>{d.visibility}</td>
                <td>{d.mood}</td>
                <td>{d.createdAt ? new Date(d.createdAt).toLocaleDateString() : '—'}</td>
                <td>
                  <button
                    type="button"
                    className="danger-button"
                    onClick={() => handleDeleteDaydream(d.id, d.title)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
