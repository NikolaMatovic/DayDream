import { useEffect, useState } from "react";

export default function UserList() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("/userapi/v1/users")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch users");
        }
        return res.json();
      })
      .then((data) => setUsers(data))
      .catch((err) => {
        console.error(err);
        setError("Users could not be loaded");
      });
  }, []);

  return (
    <div>
      <h1>Users</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {users.map((user) => (
        <div
          key={user.id}
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
          <h2>
            {user.firstName} {user.lastName}
          </h2>

          <p><strong>Username:</strong> {user.username}</p>
          <p><strong>Email:</strong> {user.email}</p>

          <p>
            <strong>Status:</strong>{" "}
            <span style={{ color: user.status === "ACTIVE" ? "green" : "red" }}>
              {user.status}
            </span>
          </p>

          <p>
            <strong>Last Login:</strong>{" "}
            {new Date(user.lastLogin).toLocaleString()}
          </p>

          <p>
            <strong>Location:</strong>{" "}
            {user.location?.name} ({user.location?.city})
          </p>

          <p>
            <strong>Role:</strong> {user.role?.name}
          </p>

          <div>
            <strong>Permissions:</strong>
            <ul>
              {user.role?.permissions?.map((perm) => (
                <li key={perm}>{perm}</li>
              ))}
            </ul>
          </div>
        </div>
      ))}
    </div>
  );
}