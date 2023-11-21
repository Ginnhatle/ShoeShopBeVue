package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Transaction;
import org.datn.app.core.repo.TransactionRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepo transactionRepo;
    @Override
    public Transaction doInsert(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction doUpdateById(Transaction transaction, Long aLong) {
        Transaction transaction1 = transactionRepo.findById(aLong).get();
        transaction1.setAddress(transaction.getAddress());
        transaction1.setPhoneNumber(transaction.getPhoneNumber());
        return transactionRepo.save(transaction1);
    }

    @Override
    public Transaction doDeleteById(Long aLong) {
        transactionRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Transaction findById(Long aLong) {
        return transactionRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    @Override
    public Page<Transaction> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return transactionRepo.findAll(pageable);
    }
}
