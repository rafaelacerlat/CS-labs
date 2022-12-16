package web.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.app.model.User;
import web.app.payload.request.SignupRequest;
import web.app.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(String email){
       return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public void deleteByUsername(String username){
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
    }
}
