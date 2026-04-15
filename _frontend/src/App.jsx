import { Navigate, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Shell from './components/Shell';
import { useAuth } from './context/AuthContext';
import CreateDaydreamPage from './pages/CreateDaydreamPage';
import DaydreamDetailPage from './pages/DaydreamDetailPage';
import FeedPage from './pages/FeedPage';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import ProfilePage from './pages/ProfilePage';
import SignupPage from './pages/SignupPage';

function PublicOnlyRoute({ children }) {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Navigate to="/feed" replace /> : children;
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route
        path="/login"
        element={
          <PublicOnlyRoute>
            <LoginPage />
          </PublicOnlyRoute>
        }
      />
      <Route
        path="/signup"
        element={
          <PublicOnlyRoute>
            <SignupPage />
          </PublicOnlyRoute>
        }
      />
      <Route
        element={
          <ProtectedRoute>
            <Shell />
          </ProtectedRoute>
        }
      >
        <Route path="/feed" element={<FeedPage />} />
        <Route path="/create" element={<CreateDaydreamPage />} />
        <Route path="/daydreams/:id" element={<DaydreamDetailPage />} />
        <Route path="/profile" element={<ProfilePage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}