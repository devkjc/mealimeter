package com.toy.mealimeter.user.controller;

import com.toy.mealimeter.config.security.SecurityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hello")
    public String user() {
        return SecurityService.getUser().toString();
    }

}
