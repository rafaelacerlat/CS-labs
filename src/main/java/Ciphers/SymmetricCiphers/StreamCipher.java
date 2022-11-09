package Ciphers.SymmetricCiphers;

import Ciphers.ClassicalCiphers.ClassicalCipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StreamCipher implements ClassicalCipher {
    private  List<Integer> keyword = new ArrayList<>();
    private List<Integer> messageInBinary;

    private List<Integer> encrypted = new ArrayList<>();


    public StreamCipher(String message, String keyword){

        this.messageInBinary = toBinary(message,8);

        int number = keyword.hashCode();


        Random key = new Random();
        key.setSeed(number);

        for(int i = 0; i < messageInBinary.size(); i++){
            this.keyword.add(key.nextInt(2));
        }

    }

    public static List<Integer> toBinary(String str, int bits) {
        String result = "";
        String tmpStr;
        int tmpInt;
        char[] messChar = str.toCharArray();
        List<Integer> binary = new ArrayList<>();

        for (int i = 0; i < messChar.length; i++) {
            tmpStr = Integer.toBinaryString(messChar[i]);
            tmpInt = tmpStr.length();
            if(tmpInt != bits) {
                tmpInt = bits - tmpInt;
                if (tmpInt == bits) {
                    result += tmpStr;
                } else if (tmpInt > 0) {
                    for (int j = 0; j < tmpInt; j++) {
                        result += "0";
                    }
                    result += tmpStr;
                } else {
                    System.err.println("argument 'bits' is too small");
                }
            } else {
                result += tmpStr;
            }
        }

        for(char c: result.toCharArray()){
            binary.add((int) c - 48);
        }

        return binary;
    }

    @Override
    public String encrypt(String message) {

        for(int i = 0; i < messageInBinary.size(); i++){
            this.encrypted.add(messageInBinary.get(i)^keyword.get(i));
        }

        return encrypted.toString();
    }

    @Override
    public String decrypt(String encryptedMessage) {

        String enc ="";
        for(int i = 0; i < encrypted.size(); i++){
            enc += encrypted.get(i)^keyword.get(i);
        }

        StringBuilder sb = new StringBuilder(); // Some place to store the chars
        Arrays.stream( // Create a Stream
                enc.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
        ).forEach(s -> // Go through each 8-char-section...
                sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
        );

        return sb.toString();

    }
}
