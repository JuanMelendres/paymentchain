package com.paymentchain.transaction.service;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactions() {
        log.info("Get transactions");
        return this.transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransaction(long id) {
        log.info("Get transaction with id {}", id);

        return this.transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        log.info("Creating new transaction: {}", transaction);
        transaction.applyBusinessValidations();
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(long id, Transaction transaction) {
        Transaction transactionToBeUpdated = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        transactionToBeUpdated.setReference(transaction.getReference());
        transactionToBeUpdated.setAmount(transaction.getAmount());
        transactionToBeUpdated.setFee(transaction.getFee());
        transactionToBeUpdated.setDescription(transaction.getDescription());
        transactionToBeUpdated.setStatus(transaction.getStatus());
        transactionToBeUpdated.setChannel(transaction.getChannel());
        transactionToBeUpdated.applyBusinessValidations();

        return transactionRepository.save(transactionToBeUpdated);
    }

    @Override
    public Optional<Transaction> deleteTransaction(long id) {
        Optional<Transaction> transactionExist = this.transactionRepository.findById(id);

        if (transactionExist.isPresent()) {
            log.info("Deleting transaction with id: {}", id);
            log.info("Transaction: {}", transactionExist.get());
            this.transactionRepository.delete(transactionExist.get());
        }

        return Optional.empty();
    }

    @Override
    public List<Transaction> getTransactionByIbanAccount(String ibanAccount) {
        log.info("Get transaction with ibanAccount {}", ibanAccount);
        return this.transactionRepository.findByIbanAccount(ibanAccount);
    }

}
