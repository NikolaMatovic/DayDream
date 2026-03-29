import { useEffect, useState } from "react";
import { getDaydreams } from "../services/api";

export default function DaydreamList({ onUnauthorized }) {
  const [dreams, setDreams] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getDaydreams()
      .then((data) => setDreams(data))
      .catch((err) => {
        console.error(err);
        if (err.status === 401 && onUnauthorized) {
          onUnauthorized();
          return;
        }
        setError(err.message || "Daydreams could not be loaded");
      });
  }, [onUnauthorized]);

  return (
    <div>
      <h1>Daydream Feed</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {dreams.map((dream) => (
        <div
          key={dream.id}
          style={{
            border: "1px solid #ccc",
            padding: "15px",
            margin: "15px auto",
            borderRadius: "10px",
            maxWidth: "500px",
            textAlign: "left",
            boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
          }}
        >
          <h2>{dream.title}</h2>

          <p><strong>Description:</strong> {dream.description}</p>
          <p><strong>Mood:</strong> {dream.mood || "-"}</p>
          <p><strong>Visibility:</strong> {dream.visibility}</p>
          <p>
            <strong>Author:</strong> {dream.user?.displayName || dream.user?.username || "Unknown"}
          </p>

          <p>
            <strong>Created:</strong>{" "}
            {dream.createdAt ? new Date(dream.createdAt).toLocaleString() : "-"}
          </p>

          <p>
            <strong>Updated:</strong>{" "}
            {dream.updatedAt ? new Date(dream.updatedAt).toLocaleString() : "-"}
          </p>

          <p>
            <strong>Tags:</strong>{" "}
            {dream.tags?.length ? dream.tags.map((tag) => tag.name).join(", ") : "No tags"}
          </p>

          <div>
            <strong>Comments:</strong>
            {dream.comments?.length ? (
              <ul>
                {dream.comments.map((comment) => (
                  <li key={comment.id} style={{ marginTop: "8px" }}>
                    <div>
                      <strong>{comment.user?.displayName || comment.user?.username || "Unknown"}:</strong>{" "}
                      {comment.content}
                    </div>
                    <small style={{ color: "#666" }}>
                      {comment.createdAt ? new Date(comment.createdAt).toLocaleString() : ""}
                    </small>
                  </li>
                ))}
              </ul>
            ) : (
              <p>No comments</p>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}