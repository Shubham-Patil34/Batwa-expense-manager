package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import com.shubhampatil34.BatwaExpenseManager.repository.BatwaRepository;
import com.shubhampatil34.BatwaExpenseManager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BatwaRepository batwaRepository;

    public List<Transaction> getAll(Long batwaId) {
        Optional<Batwa> batwa = batwaRepository.findById(batwaId);
        if (batwa.isPresent()) {
            return transactionRepository.findByBatwa(batwa.get());
        }
        return null;
    }

    public Transaction createOrUpdate(Long batwaId, Transaction transaction) {
        Optional<Batwa> batwa = batwaRepository.findById(batwaId);
        if (batwa.isPresent()) {
            transaction.setBatwa(batwa.get());
            // TODO - update balance
            Batwa toUpdateBatwa = batwa.get();
            Double oldBalance = toUpdateBatwa.getCurrentBalance();
            Double newBalance = switch (transaction.getType()){
                case 1 -> oldBalance + transaction.getAmount();
                case 2, 3 -> oldBalance - transaction.getAmount();
                default -> oldBalance;
            };
            toUpdateBatwa.setCurrentBalance(newBalance);
            batwaRepository.save(toUpdateBatwa);
            transactionRepository.save(transaction);
            return transaction;
        }
        return null;
    }

}
