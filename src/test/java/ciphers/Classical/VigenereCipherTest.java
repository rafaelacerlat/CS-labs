package ciphers.Classical;

import ciphers.Cipher;
import org.junit.jupiter.api.Test;

class VigenereCipherTest {
    @Test
    void name() {
        String message = "DRAGON";
        String keyword = "XO";

        Cipher classicalCipher2 = new VigenereCipher(message, keyword);
        String encrypted = classicalCipher2.encrypt(message);

        System.out.println("Vigenere Cipher:");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + classicalCipher2.decrypt(encrypted));
    }
}