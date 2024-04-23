package com.shubhampatil34.BatwaExpenseManager.converter;

import com.shubhampatil34.BatwaExpenseManager.dto.TransactionDTO;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    public Transaction convertDTOtoEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        if (transactionDTO.getId() != null) {
            transaction.setId(transactionDTO.getId());
        }
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setType(transactionDTO.getType());
        transaction.setDate(transactionDTO.getDate());

        return transaction;
    }

    public TransactionDTO convertEntityToDTO(Transaction transaction) {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setBatwaId(transaction.getBatwa().getId());
        if (transaction.getToBatwa() != null)
            transactionDTO.setToBatwaId(transaction.getToBatwa().getId());

        return transactionDTO;
    }
}
