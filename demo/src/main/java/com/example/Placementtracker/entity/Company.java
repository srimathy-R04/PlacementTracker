package com.example.Placementtracker.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data

    @Entity
    public class Company {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String companyName;
        private String role;
        private String status;
        private double packageLpa;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;


}
