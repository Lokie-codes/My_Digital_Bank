import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/client';
import Navbar from '../components/Navbar';

export default function Transfer() {
  const [toAccount, setToAccount] = useState('');
  const [amount, setAmount] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      await api.post('/transactions/transfer', {
        toAccount,
        amount: parseFloat(amount),
      });
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message || 'Transfer failed');
    }
  };

  return (
    <div>
      <Navbar />
      <div className="flex items-center justify-center h-screen">
        <form onSubmit={handleSubmit} className="w-96 p-6 border rounded-md shadow">
          <h1 className="text-2xl mb-4">Transfer Funds</h1>
          {error && <p className="text-red-500 mb-2">{error}</p>}
          <label className="block mb-2">
            To Account Number
            <input
              type="text"
              value={toAccount}
              onChange={e => setToAccount(e.target.value)}
              className="w-full p-2 border rounded mt-1"
            />
          </label>
          <label className="block mb-4">
            Amount (₹)
            <input
              type="number"
              step="0.01"
              value={amount}
              onChange={e => setAmount(e.target.value)}
              className="w-full p-2 border rounded mt-1"
            />
          </label>
          <button type="submit" className="w-full p-2 bg-green-600 text-white rounded">Send</button>
        </form>
      </div>
    </div>
  );
}