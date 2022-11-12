package Ciphers.Classical;

import Ciphers.Cipher;
import org.junit.jupiter.api.Test;


class PlayfairCipherTest {

    @Test
    void name() {
        String message = "This is a secret message";
        String keyword = "lemon";

        Cipher classicalCipher4 = new PlayfairCipher(keyword);
        String encrypted = classicalCipher4.encrypt(message);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + classicalCipher4.decrypt(encrypted));
    }
}