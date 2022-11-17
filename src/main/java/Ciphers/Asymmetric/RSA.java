package Ciphers.Asymmetric;

import Ciphers.Cipher;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RSA implements Cipher {

    private final int number;
    private BigInteger  n, e, d;

    public RSA(int number) {
        this.number = number;
        generateKey();
    }

    public void generateKey(){
        // Select two large prime numbers, x and y
        BigInteger x = BigInteger.probablePrime(number/2, new SecureRandom());
        BigInteger y = BigInteger.probablePrime(number/2, new SecureRandom());
        n = x.multiply(y);

        BigInteger totient = (x.subtract(BigInteger.ONE)).multiply(y.subtract(BigInteger.ONE));

        // The pair of numbers (n,e) makes up the public key.
        e = coprime(totient, number);

        // The pair (n,d) makes up the private key.
        d = extendedEuclidean(e, totient)[1];
    }

    private static BigInteger gcd(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)){
            return  a;
        }else{
            return gcd(b,a.mod(b));
        }
    }

    private static BigInteger coprime(BigInteger totient, int number){
        Random random = ThreadLocalRandom.current();
        BigInteger e;
        do {
            e = new BigInteger(number, random);
        } while (e.min(totient).equals(totient) & !gcd(totient, e).equals(BigInteger.ONE));

        return e;
    }

    private static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO))
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        BigInteger[] values = extendedEuclidean(b, a.mod(b));
        BigInteger x = values[0];
        BigInteger y = values[2];
        BigInteger z =  values[1].subtract(a.divide(b).multiply(y));
        return new BigInteger[]{x, y, z};
    }


    @Override
    public String encrypt(String message) {
        BigInteger encrypted = new BigInteger(message.getBytes()).modPow(e,n);
        return  Base64.getEncoder().encodeToString(encrypted.toByteArray()) ;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        BigInteger decrypted = new BigInteger(Base64.getDecoder().decode(encryptedMessage)).modPow(d,n);
        return new String(decrypted.toByteArray());
    }
}
