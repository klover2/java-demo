package com.klover.kms;

import org.junit.jupiter.api.Test;

class KmsTest {
    @Test
    void kms() {
        String encryptedMessage = Kms.encrypt("test message");
        System.out.println(encryptedMessage);
        String decrypt = Kms.decrypt(encryptedMessage);
        System.out.println(decrypt);
    }
}