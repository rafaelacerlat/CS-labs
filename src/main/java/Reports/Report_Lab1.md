# Topic: Intro to Cryptography. Classical ciphers. 

### Course: Cryptography & Security
### Author: Rafaela Cerlat

----

## Theory

The Caesar cipher, also known as the shift cipher, is one that anyone can readily understand and remember for decoding. It is a form of the substitution cipher. By shifting the alphabet a few positions in either direction, a simple sentence can become unreadable to casual inspection.


The Vigenère cipher is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword. It employs a form of polyalphabetic substitution. Vigenere is a cipher based on substitution, using multiple substitution alphabets. The encryption of the original text is done using the Vigenère square or Vigenère table, which consists of the alphabet written out 26 times in different rows, each alphabet shifted cyclically to the left compared to the previous alphabet, corresponding to the 26 possible Caesar Ciphers.


The Playfair cipher is one of the traditional ciphers which comes under the category of substitution ciphers. The cipher's essential method involves creating key tables that arrange the letters of the alphabet into a square grid. With these key tables, the user separates the text of a message into two-letter bits. To encode the message, each two-letter bit is transposed on the 5x5 key table. Thus, to decode the message, the receiver requires the key table itself. Since the table/grid can accommodate only 25 characters, there is no ‘J’ in this table. Any ‘J’ in the plaintext is replaced by ‘I’. 



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

    ...
}
```
* In the VigenereCipher the key is a word or phrase that is repeated as many times as needed for the length of the message/plaintext. So, in the end each letter of the plaintext is paired with a letter from the keyword. Using the Vigenère table, or the algebraic formula we can encrypt and decrypt easily. 
```
public class VigenereCipher extends SubstitutionCipher{

    private String key;
    int j = 0;

    public VigenereCipher(String message, String key)
    {
        int x = message.length();
        key = key.toUpperCase();

        for (int i = 0; ; i++)
        {
            if (x == i)
                i = 0;
            if (key.length() == message.length())
                break;
            key+=(key.charAt(i));
        }
        this.key = key;
    }

    @Override
    protected Character encryptCharacter(Character currentChar) {
        char res = (char) (((currentChar + key.charAt(j)) % ALPHABET_SIZE) + 65);
        return res;

    }

    @Override
    protected Character decryptCharacter(Character currentChar) {
        char res = (char) (((currentChar - key.charAt(j) + ALPHABET_SIZE) % ALPHABET_SIZE) + 65);
        return res;
    }
}
```

* The PlayfairCipher implements the Cipher interface. It is similar to the other traditional ciphers, the only difference is that it encrypts a digraph (a pair of two letters) instead of a single letter. First, we need the key table alphabet, so starting in the constructor method:
 1. if the keyword contains ‘J’, then it is replaced by ‘I’;
 2. all the duplicate characters are removed;
 3. the rest of the letters are included, besides ‘J’;
 4. the new formed alphabet is distributed into the 5x5 table.
```
    String key;
    char[][] matrix = new char[5][5];
    private String newAlphabet = "";

    public PlayfairCipher(String key)
    {
        // convert all the characters to uppercase
        // if the keyword contains ‘J’, then it is replaced by ‘I’
        this.key = key.toUpperCase().replaceAll("J", "I");

        // remove duplicate characters from string
        this.key.chars().distinct().forEach(c -> newAlphabet += ((char) c));

        // add the rest of letters from alphabet, besides 'J'
        for ( char c = 'A'; c <= 'Z'; ++c){
            if (!newAlphabet.contains(Character.toString(c)) && c != 'J')
                newAlphabet += c;
        }

        // create cipher key table
        for (int i = 0, idx = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                matrix[i][j] = newAlphabet.charAt(idx++);
        }

        System.out.println("\nPlayfair Cipher Key Matrix:");

        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(matrix[i]));
    }
```

Before encrypting the text, we need to divide the plaintext into digraphs – pairs of two letters. In the case of plaintext with an odd number of letters, add the letter ‘Z’ to the last letter. If there are any double letters in the plaintext, replace the second occurrence of the letter with ‘X’ or ‘Z’ if the original letter is X,  e.g., ‘better’ -> ‘betxer’.
The preprocessMessage() method takes the original message/plaintext and does just that. Using the method isOdd() it checks to add the ‘Z’ at the end. Then using the method insertBogusChar() it solves the problem of any double letters in the plaintext.
```
 private String preprocessMessage(String message)
    {
         message = message.replaceAll("\\s", "").toUpperCase();

        int i = 0;

        for (;; i += 2)
        {
            if (i >= message.length() - 1) break;

            if (message.charAt(i) == message.charAt(i + 1))
            {
                message = insertBogusChar(message, i);
                i++;
            }
        }

        if (isOdd(message.length()))
        {
            message = message + "Z";
        }

        return message;
    }

    private String insertBogusChar(final String currentString, final int position)
    {
        final char bogusChar = (currentString.charAt(position) == 'X' ? 'Z' : 'X');

        return currentString.substring(0, position) + bogusChar + currentString.substring(position);
    }

    private boolean isOdd(final int number)
    {
        return number % 2 == 1;
    }
```

Then there's the splitToNChar() method that splits the text into digraphs:

```
    private String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }
```

The encrypt() method goes through each digraph and gets the position in the table of the two letters using the getCharPos(). Then following the rules:
 1. If both the letters are in the same row - replace each letter of the digraph with the letters immediately to their right. If there is no letter to the right, consider the first letter of the same row as the right letter.
 2. If both the letters are in the same column - replace each letter of the digraph with the letters immediately below them. If there is no letter below, wrap around to the top of the same column. 
 3. If both the letters are in different rows and column - form a rectangle with the two letters in the digraph and consider the rectangle’s horizontal opposite corners.

```
 public String encrypt(String message) {
        String[] msgPairs = splitToNChar(preprocessMessage(message), 2);
        String encText = "";

        for (int i = 0; i < msgPairs.length; i++)
        {
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }

            // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }

            // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // get the corresponding cipher characters from
            // the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }
        return encText;
    }
 ```
 The decryption procedure is the same as encryption but the steps are applied in reverse order. The receiver of the encrypted message has the same key and can create the same key-table that is used to decrypt the message.

## Conclusions / Screenshots / Results
Classical ciphers are part of history and they range in complexity from very simple to very complex. None of these algorithms are very secure as far as protecting information goes, because today's computers can break any of them with relative ease. But, they can still provide a quick and fascinating way to encrypt text just for fun. In the end, for me the most interesting was the Playfair Cipher. 

![image](https://user-images.githubusercontent.com/41265306/192862791-9c140d2d-ec42-47ee-a292-174489dd0058.png)


