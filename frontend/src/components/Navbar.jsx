import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

export default function Navbar() {
  const { user, logout } = useAuth();
  return (
    <nav className="p-4 bg-blue-600 text-white flex justify-between">
      <div className="font-bold">MyBank</div>
      <div>
        <Link to="/">Dashboard</Link>
        {' | '}
        <Link to="/transfer">Transfer</Link>
        {' | '}
        <button onClick={logout} className="underline">Logout</button>
      </div>
    </nav>
  );
}