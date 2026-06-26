package com.example.Placementtracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/AddCompany")
    public String AddCompany() {
        return "AddCompany";
    }


}

