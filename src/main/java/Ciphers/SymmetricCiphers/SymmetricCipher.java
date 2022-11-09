package Ciphers.SymmetricCiphers;

public interface SymmetricCipher {
    String encrypt(String message, String key);
    String decrypt(String encryptedMessage, String key);
}
