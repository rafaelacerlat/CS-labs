package ciphers.Hashing;

import hashing.DigitalSignature;
import hashing.Repository;
import hashing.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DigitalSignatureTest{
    private Repository passwordRepository = Repository.getInstance();

    @Test
    public void testDS(){
        DigitalSignature digitalSignature = new DigitalSignature();

        // Generate a new user
        User user = new User(1L,"rafaelacerlat", "secure123");

        // Store the user's id and hashed password to the database
        passwordRepository.save(user.getId(), digitalSignature.hash(user.getPassword()));

        // Store the user's hashed password in a variable to use for validation later
        String userHashedPassword = passwordRepository.getById(user.getId());

        // Generate a digital signature from the user's password
        String userDigitalSignature = digitalSignature.sign(user.getPassword());

        // Check/validate the digital signature
        boolean valid = digitalSignature.check(userHashedPassword, userDigitalSignature);

        Assertions.assertTrue(valid);
    }
}
