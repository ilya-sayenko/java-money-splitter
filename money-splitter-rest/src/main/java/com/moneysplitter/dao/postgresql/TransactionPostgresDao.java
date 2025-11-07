package com.moneysplitter.dao.postgresql;

import com.moneysplitter.dao.TransactionDao;
import com.moneysplitter.dao.postgresql.entity.TransactionEntity;
import com.moneysplitter.dao.postgresql.repository.TransactionRepository;
import com.moneysplitter.mapper.TransactionMapper;
import com.moneysplitter.model.PartyTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionPostgresDao implements TransactionDao {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    @Override
    public void saveTransactions(List<PartyTransaction> transactions) {
        transactionRepository.saveAll(transactionMapper.toEntities(transactions));
    }

    @Override
    public PartyTransaction saveTransaction(PartyTransaction transaction) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        transactionRepository.save(transactionEntity);

        return transactionMapper.fromEntity(transactionEntity);
    }

    @Override
    public void deleteTransactionsByPartyId(UUID partyId) {
        transactionRepository.deleteByPartyId(partyId);
    }

    @Override
    public List<PartyTransaction> findTransactionsByPartyId(UUID partyId) {
        return transactionMapper.fromEntity(transactionRepository.findByPartyId(partyId));
    }

    @Override
    public Optional<PartyTransaction> findTransactionById(UUID id) {
        return transactionRepository.findById(id).map(transactionMapper::fromEntity);
    }
}
