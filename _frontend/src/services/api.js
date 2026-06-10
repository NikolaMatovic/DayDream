async function parseResponse(response) {
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

async function request(path, options = {}) {
  const response = await fetch(path, {
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {}),
    },
    ...options,
  });

  const body = await parseResponse(response);

  if (!response.ok) {
    const error = new Error(body?.message || 'Request failed');
    error.status = response.status;
    throw error;
  }

  return body;
}

export async function loginRequest({ username, password }) {
  return request('/v1/auth/login', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  });
}

export async function signupRequest({ username, email, password }) {
  return request('/v1/auth/signup', {
    method: 'POST',
    body: JSON.stringify({ username, email, password }),
  });
}

export async function logoutRequest() {
  const response = await fetch('/logout', {
    method: 'POST',
    credentials: 'include',
  });

  if (!response.ok && response.status !== 204) {
    throw new Error('Logout fehlgeschlagen');
  }
}

export async function getPublicDaydreams() {
  return request('/v1/dreams/public');
}

export async function getMyDaydreams() {
  return request('/v1/dreams/my');
}

export async function getAllDaydreams() {
  return request('/v1/dreams/all');
}

export async function createDaydream(payload) {
  return request('/v1/dreams', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
}

export async function updateDaydream(id, payload) {
  return request(`/v1/dreams/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload),
  });
}

export async function deleteDaydream(id) {
  const response = await fetch(`/v1/dreams/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });

  if (!response.ok && response.status !== 204) {
    const body = await parseResponse(response);
    const error = new Error(body?.message || 'Löschen fehlgeschlagen');
    error.status = response.status;
    throw error;
  }
}

export async function createComment(daydreamId, content) {
  return request(`/v1/dreams/${daydreamId}/comments`, {
    method: 'POST',
    body: JSON.stringify({ content }),
  });
}

export async function adminGetAllUsers() {
  return request('/v1/admin/users');
}

export async function adminDeleteUser(id) {
  const response = await fetch(`/v1/admin/users/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });
  if (!response.ok && response.status !== 204) {
    const body = await parseResponse(response);
    const error = new Error(body?.message || 'Löschen fehlgeschlagen');
    error.status = response.status;
    throw error;
  }
}

export async function adminGetAllDaydreams() {
  return request('/v1/admin/dreams');
}

export async function adminDeleteDaydream(id) {
  const response = await fetch(`/v1/admin/dreams/${id}`, {
    method: 'DELETE',
    credentials: 'include',
  });
  if (!response.ok && response.status !== 204) {
    const body = await parseResponse(response);
    const error = new Error(body?.message || 'Löschen fehlgeschlagen');
    error.status = response.status;
    throw error;
  }
}