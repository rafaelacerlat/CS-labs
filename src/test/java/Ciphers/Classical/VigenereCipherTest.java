package Ciphers.Classical;

import Ciphers.Cipher;
import org.junit.jupiter.api.Test;

class VigenereCipherTest {
    @Test
    void name() {
        String message = "This is a secret message";
        String keyword = "lemon";

        Cipher classicalCipher2 = new VigenereCipher(message, keyword);
        String encrypted = classicalCipher2.encrypt(message);

        System.out.println("Vigenere Cipher:");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + classicalCipher2.decrypt(encrypted));
    }
}