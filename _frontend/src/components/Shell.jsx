import { useState } from 'react';
import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navItems = [
  { to: '/feed', label: 'Feed' },
  { to: '/create', label: 'Create' },
  { to: '/profile', label: 'Profile' },
];

export default function Shell() {
  const navigate = useNavigate();
  const { user, logout, isAdmin } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);

  async function handleLogout() {
    await logout();
    navigate('/');
  }

  return (
    <div className="shell">
      <header className="topbar">
        <div>
          <NavLink to="/feed" className="brand-link">
            <span className="brand-mark">DayDream</span>
          </NavLink>
          <p className="topbar-subtitle">Capture ideas. Shape a private or public world.</p>
        </div>
        <button
          type="button"
          className="menu-toggle"
          aria-label="Toggle navigation"
          aria-expanded={menuOpen}
          onClick={() => setMenuOpen((current) => !current)}
        >
          {menuOpen ? 'Close' : 'Menu'}
        </button>
        <nav className={`topbar-nav${menuOpen ? ' topbar-nav-open' : ''}`}>
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) => `nav-link${isActive ? ' nav-link-active' : ''}`}
              onClick={() => setMenuOpen(false)}
            >
              {item.label}
            </NavLink>
          ))}
          {isAdmin && (
            <NavLink
              to="/admin"
              className={({ isActive }) => `nav-link${isActive ? ' nav-link-active' : ''}`}
              onClick={() => setMenuOpen(false)}
            >
              Admin
            </NavLink>
          )}
          <div className="user-pill">
            <span>{user?.username}</span>
            <button
              type="button"
              className="ghost-button"
              onClick={async () => {
                setMenuOpen(false);
                await handleLogout();
              }}
            >
              Log out
            </button>
          </div>
        </nav>
      </header>
      <main className="page-frame">
        <Outlet />
      </main>
    </div>
  );
}