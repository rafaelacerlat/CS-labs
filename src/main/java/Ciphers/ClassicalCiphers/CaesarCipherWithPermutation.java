package Ciphers.ClassicalCiphers;

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
