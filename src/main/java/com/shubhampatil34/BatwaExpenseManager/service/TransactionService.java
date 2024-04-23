package com.shubhampatil34.BatwaExpenseManager.service;

import com.shubhampatil34.BatwaExpenseManager.converter.TransactionConverter;
import com.shubhampatil34.BatwaExpenseManager.dto.TransactionDTO;
import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.repository.BatwaRepository;
import com.shubhampatil34.BatwaExpenseManager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BatwaRepository batwaRepository;

    @Autowired
    private TransactionConverter transactionConverter;

    private Optional<Batwa> batwa;

    public List<TransactionDTO> getAll(Long batwaId) {
        Optional<Batwa> batwa = batwaRepository.findById(batwaId);
        if (batwa.isPresent()) {
//            return transactionRepository.findByBatwa(batwa.get());
            List<Transaction> listOfTransactions = (List<Transaction>) transactionRepository.findByBatwaOrToBatwa(batwa.get(), batwa.get());
            List<TransactionDTO> listOfTransactionDTO = new ArrayList<>();

            for (Transaction transaction : listOfTransactions) {
                TransactionDTO transactionDTO = transactionConverter.convertEntityToDTO(transaction);
                listOfTransactionDTO.add(transactionDTO);
            }

            return listOfTransactionDTO;
        }
        return null;
    }

    public TransactionDTO getById(Long batwaId, Long transactionId) {
        Optional<Batwa> batwa = batwaRepository.findById(batwaId);
        if (batwa.isPresent()) {
            Optional<Transaction> transaction = transactionRepository.findById(transactionId);
            if (transaction.isPresent()) {
                return transactionConverter.convertEntityToDTO(transaction.get());
            }
            throw new BatwaException("Transaction doesn't exists for id: " + transactionId);
        }
        throw new BatwaException("Batwa doesn't exists for id: " + batwaId);
    }

    public TransactionDTO createOrUpdate(TransactionDTO transactionDTO) {
        Transaction transaction = transactionConverter.convertDTOtoEntity(transactionDTO);
        Optional<Batwa> fromBatwa = batwaRepository.findById(transactionDTO.getBatwaId());

        if (fromBatwa.isPresent()) {
            transaction.setBatwa(fromBatwa.get());

            // TODO - transfer transaction
            Batwa toUpdateBatwa = fromBatwa.get();
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

            if (transaction.getType() == 3) {
                Optional<Batwa> toBatwa = batwaRepository.findById(transactionDTO.getToBatwaId());
                if (toBatwa.isPresent()) {
                    transaction.setToBatwa(toBatwa.get());

                    toUpdateBatwa = toBatwa.get();
                    newBalance = getUpdatedBalance(toUpdateBatwa.getCurrentBalance(),
                            oldTransactionAmount,
                            transaction.getAmount(),
                            1);
                    toUpdateBatwa.setCurrentBalance(newBalance);
                    batwaRepository.save(toUpdateBatwa);
                } else {
                    throw new BatwaException("Batwa doesn't exists for id: " + transactionDTO.getToBatwaId());
                }
            }

            transactionDTO = transactionConverter.convertEntityToDTO(transaction);
            transactionRepository.save(transaction);
            return transactionDTO;
        }
        return null;
    }

    public boolean delete(Long batwaId, Long transactionId) {
        Optional<Batwa> batwa = batwaRepository.findById(batwaId);
        if (batwa.isPresent()) {
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

                if (transaction1.getType() == 3) {
                    toUpdateBatwa = transaction1.getToBatwa();

                    newBalance = getUpdatedBalance(toUpdateBatwa.getCurrentBalance(),
                            transaction1.getAmount(),
                            0.0,
                            1);
                    toUpdateBatwa.setCurrentBalance(newBalance);
                    batwaRepository.save(toUpdateBatwa);
                }

                transactionRepository.delete(transaction1);
                return true;
            }
            throw new BatwaException("Transaction doesn't exists for id: " + transactionId);
        }
        throw new BatwaException("Batwa doesn't exists for id: " + batwaId);
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
