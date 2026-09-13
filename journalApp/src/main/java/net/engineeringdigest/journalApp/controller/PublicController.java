package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Utils.JwtUtil;
import net.engineeringdigest.journalApp.api.request.LoginRequest;
import net.engineeringdigest.journalApp.api.request.SignupRequest;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.engineeringdigest.journalApp.api.response.UserResponse;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(
        name = "Public APIs",
        description = "Public endpoints for health check, registration and authentication"
)
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @Operation(
            summary = "Health check",
            description = "Checks whether the Journal application is running"
    )
    @GetMapping("/HealthCheck")
    public String healthCheck() {
        return "OK";
    }


    @Operation(
            summary = "Register new user",
            description = "Creates a new user account. The USER role is assigned automatically."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            )
    })
    @PostMapping("/SignUp")
    public ResponseEntity<String> createUser(
            @RequestBody SignupRequest request) {

        userService.saveNewUser(request);

        return new ResponseEntity<>(
                "User registered successfully",
                HttpStatus.CREATED
        );
    }


    @Operation(
            summary = "Login",
            description = "Authenticates the user and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect username or password"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest user) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            user.getUserName()
                    );

            String jwt =
                    jwtUtil.generateToken(
                            userDetails.getUsername()
                    );

            return new ResponseEntity<>(
                    jwt,
                    HttpStatus.OK
            );

        } catch (Exception e) {

            log.error(
                    "Exception occurred while Authentication of the user",
                    e
            );

            return new ResponseEntity<>(
                    "Incorrect username or password",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}