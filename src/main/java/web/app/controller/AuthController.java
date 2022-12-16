package web.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.app.payload.request.LoginRequest;
import web.app.payload.request.SignupRequest;
import web.app.service.AuthService;


@RestController
@RequestMapping("/users")
public class AuthController {
    @Autowired
    AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> registerUser( @RequestBody SignupRequest signupRequest) {
        authenticationService.register(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}
