package com.clicks.crimereportapp.repository;

import com.clicks.crimereportapp.model.Crime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrimeRepository extends JpaRepository<Crime, Long> {

    boolean existsByName(String name);

    Optional<Crime> findByCode(Integer code);

    @Query(value = "SELECT crime FROM Crime crime WHERE crime.name LIKE %:name% OR crime.description LIKE %:description%")
    Page<Crime> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}
