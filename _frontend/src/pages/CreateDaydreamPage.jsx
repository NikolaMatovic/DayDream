import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DaydreamComposer from '../components/DaydreamComposer';
import { createDaydream } from '../services/api';

export default function CreateDaydreamPage() {
  const navigate = useNavigate();
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState('');

  async function handleSubmit(payload) {
    setBusy(true);
    setError('');

    try {
      const created = await createDaydream(payload);
      navigate(`/daydreams/${created.id}`);
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setBusy(false);
    }
  }

  return (
    <DaydreamComposer
      title="Create a daydream"
      subtitle="Capture the scene, set the mood, decide whether it stays private or joins the public feed."
      submitLabel="Publish daydream"
      onSubmit={handleSubmit}
      busy={busy}
      error={error}
      onCancel={() => navigate('/feed')}
    />
  );
}