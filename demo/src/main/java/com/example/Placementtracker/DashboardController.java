package com.example.Placementtracker;

import com.example.Placementtracker.entity.Application;
import com.example.Placementtracker.entity.User;
import com.example.Placementtracker.repository.ApplicationRepository;
import com.example.Placementtracker.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {

    private final ApplicationRepository appRepo;
    private final UserRepository userRepo;
    public DashboardController(ApplicationRepository appRepo, UserRepository userRepo) {
        this.appRepo = appRepo;
        this.userRepo = userRepo;
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user==null)
            return "redirect:/login";
        List<Application> apps = appRepo.findByUser(user);
        System.out.println("application:"+ apps.size());
        long application =apps.size();
        model.addAttribute("totalApplications", application);

     /* long interviews = apps.stream()
                .filter(a -> "INTERVIEW".equalsIgnoreCase(String.valueOf(a.getStatus())))
                .count();
*/
        long selected = apps.stream()
                .filter(a -> "SELECTED".equalsIgnoreCase(String.valueOf(a.getStatus())))
                .count();

        long interviews = appRepo.countByUserAndInterviewDateGreaterThanEqual(user, LocalDate.now());
        model.addAttribute("interviews", interviews);
        model.addAttribute("selected", selected);
        System.out.println(model.asMap());
        return "dashboard";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("total", appRepo.countByUser(user));
        model.addAttribute("interviews",
                appRepo.countByUserAndInterviewDateAfter(user, LocalDate.now()));
        model.addAttribute("selected",
                appRepo.countByUserAndStatus(user, Application.Status.SELECTED));

        return "profile";
    }
}