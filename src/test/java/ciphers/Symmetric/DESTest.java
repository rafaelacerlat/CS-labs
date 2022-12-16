package ciphers.Symmetric;


import ciphers.Cipher;
import org.junit.jupiter.api.Test;

class DESTest {
    @Test
    void testDES() {
        String message = "This is a secret message";
        String keyword = "lemon";

        Cipher symmetricCipher1 = new DES(keyword);
        String encrypted = symmetricCipher1.encrypt(message);
        System.out.println("DES Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher1.decrypt(encrypted));
    }
}