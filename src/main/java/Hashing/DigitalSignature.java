package Hashing;

import Ciphers.Asymmetric.RSA;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class DigitalSignature {
    private Repository passwordRepository = Repository.getInstance();
    private RSA rsaEncryption = new RSA(1024);

    public String hash(String message){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(message.getBytes());
            String hashedMessage = DatatypeConverter.printHexBinary(digest);
            return hashedMessage;

        } catch (Exception e) {
            throw new RuntimeException("Could not prepare data for hashing!");
        }
    }

    public String sign(String message){
        try{
            return rsaEncryption.encrypt(hash(message));
        } catch (Exception e) {
            throw new RuntimeException("Could not generate the digital signature!");
        }
    }

    public boolean check(String password, String digitalSignature){
        String decryptedPassword = rsaEncryption.decrypt(digitalSignature);
        return password.equals(decryptedPassword);
    }

}
