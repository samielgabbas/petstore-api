package com.rbc.petstore.rest;

import com.rbc.petstore.dto.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
public class AuthController {

    @GetMapping("/api/login")
    public Principal login(Principal user) {
        return user;
    }

}
