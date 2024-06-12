package com.jaredsantiag.backendcartapp.helpers;


import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import org.jasypt.encryption.StringEncryptor;

public class EncriptorHelper {
    private final StringEncryptor stringEncryptor;

    public EncriptorHelper(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public PaymentMethod decryptPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethod.setCardNumber(stringEncryptor.decrypt(paymentMethod.getCardNumber()));
        paymentMethod.setMonthExpiration(stringEncryptor.decrypt(paymentMethod.getMonthExpiration()));
        paymentMethod.setYearExpiration(stringEncryptor.decrypt(paymentMethod.getYearExpiration()));
        paymentMethod.setSecurityCode(stringEncryptor.decrypt(paymentMethod.getSecurityCode()));
        return paymentMethod;
    }

    public PaymentMethod encryptPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethod.setCardNumber(stringEncryptor.encrypt(paymentMethod.getCardNumber()));
        paymentMethod.setMonthExpiration(stringEncryptor.encrypt(paymentMethod.getMonthExpiration()));
        paymentMethod.setYearExpiration(stringEncryptor.encrypt(paymentMethod.getYearExpiration()));
        paymentMethod.setSecurityCode(stringEncryptor.encrypt(paymentMethod.getSecurityCode()));
        return paymentMethod;
    }
}
