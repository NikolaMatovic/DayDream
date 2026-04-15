import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DaydreamCard from '../components/DaydreamCard';
import DaydreamComposer from '../components/DaydreamComposer';
import { useAuth } from '../context/AuthContext';
import {
  deleteDaydream,
  getMyDaydreams,
  getPublicDaydreams,
  updateDaydream,
} from '../services/api';

function matchesSearch(dream, query) {
  const normalized = query.toLowerCase();
  const haystack = [
    dream.title,
    dream.description,
    dream.mood,
    dream.user?.username,
    ...(dream.tags || []).map((tag) => tag.name),
  ]
    .filter(Boolean)
    .join(' ')
    .toLowerCase();

  return haystack.includes(normalized);
}

export default function FeedPage() {
  const navigate = useNavigate();
  const { user, clearSession } = useAuth();
  const [feed, setFeed] = useState('public');
  const [visibilityFilter, setVisibilityFilter] = useState('ALL');
  const [search, setSearch] = useState('');
  const [dreams, setDreams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editingDream, setEditingDream] = useState(null);
  const [composerError, setComposerError] = useState('');
  const [composerBusy, setComposerBusy] = useState(false);

  async function loadDreams(activeFeed = feed) {
    setLoading(true);
    setError('');

    try {
      const data = activeFeed === 'public' ? await getPublicDaydreams() : await getMyDaydreams();
      setDreams(data);
    } catch (requestError) {
      if (requestError.status === 401) {
        clearSession();
      }
      setError(requestError.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadDreams(feed);
  }, [feed]);

  const filteredDreams = useMemo(() => {
    return dreams.filter((dream) => {
      if (visibilityFilter !== 'ALL' && dream.visibility !== visibilityFilter) {
        return false;
      }

      if (!search.trim()) {
        return true;
      }

      return matchesSearch(dream, search.trim());
    });
  }, [dreams, search, visibilityFilter]);

  async function handleSaveEdit(payload) {
    if (!editingDream) {
      return;
    }

    setComposerBusy(true);
    setComposerError('');

    try {
      await updateDaydream(editingDream.id, payload);
      setEditingDream(null);
      await loadDreams('my');
      setFeed('my');
    } catch (requestError) {
      setComposerError(requestError.message);
    } finally {
      setComposerBusy(false);
    }
  }

  async function handleDelete(dream) {
    const confirmed = window.confirm(`Delete "${dream.title}"?`);
    if (!confirmed) {
      return;
    }

    try {
      await deleteDaydream(dream.id);
      await loadDreams(feed);
    } catch (requestError) {
      setError(requestError.message);
    }
  }

  return (
    <div className={`page-grid${editingDream ? '' : ' page-grid-single'}`}>
      <section className="panel feed-panel">
        <div className="section-heading split-heading">
          <div>
            <p className="eyebrow">Dashboard</p>
            <h1>{feed === 'public' ? 'Public feed' : 'My daydreams'}</h1>
            <p className="muted max-width-copy">
              Switch between the community stream and your personal archive. Search by title, author, tag, or mood.
            </p>
          </div>
          <button type="button" className="primary-button" onClick={() => navigate('/create')}>
            New daydream
          </button>
        </div>

        <div className="toolbar-row">
          <div className="pill-switch">
            <button
              type="button"
              className={feed === 'public' ? 'pill-active' : ''}
              onClick={() => setFeed('public')}
            >
              Public
            </button>
            <button
              type="button"
              className={feed === 'my' ? 'pill-active' : ''}
              onClick={() => setFeed('my')}
            >
              Mine
            </button>
          </div>
          <input
            value={search}
            onChange={(event) => setSearch(event.target.value)}
            placeholder="Search dreams, people, moods, tags"
          />
          <select value={visibilityFilter} onChange={(event) => setVisibilityFilter(event.target.value)}>
            <option value="ALL">All visibility</option>
            <option value="PUBLIC">Public only</option>
            <option value="PRIVATE">Private only</option>
          </select>
        </div>

        {error ? <div className="error-banner">{error}</div> : null}

        {loading ? <div className="empty-state">Loading daydreams...</div> : null}

        {!loading && filteredDreams.length === 0 ? (
          <div className="empty-state">No daydreams match this filter yet.</div>
        ) : null}

        <div className="dream-list">
          {filteredDreams.map((dream) => (
            <DaydreamCard
              key={dream.id}
              dream={dream}
              canManage={feed === 'my' && dream.user?.username === user?.username}
              onEdit={setEditingDream}
              onDelete={handleDelete}
            />
          ))}
        </div>
      </section>

      {editingDream ? (
        <DaydreamComposer
          title="Edit daydream"
          subtitle="Update the text, tags, mood, or visibility without leaving the dashboard."
          submitLabel="Save changes"
          initialValue={editingDream}
          onSubmit={handleSaveEdit}
          busy={composerBusy}
          error={composerError}
          onCancel={() => {
            setEditingDream(null);
            setComposerError('');
          }}
        />
      ) : null}
    </div>
  );
}