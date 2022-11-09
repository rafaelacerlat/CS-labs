package Ciphers.SymmetricCiphers;

import org.junit.jupiter.api.Test;

class RC4Test {

    @Test
    void testRC4() {
        String message = "This is a secret message";
        String keyword = "lemon";

        SymmetricCipher symmetricCipher = new RC4();
        String encrypted = symmetricCipher.encrypt(message, keyword);
        System.out.println("RC4 Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher.decrypt(encrypted, keyword));
    }
}