package com.example.Placementtracker.repository;

import com.example.Placementtracker.entity.Company;
import com.example.Placementtracker.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Integer>
{

    List<Company> findByUser(User user);

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user = null;
}
