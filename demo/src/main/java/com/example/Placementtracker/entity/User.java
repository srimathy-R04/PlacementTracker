package com.example.Placementtracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

   private int id;
    private String username;
    private String password;
    private String role;
 private String packageLpa;

}


