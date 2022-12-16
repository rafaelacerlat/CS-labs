package ciphers.Asymmetric;

import ciphers.Cipher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RSATest {

    @Test
    void testRSA() {
        String message = "This is a secret message";

        Cipher asymmetricCipher = new RSA(345);
        String encrypted = asymmetricCipher.encrypt(message);
        String decrypted = asymmetricCipher.decrypt(encrypted);

        System.out.println("RC4 Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        assertEquals(message, decrypted);
    }
}