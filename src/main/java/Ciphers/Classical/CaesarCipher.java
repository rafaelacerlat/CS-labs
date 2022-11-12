package Ciphers.Classical;

public class CaesarCipher extends SubstitutionCipher{

    private final int key;

    public CaesarCipher(int key)
    {
        this.key = key;
    }
    @Override
    protected Character encryptCharacter(Character currentChar) {
        return (char) (((currentChar + key - 65) % 26) + 65);
    }

    @Override
    protected Character decryptCharacter(Character currentChar) {
        return (char) (((currentChar + key - 65) % 26) + 65);
    }
}
