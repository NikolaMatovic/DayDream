import { useEffect, useState } from 'react';

const moodOptions = [
  '',
  'Calm',
  'Curious',
  'Joyful',
  'Hopeful',
  'Inspired',
  'Dreamy',
  'Playful',
  'Focused',
  'Adventurous',
  'Romantic',
  'Mysterious',
  'Nostalgic',
  'Melancholic',
  'Anxious',
  'Electric',
  'Overwhelmed',
  'Restless',
  'Peaceful',
  'Motivated',
];

const initialState = {
  title: '',
  description: '',
  mood: '',
  visibility: 'PUBLIC',
  tags: '',
};

function buildState(source) {
  if (!source) {
    return initialState;
  }

  return {
    title: source.title || '',
    description: source.description || '',
    mood: source.mood || '',
    visibility: source.visibility || 'PUBLIC',
    tags: source.tags?.map((tag) => tag.name).join(', ') || '',
  };
}

export default function DaydreamComposer({
  title,
  subtitle,
  submitLabel,
  initialValue,
  onSubmit,
  busy,
  error,
  onCancel,
}) {
  const [form, setForm] = useState(() => buildState(initialValue));

  useEffect(() => {
    setForm(buildState(initialValue));
  }, [initialValue]);

  function updateField(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    await onSubmit({
      title: form.title.trim(),
      description: form.description.trim(),
      mood: form.mood.trim(),
      visibility: form.visibility,
      tags: form.tags
        .split(',')
        .map((tag) => tag.trim())
        .filter(Boolean),
    });
  }

  return (
    <section className="panel composer-panel">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Create and update</p>
          <h2>{title}</h2>
        </div>
        {subtitle ? <p className="muted max-width-copy">{subtitle}</p> : null}
      </div>
      <form className="composer-form" onSubmit={handleSubmit}>
        <label>
          <span>Title</span>
          <input name="title" value={form.title} onChange={updateField} placeholder="A city made of mirrors" required />
        </label>
        <label>
          <span>Description</span>
          <textarea
            name="description"
            value={form.description}
            onChange={updateField}
            placeholder="Describe the scene, the people, the strange details, the feeling."
            rows="7"
            required
          />
        </label>
        <div className="form-grid two-columns">
          <label>
            <span>Mood</span>
            <select name="mood" value={form.mood} onChange={updateField}>
              <option value="">Select a mood</option>
              {moodOptions
                .filter((option) => option)
                .map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
            </select>
          </label>
          <label>
            <span>Visibility</span>
            <select name="visibility" value={form.visibility} onChange={updateField}>
              <option value="PUBLIC">Public</option>
              <option value="PRIVATE">Private</option>
            </select>
          </label>
        </div>
        <label>
          <span>Tags</span>
          <input name="tags" value={form.tags} onChange={updateField} placeholder="ocean, summer, impossible-architecture" />
        </label>
        {error ? <div className="error-banner">{error}</div> : null}
        <div className="form-actions">
          {onCancel ? (
            <button type="button" className="ghost-button" onClick={onCancel}>
              Cancel
            </button>
          ) : null}
          <button type="submit" className="primary-button" disabled={busy}>
            {busy ? 'Saving...' : submitLabel}
          </button>
        </div>
      </form>
    </section>
  );
}