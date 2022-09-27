package Lab1.Ciphers;

import Lab1.Cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayfairCipher implements Cipher {
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

    private String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

    // function to get position of character in key table
    public int[] getCharPos(char ch)
    {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] == ch)
                {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    @Override
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

    @Override
    public String decrypt(String encryptedMessage) {
        String[] msgPairs = splitToNChar(encryptedMessage, 2);
        String originalMessage = "";

        for (int i = 0; i < msgPairs.length; i++)
        {
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] - 1) % 5;
                ch2Pos[1] = (ch2Pos[1] - 1) % 5;
            }

            // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] - 1) % 5;
                ch2Pos[0] = (ch2Pos[0] - 1) % 5;
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
            originalMessage = originalMessage + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }

        return originalMessage;
    }
}
