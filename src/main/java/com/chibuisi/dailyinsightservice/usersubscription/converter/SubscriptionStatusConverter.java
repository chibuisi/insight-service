package com.chibuisi.dailyinsightservice.usersubscription.converter;

import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SubscriptionStatusConverter implements AttributeConverter<SubscriptionStatus, String> {

    @Override
    public String convertToDatabaseColumn(SubscriptionStatus status) {
        if(status == null)
            return null;
        return status.getName();
    }

    @Override
    public SubscriptionStatus convertToEntityAttribute(String status) {
        if(status == null)
            return null;
        return Stream.of(SubscriptionStatus.values())
                .filter(s -> s.getName().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
