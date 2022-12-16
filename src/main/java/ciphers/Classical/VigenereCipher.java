package ciphers.Classical;

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
