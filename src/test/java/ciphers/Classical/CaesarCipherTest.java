package ciphers.Classical;


import ciphers.Cipher;
import org.junit.jupiter.api.Test;

class CaesarCipherTest {
    @Test
    void name() {
        String message = "This is a secret message";
        int key = 13;

        Cipher classicalCipher = new CaesarCipher(key);
        String encrypted = classicalCipher.encrypt(message);

        System.out.println("\nCaesar Cipher:");
        System.out.println("Encrypted: "+ encrypted);
        System.out.println("Decrypted: "+ classicalCipher.decrypt(encrypted)+"\n");
    }
}