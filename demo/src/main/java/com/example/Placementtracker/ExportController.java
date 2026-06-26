package com.example.Placementtracker;

import com.example.Placementtracker.entity.Application;
import com.example.Placementtracker.entity.User;
import com.example.Placementtracker.repository.ApplicationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExportController {

    private final ApplicationRepository appRepo;

    public ExportController(ApplicationRepository appRepo) {
        this.appRepo = appRepo;
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportCSV(HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        System.out.println("session User:"+session.getAttribute("loggedInUser"));
        if (user == null) {
            return ResponseEntity.status(401).body("Please login");
        }

        List<Application> apps = appRepo.findByUser(user);

        StringBuilder csv = new StringBuilder();
        csv.append("Company,Role,Status,Interview Date\n");

        for (Application app : apps) {
            csv.append(app.getCompanyName()).append(",");
            csv.append(app.getRole()).append(",");
            csv.append(app.getStatus()).append(",");
            csv.append(app.getInterviewDate()).append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=applications.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv.toString());
    }
}