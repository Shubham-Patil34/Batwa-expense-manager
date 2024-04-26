package com.shubhampatil34.BatwaExpenseManager.repository;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByBatwa(Batwa batwa);
List<Transaction> findByBatwaOrToBatwaOrderByDateDesc(Batwa batwa, Batwa toBatwa);
}
