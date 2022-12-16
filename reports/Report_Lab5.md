# Topic: Web Authentication & Authorisation. 

### Course: Cryptography & Security
### Author: Rafaela Cerlat

----

## Theory

Spring Security is a framework that focuses on providing both authentication and authorization (or access-controls) to Java applications.

#### What are the core concepts of spring security?
**Authentication**

Authentication is about validating your credentials like User Name/User ID and password to verify your identity. The system determines whether you are what you say you are using your credentials.

**Authorization**

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






