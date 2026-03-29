export async function login(username, password) {
  const response = await fetch('/v1/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include', // Send cookies (session)
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || 'Login fehlgeschlagen');
  }

  return response.json();
}

export async function getDaydreams() {
  const response = await fetch('/v1/dreams/all', {
    credentials: 'include', // Send cookies (session)
  });

  if (!response.ok) {
    let message = 'Failed to fetch daydreams';
    try {
      const errorBody = await response.json();
      message = errorBody.message || message;
    } catch {
      // Ignore JSON parsing errors and use default message.
    }

    const error = new Error(message);
    error.status = response.status;
    throw error;
  }

  return response.json();
}