async function readJsonIfPossible(response) {
  const contentType = response.headers.get('content-type') || '';

  if (!contentType.includes('application/json')) {
    return null;
  }

  try {
    return await response.json();
  } catch {
    return null;
  }
}

function buildProxyHintMessage(defaultMessage, responseText) {
  if (responseText && responseText.toLowerCase().includes('proxy error')) {
    return 'Backend nicht erreichbar. Bitte pruefe, ob das Backend auf Port 8080 laeuft.';
  }

  return defaultMessage;
}

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
    const errorBody = await readJsonIfPossible(response);

    if (errorBody && errorBody.message) {
      throw new Error(errorBody.message);
    }

    const rawText = await response.text();
    throw new Error(buildProxyHintMessage('Login fehlgeschlagen', rawText));
  }

  const body = await readJsonIfPossible(response);

  if (!body) {
    throw new Error('Unerwartete Antwort vom Server erhalten.');
  }

  return body;
}

export async function signup(username, email, password, displayName) {
  const response = await fetch('/v1/auth/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ username, email, password, displayName }),
  });

  if (!response.ok) {
    const errorBody = await readJsonIfPossible(response);

    if (errorBody && errorBody.message) {
      throw new Error(errorBody.message);
    }

    const rawText = await response.text();
    throw new Error(buildProxyHintMessage('Registrierung fehlgeschlagen', rawText));
  }

  const body = await readJsonIfPossible(response);

  if (!body) {
    throw new Error('Unerwartete Antwort vom Server erhalten.');
  }

  return body;
}

export async function getDaydreams() {
  const response = await fetch('/v1/dreams/all', {
    credentials: 'include', // Send cookies (session)
  });

  if (!response.ok) {
    let message = 'Failed to fetch daydreams';

    const errorBody = await readJsonIfPossible(response);
    if (errorBody && errorBody.message) {
      message = errorBody.message;
    } else {
      const rawText = await response.text();
      message = buildProxyHintMessage(message, rawText);
    }

    const error = new Error(message);
    error.status = response.status;
    throw error;
  }

  const body = await readJsonIfPossible(response);

  if (!body) {
    throw new Error('Unerwartete Antwort vom Server erhalten.');
  }

  return body;
}

export async function createComment(daydreamId, content) {
  const response = await fetch(`/v1/dreams/${daydreamId}/comments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ content }),
  });

  if (!response.ok) {
    let message = 'Kommentar konnte nicht gespeichert werden';

    const errorBody = await readJsonIfPossible(response);
    if (errorBody && errorBody.message) {
      message = errorBody.message;
    } else {
      const rawText = await response.text();
      message = buildProxyHintMessage(message, rawText);
    }

    const error = new Error(message);
    error.status = response.status;
    throw error;
  }

  const body = await readJsonIfPossible(response);

  if (!body) {
    throw new Error('Unerwartete Antwort vom Server erhalten.');
  }

  return body;
}

export async function createDaydream(title, description, mood, visibility, tags) {
  const response = await fetch('/v1/dreams', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ title, description, mood, visibility, tags }),
  });

  if (!response.ok) {
    let message = 'Daydream konnte nicht erstellt werden';

    const errorBody = await readJsonIfPossible(response);
    if (errorBody && errorBody.message) {
      message = errorBody.message;
    } else {
      const rawText = await response.text();
      message = buildProxyHintMessage(message, rawText);
    }

    const error = new Error(message);
    error.status = response.status;
    throw error;
  }

  const body = await readJsonIfPossible(response);

  if (!body) {
    throw new Error('Unerwartete Antwort vom Server erhalten.');
  }

  return body;
}