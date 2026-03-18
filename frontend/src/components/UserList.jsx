import { useEffect, useState } from "react";

//const BASE_URL = "https://fuzzy-umbrella-5vvj56gqqqqfpgw4-3000.app.github.dev";

export default function UserList() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`/userapi/v1/users`)
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

      {error && <p>{error}</p>}

      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.firstName} {user.lastName} - {user.email}
          </li>
        ))}
      </ul>
    </div>
  );
}