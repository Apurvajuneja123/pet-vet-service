package com.petcare.controller;

import com.petcare.dto.LoginRequest;
import com.petcare.dto.SignUpRequest;
import com.petcare.dto.JwtAuthenticationResponse;
import com.petcare.model.User;
import com.petcare.service.AuthService;
import com.petcare.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // User Registration
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            // Check if username is already taken
            if (authService.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body("Error: Username is already taken!");
            }

            // Check if email is already in use
            if (authService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("Error: Email is already in use!");
            }

            // Create new user account
            User user = authService.createUser(signUpRequest);

            return ResponseEntity.ok()
                    .body("User registered successfully! User ID: " + user.getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not register user - " + e.getMessage());
        }
    }

    // User Login
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Get user details first
            User user = authService.getUserByUsernameOrEmail(loginRequest.getUsernameOrEmail());
            
            // Generate token
            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(
                    jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
            ));

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid username/email or password!");
        }
    }

    // Refresh Token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                
                if (tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsernameFromJWT(jwt);
                    User user = authService.getUserByUsername(username);
                    
                    String newToken = tokenProvider.generateTokenFromUser(user);
                    
                    return ResponseEntity.ok(new JwtAuthenticationResponse(
                            newToken,
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getRole()
                    ));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Error: Invalid or expired token!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Token not provided!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not refresh token - " + e.getMessage());
        }
    }

    // Get Current User
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Error: User not authenticated!");
            }

            User user = authService.getUserByUsername(authentication.getName());
            
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not fetch user details - " + e.getMessage());
        }
    }

    // Logout
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // In a stateless JWT implementation, logout is typically handled client-side
        // by removing the token from storage
        return ResponseEntity.ok()
                .body("User logged out successfully!");
    }

    // Change Password
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                          Authentication authentication) {
        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Error: User not authenticated!");
            }

            String username = authentication.getName();
            authService.changePassword(username, changePasswordRequest.getOldPassword(), 
                                     changePasswordRequest.getNewPassword());

            return ResponseEntity.ok()
                    .body("Password changed successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Could not change password - " + e.getMessage());
        }
    }

    // Forgot Password (initiate password reset)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authService.initiatePasswordReset(forgotPasswordRequest.getEmail());
            return ResponseEntity.ok()
                    .body("Password reset email sent successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Could not initiate password reset - " + e.getMessage());
        }
    }

    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            authService.resetPassword(resetPasswordRequest.getToken(), 
                                    resetPasswordRequest.getNewPassword());
            return ResponseEntity.ok()
                    .body("Password reset successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Could not reset password - " + e.getMessage());
        }
    }

    // Inner classes for request bodies
    public static class ChangePasswordRequest {
        private String oldPassword;
        private String newPassword;

        // Getters and setters
        public String getOldPassword() { return oldPassword; }
        public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    public static class ForgotPasswordRequest {
        private String email;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}