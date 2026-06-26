package com.example.Placementtracker;

import com.example.Placementtracker.entity.Application;
import com.example.Placementtracker.entity.User;
import com.example.Placementtracker.repository.ApplicationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class SearchController {
    @Autowired
    private ApplicationRepository appRepo;
    @GetMapping("/search-page")
    public String searchPage() {
        return "search";
    }
    @GetMapping("/search")
    public String searchApplications(
            @RequestParam String keyword,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        model.addAttribute(
                "applications",
                appRepo.findByUserAndCompanyNameContainingIgnoreCase(user, keyword));

        return "application-list";
    }


    @GetMapping("/search/date-filter")
    public String filterByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        model.addAttribute(
                "applications",
                appRepo.findByUserAndInterviewDateBetween(
                        user, startDate, endDate));

        return "application-list";
    }

    @GetMapping("/search/filter")
    public String filterByStatus(
            @RequestParam Application.Status status,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        model.addAttribute(
                "applications",
                appRepo.findByUserAndStatus(user, status));

        return "application-list";
    }
    }

