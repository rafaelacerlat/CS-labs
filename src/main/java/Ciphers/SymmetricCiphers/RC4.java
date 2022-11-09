package Ciphers.SymmetricCiphers;

import java.util.Base64;


public class RC4 implements SymmetricCipher {
    private final byte[] S = new byte[256];
    private final byte[] K = new byte[256];


    private void scheduleKey(String keyword){

        byte[] key = new byte[keyword.length()];

        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            K[i] = key[i % keyword.length()]; // elements of the key array are repeated
        }

        // key scheduling algorithm
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) & 0xFF; // mod 256
            // swap S[i] and S[j]
            swap(i, j, S);
        }
    }

    // pseudo-random generation algorithm
    private byte[] PRGA(int plaintextLength) {

        final byte[] keyStream = new byte[plaintextLength];

        int i = 0, j = 0, t;
        for (int counter = 0; counter < plaintextLength; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;

            // swap S[i] and S[j]
            swap(i, j, S);

            t = (S[i] + S[j]) & 0xFF;
            keyStream[counter] = S[t];
        }
        return keyStream;
    }

    private void swap(int i, int j, byte[] S) {
        byte tmp = S[j];
        S[j] = S[i];
        S[i] = tmp;
    }

    public byte[] performAlgorithm(byte[] text, String key){

        byte[] ciphertext = new byte[text.length];

        scheduleKey(key);
        final byte[] keyStream = PRGA(text.length);
        for(int i = 0; i < text.length; i++){
            ciphertext[i] = (byte) (text[i] ^ keyStream[i]);
        }

        return ciphertext;
    }

    @Override
    public String encrypt(String message, String key) {
        byte[] text = message.getBytes();
        byte[] encrypted = performAlgorithm(text, key);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public String decrypt(String encryptedMessage, String key) {
        byte[] text = Base64.getDecoder().decode(encryptedMessage);
        return new String(performAlgorithm(text, key));
    }

}
