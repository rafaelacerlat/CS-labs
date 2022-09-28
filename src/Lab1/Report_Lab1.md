# Topic: Intro to Cryptography. Classical ciphers. 

### Course: Cryptography & Security
### Author: Rafaela Cerlat

----

## Theory
If needed and written by the author.


Caesar cipher, also known as the shift cipher, is one that anyone can readily understand and remember for decoding. It is a form of the substitution cipher. By shifting the alphabet a few positions in either direction, a simple sentence can become unreadable to casual inspection.


The Vigenère cipher is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword. It employs a form of polyalphabetic substitution.


## Objectives:
1. Get familiar with the basics of cryptography and classical ciphers.

2. Implement 4 types of the classical ciphers:
    - Caesar cipher with one key used for substitution,
    - Caesar cipher with one key used for substitution, and a permutation of the alphabet,
    - Vigenere cipher,
    - Playfair cipher.

3. Structure the project in methods/classes/packages as needed.


## Implementation description

* Firt of all there is the Cipher interface in which the alphabet size is set to 26(for English), and the abstract methods encrypt() and decrypt(), which are going to be overridden in later class implementations.

```
public interface Cipher {
    int ALPHABET_SIZE = 26;
    String encrypt(String message);
    String decrypt(String encryptedMessage);
}
```

* Then there is the abstract class SubstitutionCipher that implements the Cipher interface. Besides the methods from Cipher, this class has two abstract methods: encryptCharacter() and decryptCharacter() which are responsible for encrypting and decrypting character by character(based on the logic later implemented in each subclass).

 The encrypt() method takes the original message and goes through each character, sending each one to the encryptCharacter() method. 
 The decrypt() method has the same logic.

```
public abstract class SubstitutionCipher implements Cipher {
    @Override
    public String encrypt(String message) {
        final String upperCaseMessage = message.toUpperCase();
        final StringBuilder encryptedMessage = new StringBuilder(upperCaseMessage.length());

        for (char character : upperCaseMessage.toCharArray())
        {
            if (Character.isLetter(character))
            encryptedMessage.append(encryptCharacter(character));
            else encryptedMessage.append(character);
        }

        return encryptedMessage.toString();
    }

    @Override
    public String decrypt(String encryptedMessage) {
        final StringBuilder decryptedMessage = new StringBuilder(encryptedMessage.length());

        for (char character : encryptedMessage.toCharArray())
        {
            if (Character.isLetter(character))
            decryptedMessage.append(decryptCharacter(character));
            else decryptedMessage.append(character);
        }

        return decryptedMessage.toString();
    }

    protected abstract Character encryptCharacter(final Character currentChar);

    protected abstract Character decryptCharacter(final Character currentChar);
}
```

* According to theory Caesar Cipher is a substitution cipher, so the respective class extends SubstitutionCipher. It works using a key, and shifting each characters by the key value. 
```
public class CaesarCipher extends SubstitutionCipher{

    private final int key;

    public CaesarCipher(int key)
    {
        this.key = key;
    }
    @Override
    protected Character encryptCharacter(Character currentChar) {
        return (char) (((currentChar + key - 65) % ALPHABET_SIZE) + 65);
    }

    @Override
    protected Character decryptCharacter(Character currentChar) {
        return (char) (((currentChar + key - 65) % ALPHABET_SIZE) + 65);
    }
}
```
* CaesarCipherWithPermutation is very similar, it just changes the alphabet before using the key to shift the characters/ letters. So, in the code below, in the constructor method the keyword is used to create the newAlphabet, first by removing duplicate characters from the keyword and then adding the rest of the alphabet letter in order. The encryption and decryption work similar to simple Caesar.

```
public class CaesarCipherWithPermutation extends  SubstitutionCipher{
    private final int key;
    private final String keyword;
    private String newAlphabet = "";

    public CaesarCipherWithPermutation(int key, String keyword) {
        this.key = key;
        this.keyword = keyword.toUpperCase();


        // remove duplicate characters from string
        this.keyword.chars().distinct().forEach(c -> newAlphabet += ((char) c));

        // add the rest of letters from alphabet
        for ( char c = 'A'; c <= 'Z'; ++c){
            if (!newAlphabet.contains(Character.toString(c)))
                newAlphabet += c;
        }
    }

    @Override
    protected Character encryptCharacter(Character currentChar) {
        int index = (newAlphabet.indexOf(currentChar) + key)% ALPHABET_SIZE;
        return newAlphabet.charAt(index) ;
    }

    @Override
    protected Character decryptCharacter(Character currentChar) {
        int index = (newAlphabet.indexOf(currentChar) - key + ALPHABET_SIZE) % ALPHABET_SIZE;
        return newAlphabet.charAt(index) ;
    }
}
```
* VigenereCipher 


## Conclusions / Screenshots / Results

