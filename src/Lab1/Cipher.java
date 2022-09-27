package Lab1;

public interface Cipher {
    int ALPHABET_SIZE = 26;
    String encrypt(String message);
    String decrypt(String encryptedMessage);
}
