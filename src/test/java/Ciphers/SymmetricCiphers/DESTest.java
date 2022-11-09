package Ciphers.SymmetricCiphers;


import org.junit.jupiter.api.Test;

class DESTest {
    @Test
    void testDES() {
        String message = "This is a secret message";
        String keyword = "lemon";

        SymmetricCipher symmetricCipher1 = new DES();
        String encrypted = symmetricCipher1.encrypt(message, keyword);
        System.out.println("DES Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher1.decrypt(encrypted, keyword));
    }
}