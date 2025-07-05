import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';
import Transfer from './pages/Transfer';
import { AuthProvider, useAuth } from './hooks/useAuth';

function PrivateRoute({ children }) {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
}

export default function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/"
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        />
        <Route
          path="/transfer"
          element={
            <PrivateRoute>
              <Transfer />
            </PrivateRoute>
          }
        />
      </Routes>
    </AuthProvider>
  );
}