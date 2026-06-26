package com.example.Placementtracker.repository;

import com.example.Placementtracker.entity.Company;
import com.example.Placementtracker.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.PublicKey;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>
            {

                    User findByUsername(String username);

                    User findByUsernameAndPassword(
                            String username,
                            String password);
                    @ManyToOne
                    @JoinColumn(name = "user_id")
                    User user = null;

            }




