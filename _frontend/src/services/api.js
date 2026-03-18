//const BASE_URL = "http://localhost:8080";
//const BASE_URL = "https://fuzzy-umbrella-5vvj56gqqqqfpgw4-3000.app.github.dev"

export async function getUsers() {
  const response = await fetch(`/userapi/v1/users`);
  if (!response.ok) {
    throw new Error("Failed to fetch users");
  }
  return response.json();
}