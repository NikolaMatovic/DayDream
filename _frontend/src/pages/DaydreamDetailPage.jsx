import { useEffect, useMemo, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { createComment, getMyDaydreams, getPublicDaydreams } from '../services/api';

function formatDate(value) {
  if (!value) return 'Unknown date';
  return new Date(value).toLocaleString();
}

export default function DaydreamDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, clearSession } = useAuth();
  const [daydream, setDaydream] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [comment, setComment] = useState('');
  const [commentError, setCommentError] = useState('');
  const [commentBusy, setCommentBusy] = useState(false);

  async function loadDaydream() {
    setLoading(true);
    setError('');

    try {
      const [publicDreams, myDreams] = await Promise.all([
        getPublicDaydreams(),
        getMyDaydreams().catch((requestError) => {
          if (requestError.status === 401) {
            return [];
          }
          throw requestError;
        }),
      ]);

      const combined = [...publicDreams, ...myDreams];
      const found = combined.find((dream) => String(dream.id) === id);

      if (!found) {
        setError('Daydream nicht gefunden.');
      }

      setDaydream(found || null);
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
    loadDaydream();
  }, [id]);

  const canComment = useMemo(() => daydream?.visibility === 'PUBLIC', [daydream]);

  async function handleSubmitComment(event) {
    event.preventDefault();
    setCommentBusy(true);
    setCommentError('');

    try {
      await createComment(daydream.id, comment.trim());
      setComment('');
      await loadDaydream();
    } catch (requestError) {
      setCommentError(requestError.message);
    } finally {
      setCommentBusy(false);
    }
  }

  if (loading) {
    return <div className="panel empty-state">Loading daydream...</div>;
  }

  if (!daydream) {
    return (
      <section className="panel empty-state">
        <p>{error || 'Daydream not found.'}</p>
        <button type="button" className="ghost-button" onClick={() => navigate('/feed')}>
          Back to feed
        </button>
      </section>
    );
  }

  return (
    <div className="detail-layout">
      <section className="panel detail-panel">
        <div className="section-heading split-heading">
          <div>
            <p className="eyebrow">Daydream detail</p>
            <h1>{daydream.title}</h1>
          </div>
          <Link className="ghost-button" to="/feed">
            Back to feed
          </Link>
        </div>
        <p className="detail-description">{daydream.description}</p>
        <div className="dream-meta">
          <span>Visibility: {daydream.visibility}</span>
          <span>Mood: {daydream.mood || 'Not set'}</span>
          <span>Author: {daydream.user?.username}</span>
          <span>Created: {formatDate(daydream.createdAt)}</span>
        </div>
        <div className="tag-row">
          {daydream.tags?.length ? daydream.tags.map((tag) => <span key={tag.id || tag.name} className="tag">#{tag.name}</span>) : <span className="muted">No tags</span>}
        </div>
      </section>

      <section className="panel comments-panel">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Discussion</p>
            <h2>{daydream.comments?.length || 0} comments</h2>
          </div>
        </div>

        {canComment ? (
          <form className="comment-form" onSubmit={handleSubmitComment}>
            <textarea
              value={comment}
              onChange={(event) => setComment(event.target.value)}
              placeholder="Add your perspective to this public daydream"
              rows="4"
              required
            />
            {commentError ? <div className="error-banner">{commentError}</div> : null}
            <button type="submit" className="primary-button" disabled={commentBusy || !comment.trim()}>
              {commentBusy ? 'Posting...' : 'Post comment'}
            </button>
          </form>
        ) : (
          <div className="empty-state">This daydream is private, so comments are disabled.</div>
        )}

        <div className="comment-list">
          {daydream.comments?.length ? (
            daydream.comments.map((entry) => (
              <article key={entry.id} className="comment-card">
                <div className="comment-head">
                  <strong>{entry.authorUsername || 'Unknown user'}</strong>
                  <span>{formatDate(entry.createdAt)}</span>
                </div>
                <p>{entry.content}</p>
              </article>
            ))
          ) : (
            <div className="empty-state">No comments yet.</div>
          )}
        </div>
      </section>
    </div>
  );
}