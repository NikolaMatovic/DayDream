import { Link } from 'react-router-dom';

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
      <Link className="primary-link" to={`/daydreams/${dream.id}`}>
        Open discussion
      </Link>
    </article>
  );
}