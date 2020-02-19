package ru.godsonpeya.springsecurityjwttest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.godsonpeya.springsecurityjwttest.entities.User;
import ru.godsonpeya.springsecurityjwttest.payload.AuthenticationRequest;
import ru.godsonpeya.springsecurityjwttest.payload.AuthenticationResponse;
import ru.godsonpeya.springsecurityjwttest.repositories.UserReopository;
import ru.godsonpeya.springsecurityjwttest.security.JwtUtil;
import ru.godsonpeya.springsecurityjwttest.services.MyUserDetailsService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeResource {

    @Autowired
    UserReopository userReopository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello(){
        return ("<h1>Hello world</h1>");
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password ");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @GetMapping("/me")
    public User me(HttpServletRequest req) throws UsernameNotFoundException {
        final String authorizationHeader = req.getHeader("Authorization");
        String token = "";
        String username = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        User user = userReopository
                .findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return user;

    }
}
