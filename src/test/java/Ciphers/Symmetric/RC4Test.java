package Ciphers.Symmetric;

import Ciphers.Cipher;
import org.junit.jupiter.api.Test;

class RC4Test {

    @Test
    void testRC4() {
        String message = "This is a secret message";
        String keyword = "lemon";

        Cipher symmetricCipher = new RC4(keyword);
        String encrypted = symmetricCipher.encrypt(message);
        System.out.println("RC4 Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher.decrypt(encrypted));
    }
}