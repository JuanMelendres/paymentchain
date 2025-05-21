package com.paymentchain.transaction.service;

import com.paymentchain.transaction.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    public List<Transaction> getTransactions();
    public Optional<Transaction> getTransaction(long id);
    public Transaction createTransaction(Transaction customer);
    public Transaction updateTransaction(long id, Transaction customer);
    public Optional<Transaction> deleteTransaction(long id);
    public List<Transaction> getTransactionByIbanAccount(String ibanAccount);
}
