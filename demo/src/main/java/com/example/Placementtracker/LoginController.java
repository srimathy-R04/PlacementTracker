package com.example.Placementtracker;

import com.example.Placementtracker.entity.Company;
import com.example.Placementtracker.entity.User;

import com.example.Placementtracker.repository.CompanyRepository;
import com.example.Placementtracker.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.HttpSession;
@Controller
public class LoginController {

    @Autowired
    UserRepository repo;

    @Autowired
    CompanyRepository CompanyRepo;

    @Autowired
    private PasswordEncoder
    passwordEncoder;

    @PostMapping("/Login")
    public String Login(
            @RequestParam String username,
            @RequestParam String password, Model model,
            HttpSession session) {

        User u = new User();

        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        User user = repo.findByUsername(username);
        if (user != null && passwordEncoder.matches(password,user.getPassword())) {
            session.setAttribute("loggedInUser",user);
            return "dashboard";
        }else if (user == null) {
            model.addAttribute(
                    "error", "doesn't exist!Enter Valid Username or Password. "
            );
        }
        return "login";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String password, Model model) {

        User existingUser =
                repo.findByUsername(username);

        if (existingUser != null) {
            model.addAttribute(
                    "error", "username already exist!"
            );
            return "signup";

        }
        if (password.length() < 6) {
            model.addAttribute("error", "password must be in 6 characters!");
            return "signup";
        }

        User u = new User();

        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        repo.save(u);

        return "login";
    }

    @PostMapping("/addCompany")
    public String addCompany(

            @RequestParam String companyName,
            @RequestParam String role,
            @RequestParam String status,
            @RequestParam double packageLpa,
    HttpSession session) {
        Company c = new Company();

        c.setCompanyName(companyName);
        c.setRole(role);
        c.setStatus(status);
        c.setPackageLpa(packageLpa);
        User user=(User)session.getAttribute("loggedInUser");
        if(user==null)
        {
            return "redirect:/login";
        }
        System.out.println("logged in user id="+user.getId());
        System.out.println("Session User:"+user);
        c.setUser(user);

        CompanyRepo.save(c);
        session.setAttribute("success", "Application added successfully!");

        return "dashboard";
    }



}


