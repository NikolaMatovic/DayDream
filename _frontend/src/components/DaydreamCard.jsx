import { useState } from 'react';
import { Link } from 'react-router-dom';

const PREVIEW_COUNT = 3;

function formatDate(value) {
  if (!value) return 'Unknown date';
  return new Date(value).toLocaleString();
}

export default function DaydreamCard({
  dream,
  canManage,
  onEdit,
  onDelete,
}) {
  const comments = dream.comments ?? [];
  const hasMore = comments.length > PREVIEW_COUNT;
  const [expanded, setExpanded] = useState(false);
  const visibleComments = expanded ? comments : comments.slice(0, PREVIEW_COUNT);

  return (
    <article className="dream-card">
      <div className="dream-card-top">
        <div>
          <p className="eyebrow">{dream.visibility}</p>
          <h3>{dream.title}</h3>
        </div>
        {canManage ? (
          <div className="card-actions">
            <button type="button" className="ghost-button" onClick={() => onEdit(dream)}>
              Edit
            </button>
            <button type="button" className="danger-button" onClick={() => onDelete(dream)}>
              Delete
            </button>
          </div>
        ) : null}
      </div>
      <p className="dream-description">{dream.description}</p>
      <div className="tag-row">
        {dream.tags?.length ? dream.tags.map((tag) => <span key={tag.id || tag.name} className="tag">#{tag.name}</span>) : <span className="muted">No tags yet</span>}
      </div>
      <div className="dream-meta">
        <span>Mood: {dream.mood || 'Not set'}</span>
        <span>By {dream.user?.username || 'Unknown user'}</span>
        <span>{formatDate(dream.createdAt)}</span>
      </div>

      {comments.length > 0 && (
        <div className="card-comments">
          <p className="card-comments-label">{comments.length} comment{comments.length !== 1 ? 's' : ''}</p>
          {visibleComments.map((entry) => (
            <div key={entry.id} className="card-comment">
              <strong>{entry.authorUsername || 'Unknown'}</strong>
              <span>{entry.content}</span>
            </div>
          ))}
          {hasMore && (
            <button
              type="button"
              className="ghost-button card-comments-toggle"
              onClick={() => setExpanded((v) => !v)}
            >
              {expanded
                ? 'Show less'
                : `Show ${comments.length - PREVIEW_COUNT} more comment${comments.length - PREVIEW_COUNT !== 1 ? 's' : ''}`}
            </button>
          )}
        </div>
      )}

      <Link className="primary-link" to={`/daydreams/${dream.id}`}>
        {dream.visibility === 'PUBLIC' ? 'Open discussion & comment' : 'View daydream'}
      </Link>
    </article>
  );
}