package Lab1;

import Lab1.Ciphers.CaesarCipher;
import Lab1.Ciphers.CaesarCipherWithPermutation;
import Lab1.Ciphers.PlayfairCipher;
import Lab1.Ciphers.VigenereCipher;

public class Main {

    public static void main(String[] args) {

        String message = "This is a secret message";
        String keyword = "lemon";
        int key = 13;
        String encrypted;

        Cipher cipher1 = new CaesarCipher(13);
        encrypted = cipher1.encrypt(message);

        System.out.println(encrypted);
        System.out.println(cipher1.decrypt(encrypted) + "\n");

        Cipher cipher2 = new VigenereCipher(message, keyword);
        encrypted = cipher2.encrypt(message);

        System.out.println(encrypted);
        System.out.println(cipher2.decrypt(encrypted));

        Cipher cipher3 = new CaesarCipherWithPermutation(key, keyword);
        encrypted = cipher3.encrypt(message);

        System.out.println("\n" + encrypted);
        System.out.println(cipher3.decrypt(encrypted));

        Cipher cipher4 = new PlayfairCipher(keyword);
        encrypted = cipher4.encrypt(message);

        System.out.println("\n" + encrypted);
        System.out.println(cipher4.decrypt(encrypted));





    }
}
