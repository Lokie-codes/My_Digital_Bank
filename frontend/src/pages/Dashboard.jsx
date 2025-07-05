import React, { useEffect, useState } from 'react';
import api from '../api/client';
import Navbar from '../components/Navbar';
import AccountSummary from '../components/AccountSummary';
import TransactionList from '../components/TransactionList';

export default function Dashboard() {
  const [account, setAccount] = useState({ balance: 0 });
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    api.get('/account').then(res => setAccount(res.data));
    api.get('/transactions').then(res => setTransactions(res.data));
  }, []);

  return (
    <div>
      <Navbar />
      <div className="p-6">
        <AccountSummary account={account} />
        <h2 className="text-xl font-semibold mb-2">Recent Transactions</h2>
        <TransactionList transactions={transactions} />
      </div>
    </div>
  );
}