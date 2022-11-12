package Ciphers.Classical;

import Ciphers.Cipher;

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
