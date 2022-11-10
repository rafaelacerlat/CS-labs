# Topic: Symmetric Ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography & Security
### Author: Rafaela Cerlat

----

## Theory
Symmetric encryption is a type of encryption that uses the same key to encrypt and decrypt data. Both the sender and the recipient have identical copies of the key, which they keep secret and don’t share with anyone.
Block ciphers and stream ciphers are two separate methods of encrypting data with symmetric encryption algorithms:

* Encrypting information in chunks. A block cipher breaks down plaintext messages into fixed-size blocks before converting them into ciphertext using a key.
* Encrypting information bit-by-bit. A stream cipher, on the other hand, breaks a plaintext message down into single bits, which then are converted individually into ciphertext using key bits.

#### RC4 - Rivest Cipher 4
This is a stream cipher and variable-length key algorithm. This algorithm encrypts one byte at a time (or larger units at a time).
A key input is pseudorandom bit generator that produces a stream 8-bit number that is unpredictable without knowledge of input key, The output of the generator is called key-stream, is combined one byte at a time with the plaintext stream cipher using X-OR operation.

RC4 relies on two mathematical concepts:
* KSA: A key-scheduling algorithm initializes the process in an array typically referred to as "S." That "S" is processed 256 times, and bytes from the key are mixed in too. 
* PRGA: Data is fed in byte by byte, and a mathematical model modifies it. The model looks up values, add them to 256, and uses the sum as the byte within the keystream. It swaps each element with another at least once every 256 rounds. 

RC4 encryption algorithms are easy to use, do not require an exuberant amount of memory to operate, and are implemented on large data streams. However, RC4 encryption does not provide authentication, which can compromise security.

#### DES - Data Encryption Standard
This is a block cipher algorithm that takes plain text in blocks of 64 bits and converts them to ciphertext using keys of 48 bits. There are 16 rounds of encryption in the algorithm, and a different key is used for each round.

The algorithm process breaks down into the following steps:
* The process begins with the 64-bit plain text block getting handed over to an initial permutation (IP) function.
* The initial permutation (IP) is then performed on the plain text.
* Next, the initial permutation (IP) creates two halves of the permuted block, referred to as Left Plain Text (LPT) and Right Plain Text (RPT).
* Each LPT and RPT goes through 16 rounds of the encryption process.
* Finally, the LPT and RPT are rejoined, and a Final Permutation (FP) is performed on the newly combined block.
* The result of this process produces the desired 64-bit ciphertext.

The encryption process step (step 4, above) is further broken down into five stages:
* Key transformation
* Expansion permutation
* S-Box permutation
* P-Box permutation
* XOR and swap

For decryption, we use the same algorithm, and we reverse the order of the 16 round keys.

## Objectives:
1. Get familiar with the symmetric cryptography, stream and block ciphers.

2. Implement an example of a stream cipher.

3. Implement an example of a block cipher.

4. Structure the project in methods/classes/packages as needed.


## Implementation description

* Firt of all there is the SymmetricCipher interface with the respective methods, encrypt() and decrypt(), that take as parameters the message and the key to either encrypt or decrypt.
```
public interface SymmetricCipher {
    String encrypt(String message, String key);
    String decrypt(String encryptedMessage, String key);
}
```


* For RC4 a state array S of 256 bytes is initialized and a temporary array T is generated where the elements of the key array are copied into it as shown below:
```
byte[] key = new byte[keyword.length()];

        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            K[i] = key[i % keyword.length()]; // elements of the key array are repeated
        }
```

The main purpose of creating a temporary array is to provide an initial permutation for the S array.

Run the KSA on the altered array. It is used to create different permutations in the defined array S. KSA will use the secret key and the temporary array K to set the values of the S array's entries as follows:
```
 // key scheduling algorithm
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) & 0xFF; // mod 256
            // swap S[i] and S[j]
            swap(i, j, S);
        }
```
After passing through KSA, its output acts as the input for PRGA. It outputs a key based on the state of the array S modified by the KSA algorithm. The code for PRGA is as follows:
```
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
```
The output of PRGA, which is the key stream is XORed with the plain text provided by the user to create a cipher text.
```
byte[] ciphertext = new byte[text.length];

        scheduleKey(key);
        final byte[] keyStream = PRGA(text.length);
        for(int i = 0; i < text.length; i++){
            ciphertext[i] = (byte) (text[i] ^ keyStream[i]);
        }
```


* For DES the perform() method encrypts or decrypts based on the given boolean parameter.
```
 public String perform(String text, String key, boolean decryption){}
```
This method goes through the following steps:
1. It builds the key schedule;
2. Reverses the keys in case the method is used for decryption;
3. Adds padding to the message(already tranfformed in binary) if neccesary;
4. Separates the binary message into blocks;
5. Encrypts/decrypts each block;
6. Builds the encrypted/decrypted text from the blocks;
7. Destroys the key schedule.

So the buildKeySchedule() method uses a hash() function for the key and then converts it to binary, adding leading zeros if the key length is less than 64.
Then the key is compressed and transposed from a 64-bit key into a 56-bit key using the PC1 table.
```
for (int j : PC1) binaryKey_PC1 = binaryKey_PC1 + binaryKey.charAt(j - 1);
```
The result is divided into 2 equal parts, that are left-shifted circularly in 16 rounds. For encryption rounds 1, 2, 9, and 16 they are left shifted circularly by 1 bit; for all of the other rounds, they are left-circularly shifted by 2.
```
 // Split permuted string in half | 56/2 = 28
        String leftString = binaryKey_PC1.substring(0, 28);
        String rightString = binaryKey_PC1.substring(28);

        // Parse binary strings into integers for shifting
        int leftInteger = Integer.parseInt(leftString, 2);
        int rightInteger = Integer.parseInt(rightString, 2);
```
After the 2 parts are shifted according to key shift array, they are merged together and converted to binary.
```
// Perform left shifts according to key shift array
            leftInteger = Integer.rotateLeft(leftInteger, KEY_SHIFTS[i]);
            rightInteger = Integer.rotateLeft(rightInteger, KEY_SHIFTS[i]);

            // Merge the two halves
            long merged = ((long)leftInteger << 28) + rightInteger;

            // 56-bit merged
            String sMerged = Long.toBinaryString(merged);
```
The result is compressed to 48 bits in accordance with the PC2 table.
```
for (int k : PC2) binaryKey_PC2 = binaryKey_PC2 + sMerged.charAt(k - 1);
```

The processBlock() method encrypts/ decrypts the input block...




