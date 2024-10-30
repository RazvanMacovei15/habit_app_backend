package maco.habit_backend.controllers;

import maco.habit_backend.dtos.LoginUserDTO;
import maco.habit_backend.dtos.RegisterUserDTO;
import maco.habit_backend.entities.User;
import maco.habit_backend.security.LoginResponse;
import maco.habit_backend.security.services.AuthenticationService;
import maco.habit_backend.security.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    // Service for handling JWT token generation and validation
    private final JwtService jwtService;

    // Service for handling user registration and authentication
    private final AuthenticationService authenticationService;

    // Constructor for dependency injection of JwtService and AuthenticationService
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Handles user registration by accepting a registration DTO and returning the registered User.
     *
     * @param registerUserDto Data Transfer Object with registration details (email, username, password)
     * @return ResponseEntity containing the registered User entity and HTTP status 200 (OK)
     */
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        // Calls the AuthenticationService to register a new user
        User registeredUser = authenticationService.signup(registerUserDto);

        // Returns the registered User in the response with HTTP status 200 (OK)
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Authenticates a user by accepting a login DTO, validating credentials, and returning a JWT token if successful.
     *
     * @param loginUserDto Data Transfer Object containing login credentials (email and password)
     * @return ResponseEntity containing a LoginResponse with a JWT token and expiration time, and HTTP status 200 (OK)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        // Calls AuthenticationService to authenticate the user based on provided credentials
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Generates a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Builds a LoginResponse object containing the JWT token and expiration time
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)               // Sets the generated JWT token
                .expiresIn(jwtService.getExpirationTime()).build(); // Sets the token expiration time from JwtService

        // Returns the LoginResponse with HTTP status 200 (OK)
        return ResponseEntity.ok(loginResponse);
    }

}
