export async function getDaydreams() {
  const response = await fetch('/v1/dreams/all');
  if (!response.ok) {
    throw new Error('Failed to fetch daydreams');
  }

  return response.json();
}