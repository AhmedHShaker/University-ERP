package com.university.erp.controller;

import com.university.erp.model.AuthRequest;
import com.university.erp.model.JwtResponse;
import com.university.erp.model.User;
import com.university.erp.service.UserService;
import com.university.erp.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Generate the JWT token
            final String token = jwtService.generateToken(authRequest.getUsername());

            try {
                User user = userService.getUser(authRequest.getUsername());
                String role = user.getRole();
                long userId = user.getUserId();

                // Create an HTTP-only cookie
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60); // Set the expiration time (1 hour in seconds)
                cookie.setPath("/"); // Set the path for which the cookie is valid

                response.addCookie(cookie);

                return ResponseEntity.ok(new JwtResponse(token, userId, role));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login Failed");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login Failed");
        }
    }
}
