package com.paymentchain.transaction.repository;

import com.paymentchain.transaction.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.ibanAccount = ?1")
    List<Transaction> findByIbanAccount(String ibanAccount);

}
