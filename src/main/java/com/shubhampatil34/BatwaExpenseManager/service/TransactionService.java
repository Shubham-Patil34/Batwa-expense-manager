package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
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
            // TODO - transfer transaction
            Batwa toUpdateBatwa = batwa.get();
            Double oldTransactionAmount = transaction.getId() == null ?
                    0.0 :
                    (transactionRepository.findById(transaction.getId()).isPresent() ?
                            transactionRepository.findById(transaction.getId()).get().getAmount() :
                            0.0);
            Double newBalance = getUpdatedBalance(toUpdateBatwa.getCurrentBalance(),
                    oldTransactionAmount,
                    transaction.getAmount(),
                    transaction.getType());

            toUpdateBatwa.setCurrentBalance(newBalance);
            batwaRepository.save(toUpdateBatwa);
            transactionRepository.save(transaction);
            return transaction;
        }
        return null;
    }

    public boolean delete(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction transaction1 = transaction.get();
            Batwa toUpdateBatwa = transaction1.getBatwa();
            Double newBalance = getUpdatedBalance(toUpdateBatwa.getCurrentBalance(),
                    transaction1.getAmount(),
                    0.0,
                    transaction1.getType());
            toUpdateBatwa.setCurrentBalance(newBalance);
            batwaRepository.save(toUpdateBatwa);
            transactionRepository.delete(transaction1);
            return true;
        }
        throw new BatwaException("Transaction doesn't exists for id: " + transactionId);
    }

    public boolean isExists(Long id) {
        return transactionRepository.findById(id).isPresent();
    }

    private Double getUpdatedBalance(Double currentBalance, Double oldTransactionAmount, Double newTransactionAmount, int transactionType) {
        return switch (transactionType) {
            case 1 -> currentBalance - oldTransactionAmount + newTransactionAmount;
            case 2, 3 -> currentBalance + oldTransactionAmount - newTransactionAmount;
            default -> currentBalance;
        };
    }


}
