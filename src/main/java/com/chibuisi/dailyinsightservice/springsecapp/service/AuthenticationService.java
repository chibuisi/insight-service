package com.chibuisi.dailyinsightservice.springsecapp.service;

import com.chibuisi.dailyinsightservice.exception.AuthenticationFailedException;
import com.chibuisi.dailyinsightservice.springsecapp.model.AuthenticationRequest;
import com.chibuisi.dailyinsightservice.springsecapp.model.CustomUserDetails;
import com.chibuisi.dailyinsightservice.springsecapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Service
public class AuthenticationService {
    private Logger log = Logger.getLogger("HomeResource");
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(AuthenticationRequest authenticationRequest) throws Exception {
//        UserDetails userDetails =
//                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsernameOrEmail(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.info("Authentication processing failed: "+e.getMessage());
            throw new AuthenticationFailedException("Invalid Username or Password",e);
        }
        //final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsernameOrEmail());
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;
    }

    public String verify(String jwt){
        boolean res = jwtUtil.verify(jwt);
        String username = null;
        try{
            username = jwtUtil.extractUsername(jwt);
        }
        catch (Exception e){
            log.info("JWT verification is failed: "+ e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(res)
            return "OK";
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
