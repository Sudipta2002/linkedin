package com.linkedin.backend.features.authentication.controller;

import com.linkedin.backend.features.authentication.dto.AuthenticationRequestBody;
import com.linkedin.backend.features.authentication.dto.AuthenticationResponseBody;
import com.linkedin.backend.features.authentication.model.AuthenticationUser;
import com.linkedin.backend.features.authentication.services.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/user")
    public AuthenticationUser getUser(@RequestAttribute("authenticatedUser")AuthenticationUser authenticationUser ){
        return authenticationService.getUser(authenticationUser.getEmail());
    }

    @PostMapping("/register")
    public AuthenticationResponseBody registerUser(@Valid @RequestBody AuthenticationRequestBody registerRequestBody) throws NoSuchAlgorithmException, MessagingException, UnsupportedEncodingException {
        return authenticationService.register(registerRequestBody);
    }

    @PostMapping("/login")
    public AuthenticationResponseBody loginPage(@Valid @RequestBody AuthenticationRequestBody authenticationRequestBody) throws NoSuchAlgorithmException {
        return authenticationService.login(authenticationRequestBody);
    }
    @PutMapping("/validate-email-verification-token")
    public String verifyEmail(@RequestParam String token, @RequestAttribute("authenticatedUser") AuthenticationUser user) throws NoSuchAlgorithmException {
        authenticationService.validateEmailVerificationToken(token, user.getEmail());
        return "Email verified successfully.";
    }

    @GetMapping("/send-email-verification-token")
    public String sendEmailVerificationToken(@RequestAttribute("authenticatedUser") AuthenticationUser user) throws NoSuchAlgorithmException {
        authenticationService.sendEmailVerificationToken(user.getEmail());
        return "Email verification token sent successfully.";
    }

    @PutMapping("/send-password-reset-token")
    public String sendPasswordResetToken(@RequestParam String email) throws NoSuchAlgorithmException {
        authenticationService.sendPasswordResetToken(email);
        return "Password reset token sent successfully.";
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String token, @RequestParam String email) throws NoSuchAlgorithmException {
        authenticationService.resetPassword(email, newPassword, token);
        return "Password reset successfully.";
    }
}
