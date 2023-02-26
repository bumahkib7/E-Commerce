package org.codeai.ecommerce.utility;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";

    private static final Key KEY;

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            KEY = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            byte[] encrypted = cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
            return new String(encrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decrypted = cipher.doFinal(dbData.getBytes(StandardCharsets.UTF_8));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
