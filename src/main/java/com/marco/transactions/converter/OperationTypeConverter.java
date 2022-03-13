package com.marco.transactions.converter;

import com.marco.transactions.domain.OperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, Long> {


    @Override
    public Long convertToDatabaseColumn(OperationType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getOperationTypeId();
    }

    @Override
    public OperationType convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(OperationType.values())
                .filter(c -> c.getOperationTypeId() == dbData)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
