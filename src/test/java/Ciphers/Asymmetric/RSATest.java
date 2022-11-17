package Ciphers.Asymmetric;

import Ciphers.Cipher;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

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