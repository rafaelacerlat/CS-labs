package Ciphers;

import Ciphers.ClassicalCiphers.*;
import Ciphers.SymmetricCiphers.BlockCipher;
import Ciphers.SymmetricCiphers.RC4;
import Ciphers.SymmetricCiphers.StreamCipher;
import Ciphers.SymmetricCiphers.SymmetricCipher;

public class Main {

    public static void main(String[] args) {

        String message = "This is a secret message";
        String keyword = "lemon";
//        int key = 13;
        String encrypted;
//
//        System.out.println("The plaintext: " + message);
//
//        ClassicalCipher classicalCipher1 = new CaesarCipher(13);
//        encrypted = classicalCipher1.encrypt(message);
//
//        System.out.println("\nCaesar Cipher:");
//        System.out.println("Encrypted: " + encrypted);
//        System.out.println("Decrypted: " + classicalCipher1.decrypt(encrypted) + "\n");
//
//        ClassicalCipher classicalCipher2 = new VigenereCipher(message, keyword);
//        encrypted = classicalCipher2.encrypt(message);
//
//        System.out.println("Vigenere Cipher:");
//        System.out.println("Encrypted: " + encrypted);
//        System.out.println("Decrypted: " + classicalCipher2.decrypt(encrypted));
//
//        ClassicalCipher classicalCipher3 = new CaesarCipherWithPermutation(key, keyword);
//        encrypted = classicalCipher3.encrypt(message);
//
//        System.out.println("\nCaesar Cipher with permutation:");
//        System.out.println("Encrypted: " + encrypted);
//        System.out.println("Decrypted: " + classicalCipher3.decrypt(encrypted));
//
//        ClassicalCipher classicalCipher4 = new PlayfairCipher(keyword);
//        encrypted = classicalCipher4.encrypt(message);
//
//        System.out.println("Encrypted: " + encrypted);
//        System.out.println("Decrypted: " + classicalCipher4.decrypt(encrypted));
//
//
//
//        ClassicalCipher classicalCipher5 = new StreamCipher(message, keyword);
//        encrypted = classicalCipher5.encrypt(message);
//
//        System.out.println("\nStream Cipher:");
//        System.out.println("Encrypted: " + encrypted);
//
//        System.out.println("Decrypted: " + classicalCipher5.decrypt(encrypted));

        SymmetricCipher symmetricCipher = new RC4();
        encrypted = symmetricCipher.encrypt(message, keyword);
        System.out.println("RC4 Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher.decrypt(encrypted, keyword));

        SymmetricCipher symmetricCipher1 = new BlockCipher();
        encrypted = symmetricCipher1.encrypt(message, keyword);
        System.out.println("DES Encrypted: " + encrypted);
        System.out.println("Decrypted: " + symmetricCipher1.decrypt(encrypted, keyword));




    }
}
