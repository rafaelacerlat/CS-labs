package Ciphers.ClassicalCiphers;


import org.junit.jupiter.api.Test;

class CaesarCipherWithPermutationTest {
    @Test
    void name() {
        String message = "This is a secret message";
        String keyword = "lemon";
        int key = 13;

        ClassicalCipher classicalCipher3 = new CaesarCipherWithPermutation(key, keyword);
        String encrypted = classicalCipher3.encrypt(message);

        System.out.println("\nCaesar Cipher with permutation:");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + classicalCipher3.decrypt(encrypted));
    }
}