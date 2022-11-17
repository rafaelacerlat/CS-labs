package Ciphers.Asymmetric;

import Ciphers.Cipher;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA implements Cipher {

    private final int number;

    public RSA(int number) {
        this.number = number;
    }

    public void generateKey(){
        // Select two large prime numbers, x and y
        BigInteger x = BigInteger.probablePrime(number/2, new SecureRandom());
        BigInteger y = BigInteger.probablePrime(number/2, new SecureRandom());
        BigInteger n = x.multiply(y);

        BigInteger totient = (x.subtract(BigInteger.ONE)).multiply(y.subtract(BigInteger.ONE));



        // The pair of numbers (n,e) makes up the public key.
        //  The pair (n,d) makes up the private key.


    }

    @Override
    public String encrypt(String message) {
        return null;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        return null;
    }
}
