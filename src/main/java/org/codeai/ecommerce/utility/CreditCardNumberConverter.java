package org.codeai.ecommerce.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardNumberConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return EncryptionUtils.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting credit card number", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return EncryptionUtils.decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting credit card number", e);
        }
    }

}
