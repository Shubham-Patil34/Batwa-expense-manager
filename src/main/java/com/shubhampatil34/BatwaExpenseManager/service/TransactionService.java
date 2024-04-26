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
            List<Transaction> listOfTransactions = (List<Transaction>) transactionRepository.findByBatwaOrToBatwaOrderByDateDesc(batwa.get(), batwa.get());
            List<TransactionDTO> listOfTransactionDTO = new ArrayList<>();

            for (Transaction transaction : listOfTransactions) {
                TransactionDTO transactionDTO = transactionConverter.convertEntityToDTO(transaction);
                listOfTransactionDTO.add(transactionDTO);
            }

            return listOfTransactionDTO;
        }
        throw new BatwaException("Batwa doesn't exists for id: " + batwaId);
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
            if (transactionDTO.getToBatwaId() != null) {
                batwa = batwaRepository.findById(transactionDTO.getToBatwaId());
                if (batwa.isPresent()) {
                    transaction.setToBatwa(batwa.get());
                    Batwa toUpdateBatwa = batwa.get();
                    toUpdateBatwa.setCurrentBalance(toUpdateBatwa.getCurrentBalance() + transaction.getAmount());
                    batwaRepository.save(toUpdateBatwa);
                }
            }

            Batwa toUpdateBatwa = fromBatwa.get();
            Double newBalance = switch (transaction.getType()) {
                case 1 -> toUpdateBatwa.getCurrentBalance() + transaction.getAmount();
                case 2, 3 -> toUpdateBatwa.getCurrentBalance() - transaction.getAmount();
                default -> toUpdateBatwa.getCurrentBalance();
            };

            toUpdateBatwa.setCurrentBalance(newBalance);
            batwaRepository.save(toUpdateBatwa);
            if (transactionDTO.getId() != null) {
                this.delete(transaction.getBatwa().getId(), transactionDTO.getId());
            }

            transactionDTO = transactionConverter.convertEntityToDTO(transactionRepository.save(transaction));
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
                Double newBalance = switch (transaction1.getType()) {
                    case 1 -> toUpdateBatwa.getCurrentBalance() - transaction1.getAmount();
                    case 2, 3 -> toUpdateBatwa.getCurrentBalance() + transaction1.getAmount();
                    default -> toUpdateBatwa.getCurrentBalance();
                };
                toUpdateBatwa.setCurrentBalance(newBalance);
                batwaRepository.save(toUpdateBatwa);

                if (transaction1.getType() == 3) {
                    toUpdateBatwa = transaction1.getToBatwa();
                    toUpdateBatwa.setCurrentBalance(toUpdateBatwa.getCurrentBalance() - transaction1.getAmount());
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
}