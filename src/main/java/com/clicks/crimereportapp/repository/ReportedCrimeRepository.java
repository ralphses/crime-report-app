package com.clicks.crimereportapp.repository;

import com.clicks.crimereportapp.model.ReportedCrime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedCrimeRepository extends JpaRepository<ReportedCrime, Long> {
}
