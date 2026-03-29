import { useState } from "react";
import { createDaydream } from "../services/api";
import "./DaydreamList.css";

export default function CreateDaydreamPage({ onCreated, onUnauthorized }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [mood, setMood] = useState("");
  const [tags, setTags] = useState("");
  const [visibility, setVisibility] = useState("PUBLIC");
  const [error, setError] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!title.trim() || !description.trim()) {
      setError("Titel und Beschreibung sind erforderlich");
      return;
    }

    setIsSaving(true);
    try {
      const parsedTags = tags
        .split(",")
        .map((tag) => tag.trim())
        .filter((tag) => tag.length > 0);

      const created = await createDaydream(
        title.trim(),
        description.trim(),
        mood.trim(),
        visibility,
        parsedTags
      );

      setTitle("");
      setDescription("");
      setMood("");
      setTags("");
      setVisibility("PUBLIC");

      if (onCreated) {
        onCreated(created);
      }
    } catch (err) {
      if (err.status === 401 && onUnauthorized) {
        onUnauthorized();
        return;
      }
      setError(err.message || "Daydream konnte nicht erstellt werden");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div>
      <h1>Daydream erstellen</h1>
      <form className="create-dream-form" onSubmit={handleSubmit}>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Titel"
          className="create-dream-input"
          required
        />

        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Beschreibung"
          className="create-dream-textarea"
          required
        />

        <div className="create-dream-row">
          <input
            type="text"
            value={mood}
            onChange={(e) => setMood(e.target.value)}
            placeholder="Mood (optional)"
            className="create-dream-input"
          />

          <input
            type="text"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
            placeholder="Tags (z.B. travel, fun)"
            className="create-dream-input"
          />

          <select
            value={visibility}
            onChange={(e) => setVisibility(e.target.value)}
            className="create-dream-select"
          >
            <option value="PUBLIC">Public</option>
            <option value="PRIVATE">Private</option>
          </select>
        </div>

        <button type="submit" className="create-dream-btn" disabled={isSaving}>
          {isSaving ? "Wird erstellt..." : "Daydream erstellen"}
        </button>

        {error && <p style={{ color: "red" }}>{error}</p>}
      </form>
    </div>
  );
}
