package com.meritamerica.assignment7.controllers;

import com.meritamerica.assignment7.models.AuthenticationRequest;
import com.meritamerica.assignment7.models.AuthenticationResponse;
import com.meritamerica.assignment7.models.User;
import com.meritamerica.assignment7.repositories.UserRepository;
import com.meritamerica.assignment7.security.MeritUserDetailsService;
import com.meritamerica.assignment7.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserSecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MeritUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/Authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsEx) {
            return new ResponseEntity<String>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException usernameNotFoundEx) {
            return new ResponseEntity<String>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/Authenticate/CreateUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        return userRepository.save(newUser);
    }

}