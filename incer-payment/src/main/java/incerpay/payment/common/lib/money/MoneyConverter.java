package incerpay.payment.common.lib.money;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;

@Converter
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(Money attribute) {
        return attribute.toString();
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        return Money.parse(dbData);
    }

}