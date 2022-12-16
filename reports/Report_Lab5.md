# Topic: Web Authentication & Authorisation. 

### Course: Cryptography & Security
### Author: Rafaela Cerlat

----

## Theory

Spring Security is a framework that focuses on providing both authentication and authorization (or access-controls) to Java applications.

#### What are the core concepts of spring security?
1) **Authentication**

Authentication is about validating your credentials like User Name/User ID and password to verify your identity. The system determines whether you are what you say you are using your credentials.

2) **Authorization**

Authorization is the process to determine whether the authenticated user has access to particular resources. It verifies your rights to grant you access to resources such as information, databases, files, etc. Authorization usually comes after authentication which confirms your privileges to perform.


Authorisation is a crucial requirement for any web application. There are multiple mechanisms used for this purpose having their own pros and cons. Also there are several opensource and paid libraries and frameworks that implement them. JWT token based authentication and authorisation is a relatively new and popular implementation used by a lot of modern applications.
#### What is JWT?
https://jwt.io says, JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained method for securely transmitting information between parties as a JSON object. This information is verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

#### How it works?
1. Initially the client authenticates with the server using a method such as via a username and a password.
2. When the authentication is successful, server will generate a JWT token. This token is encrypted, having information such as Username, Role- authorisation information) about the logged-in user.
3. This token will be sent back to client as the response.
4. In the subsequent requests, client send the JWT token. A common place to send it is the Authorisation header using Bearer schema.
5. Server will decode the token and authorise the user using the sent data.

----

## Objectives:
1. Take what you have at the moment from previous laboratory works and put it in a web service / several web services.
2. Your services should have implemented basic authentication and MFA (the authentication factors of your choice).
3. Your web app needs to simulate user authorization and the way you authorise user is also a choice that needs to be done by you.
4. As services that your application could provide, you could use the classical ciphers. Basically the user would like to get access and use the classical ciphers, but they need to authenticate and be authorized.

----

## Implementation 
We have a Spring Boot application with users having different roles. Depending on those roles, users will be allowed to access different APIs.

The main used dependencies are:
1. Spring Web
2. Spring Data JPA
3. Spring Security
4. PostgreSQL Driver
5. JSON Web Token

### [Entities](https://github.com/rafaelacerlat/CS-labs/tree/master/src/main/java/web/app/model)
There is a User entity and a Role entity, and corresponding repositories for database access. Also an enum to represent available roles. A single user can have multiple roles.


### [JwtUtil ](https://github.com/rafaelacerlat/CS-labs/blob/master/src/main/java/web/app/security/JwtUtil.java)
This component is for JWT related utilities like generating the token, getting username and validating it. When generating the token, we get the username using user details. Also we get help from io.jsonwebtoken library.

### [AuthTokenFilter](https://github.com/rafaelacerlat/CS-labs/blob/master/src/main/java/web/app/security/AuthTokenFilter.java)
In this filter class extending OncePerRequestFilter abstract class, it has the doFilterInternal() method in order to filter all the incoming requests. We get the JWT token from the Authorisation header and validate it. Then we get the user details from UserDetailsService and populate authentication in security context.

### [User details](https://github.com/rafaelacerlat/CS-labs/blob/master/src/main/java/web/app/service/UserDetailsServiceImpl.java)
UserDetailsServiceImpl will retrieve the user from users table and build a UserDetailsImpl object which implements UserDetails class. It will contain user information like id, username, email, password and authorities.

In UserDetailsImpl we are populating details of the user.
```
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
    
    ...
}
```
### [Authentication](https://github.com/rafaelacerlat/CS-labs/blob/master/src/main/java/web/app/controller/AuthController.java)
During signup, user’s details should be collected and persisted in the users table. Also their roles should be mapped.

Here is an example of signing up a new user through Postman:
![image](https://user-images.githubusercontent.com/41265306/208195306-d2d0361b-1afc-46bf-9f65-6065be1b19f6.png)


Further to that, during each login process, a JWT token should be generated (with the help of JwtUtils) and passed to the client in order to send in subsequent requests.

![image](https://user-images.githubusercontent.com/41265306/208195479-aa913fc8-b323-4fd9-8da6-945805f7cddf.png)



### [Authorization](https://github.com/rafaelacerlat/CS-labs/blob/master/src/main/java/web/app/controller/CiphersController.java)
As mentioned before, AuthenticationTokenFilter will filter and permit only the requests containing valid JWT tokens.

In CiphersController we will allow calling a certain URL only if the user has the corresponding role privilege. The Spring Security provided *@PreAuthorize* annotation is used for this purpose. Public APIs will not contain this annotation and will allow any authenticated user in.

So users with the role of SIMPLE_USER can access and use the Caesar and Playfair ciphers (using the token generated at login):
![image](https://user-images.githubusercontent.com/41265306/208197019-1ee97742-7892-4066-ace3-9d4131df9164.png)



![image](https://user-images.githubusercontent.com/41265306/208196225-19694791-09b9-4347-a6f0-92a472b22543.png)
![image](https://user-images.githubusercontent.com/41265306/208196432-b3167e46-5247-49df-b132-a395bb0ae218.png)


In AdminController we give the posibility to the user with the role of ADMIN to delete any user using their username.
```
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTickets(@PathVariable String username){
        userService.deleteByUsername(username);
    }

}
```






