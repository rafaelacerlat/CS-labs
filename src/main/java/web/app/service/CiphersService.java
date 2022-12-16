package web.app.service;


import ciphers.Cipher;
import ciphers.Classical.CaesarCipher;
import ciphers.Classical.PlayfairCipher;
import org.springframework.stereotype.Service;

@Service
public class CiphersService {

    public String caesar(String message, int key, boolean encryption){
        Cipher classicalCipher = new CaesarCipher(key);
        String processedMessage;
        if(encryption){
             processedMessage =  "Encrypted: " +  classicalCipher.encrypt(message);
        }else {
            processedMessage = "Decrypted: " +  classicalCipher.decrypt(message);
        }
        return processedMessage;
    }

    public String playfair(String message, String key, boolean encryption){
        Cipher classicalCipher = new PlayfairCipher(key);
        String processedMessage;
        if(encryption){
            processedMessage = "Encrypted: " + classicalCipher.encrypt(message);
        }else {
            processedMessage = "Decrypted: " +  classicalCipher.decrypt(message);
        }
        return processedMessage;
    }
}
