package Ciphers;

public interface Cipher {

    String encrypt(String message);
    String decrypt(String encryptedMessage);
}
