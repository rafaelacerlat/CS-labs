package web.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.app.model.ERole;
import web.app.model.Role;
import web.app.model.User;
import web.app.payload.request.LoginRequest;
import web.app.payload.request.SignupRequest;
import web.app.payload.response.JwtResponse;
import web.app.repository.RoleRepository;
import web.app.security.JwtUtil;
import web.app.security.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtToken;

    public void register(SignupRequest signupRequest){
        if(userService.existsByEmail(signupRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account with provided email already exists!");
        }
        if(userService.existsByUsername(signupRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account with provided username already exists!");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.SIMPLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role r;
                if(role.equalsIgnoreCase("admin")) {
                    r = roleRepository.findByRole(ERole.ADMIN);
                } else {
                    r = roleRepository.findByRole(ERole.SIMPLE_USER);
                }
                roles.add(r);
            });
        }

        user.setRoles(roles);

        userService.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtToken.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
