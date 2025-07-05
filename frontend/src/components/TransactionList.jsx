import React from 'react';

export default function TransactionList({ transactions }) {
  return (
    <table className="w-full table-auto border-collapse">
      <thead>
        <tr>
          <th className="border px-2 py-1">Date</th>
          <th className="border px-2 py-1">Type</th>
          <th className="border px-2 py-1">Amount</th>
        </tr>
      </thead>
      <tbody>
        {transactions.map(tx => (
          <tr key={tx.id}>
            <td className="border px-2 py-1">{new Date(tx.date).toLocaleString()}</td>
            <td className="border px-2 py-1">{tx.type}</td>
            <td className="border px-2 py-1">₹{tx.amount.toFixed(2)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}