import React from 'react';

export default function AccountSummary({ account }) {
  return (
    <div className="p-4 border rounded-md shadow mb-4">
      <h2 className="text-xl font-semibold">Balance</h2>
      <p className="text-2xl">₹{account.balance.toFixed(2)}</p>
    </div>
  );
}