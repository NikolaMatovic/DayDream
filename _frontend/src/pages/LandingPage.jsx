import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const featureList = [
  'Register and sign in with session-based authentication',
  'Write public or private daydreams with mood and tags',
  'Browse the public community feed and your personal archive',
  'Open each daydream, read comments, and add your own response',
];

export default function LandingPage() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="landing-page">
      <section className="hero-card">
        <div className="hero-copy">
          <p className="eyebrow">Dream journal and community feed</p>
          <h1>Keep the ideas that would usually disappear by lunch.</h1>
          <p className="lead-copy">
            DayDream is a small creative system for capturing private thoughts, publishing public daydreams,
            and discussing them with other people.
          </p>
          <div className="cta-row">
            <Link className="primary-button" to={isAuthenticated ? '/feed' : '/signup'}>
              {isAuthenticated ? 'Open app' : 'Create account'}
            </Link>
            <Link className="ghost-button" to={isAuthenticated ? '/feed' : '/login'}>
              {isAuthenticated ? 'Go to feed' : 'Login'}
            </Link>
          </div>
        </div>
        <div className="hero-panel">
          <p className="eyebrow">Use cases covered</p>
          <ul className="feature-list">
            {featureList.map((feature) => (
              <li key={feature}>{feature}</li>
            ))}
          </ul>
        </div>
      </section>
    </div>
  );
}