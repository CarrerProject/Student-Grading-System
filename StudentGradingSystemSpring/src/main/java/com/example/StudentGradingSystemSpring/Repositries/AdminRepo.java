package com.example.StudentGradingSystemSpring.Repositries;


import com.example.StudentGradingSystemSpring.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
}
