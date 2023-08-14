package org.example.moneysplitter.party.dao.postgresql.entity.attributeconverter;

import org.example.moneysplitter.party.dao.postgresql.entity.TransactionEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionEntity.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TransactionEntity.Status status) {
        switch (status) {
            case PENDING:
                return 1;
            case CLOSED:
                return 2;
            default:
                return null;
        }
    }

    @Override
    public TransactionEntity.Status convertToEntityAttribute(Integer id) {
        switch (id) {
            case 1:
                return TransactionEntity.Status.PENDING;
            case 2:
                return TransactionEntity.Status.CLOSED;
            default:
                return null;
        }
    }
}
