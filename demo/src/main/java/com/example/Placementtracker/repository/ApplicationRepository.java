package com.example.Placementtracker.repository;

import com.example.Placementtracker.entity.Application;
import com.example.Placementtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {
    long countBy();

    long countByUser(User user);

    long countByUserAndStatus(User user, Application.Status status);

    List<Application> findByUser(User user);

    List<Application> findByUserAndInterviewDateBetween(User user, LocalDate start,
                                                        LocalDate end);

    long countByUser_Id(long userid);

    List<Application> findByCompanyNameContainingIgnoreCase(String companyName);

    List<Application> findByStatus(String status);
    List<Application> findByUserAndCompanyNameContainingIgnoreCase(
            User user, String companyName);

    List<Application> findByUserAndStatus(
            User user, Application.Status status);

    List<Application>
    findByInterviewDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    List<Application> findByUserAndInterviewDateAfterOrderByInterviewDateAsc(
            User user, LocalDate date
    );

    long countByUserAndInterviewDateAfter(
            User user,
            LocalDate date
    );
    long countByUserAndInterviewDateGreaterThanEqual(User user, LocalDate date);
}