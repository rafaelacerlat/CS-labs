package Ciphers.ClassicalCiphers;

public interface ClassicalCipher {
    int ALPHABET_SIZE = 26;
    String encrypt(String message);
    String decrypt(String encryptedMessage);
}
