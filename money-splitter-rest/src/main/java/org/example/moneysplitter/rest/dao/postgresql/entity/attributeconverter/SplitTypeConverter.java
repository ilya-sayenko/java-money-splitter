package org.example.moneysplitter.rest.dao.postgresql.entity.attributeconverter;

import org.example.moneysplitter.rest.dao.postgresql.entity.SpendingEntity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SplitTypeConverter implements AttributeConverter<SpendingEntity.SplitType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SpendingEntity.SplitType splitType) {
        switch (splitType) {
            case EQUAL:
                return 1;
            case AMOUNT:
                return 2;
            case PARTITION:
                return 3;
            default:
                return null;
        }
    }

    @Override
    public SpendingEntity.SplitType convertToEntityAttribute(Integer id) {
        switch (id) {
            case 1:
                return SpendingEntity.SplitType.EQUAL;
            case 2:
                return SpendingEntity.SplitType.AMOUNT;
            case 3:
                return SpendingEntity.SplitType.PARTITION;
            default:
                return null;
        }
    }
}
