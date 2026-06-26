package com.example.Placementtracker.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank(message="company Name is required!")
    private String companyName;

    private String role;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate interviewDate;
   public enum Status{
        APPLIED,SHORTLISTED,REJECTED,SELECTED
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}