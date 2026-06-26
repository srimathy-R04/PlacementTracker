package com.example.Placementtracker;

import com.example.Placementtracker.entity.Application;
import com.example.Placementtracker.entity.User;
import com.example.Placementtracker.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationRepository appRepo;

    @GetMapping("/applications")
    public String listApplications(Model model,HttpSession session) {
        User user=(User)
        session.getAttribute("loggedInUser");
        model.addAttribute(
                "applications",
                appRepo.findByUser(user));

        return "application-list";
    }

    @GetMapping("/applications/add")
    public String showForm(Model model) {

        model.addAttribute(
                "application",
                new Application());

        return "application-form";
    }
    @GetMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        appRepo.deleteById(id);
        return "redirect:/applications";
    }
    @GetMapping("/applications/edit/{id}")
    public String edit(@PathVariable Long id,Model model) {
        Application application = appRepo.findById(id).orElse(null);
        model.addAttribute("application",application);
        return "application-form";
    }
    @PostMapping("/applications/save")
    public String saveApplication(
            @Valid @ModelAttribute("application") Application application,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "application-form";
        }
        User user=(User)session.getAttribute("loggedInUser");
        application.setUser(user);
        if (application.getStatus()==null)
        {
            application.setStatus(Application.Status.APPLIED);
        }
        appRepo.save(application);
        return "redirect:/applications";
    }
    @GetMapping("/applications/search")
    public String searchApplications(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "applications",
                appRepo.findByCompanyNameContainingIgnoreCase(keyword));

        return "application-list";
    }
    @GetMapping("/applications/filter")
    public String filterByStatus(
            @RequestParam String status,
            Model model) {

        model.addAttribute(
                "applications",
                appRepo.findByStatus(status));

        return "application-list";
    }
    @GetMapping("/applications/date-filter")
    public String filterByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Model model) {

        model.addAttribute(
                "applications",
                appRepo.findByInterviewDateBetween(
                        startDate,
                        endDate));

        return "application-list";
    }
}