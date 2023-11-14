package com.clicks.crimereportapp.repository;

import com.clicks.crimereportapp.model.CrimeScene;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrimeSceneRepository extends JpaRepository<CrimeScene, Long> {

    boolean existsByAreaAndLodgeNameAndRoomNo(String area, String lodgeName, String roomNo);

    Optional<CrimeScene> findByCode(Integer code);

    @Query(value = "" +
            "SELECT crimeScene " +
            "FROM CrimeScene crimeScene " +
            "WHERE crimeScene.area " +
            "LIKE %:area% OR crimeScene.lodgeName LIKE %:lodgeName% OR crimeScene.roomNo LIKE %:roomNo")
    Page<CrimeScene> findByAreaContainingOrLodgeNameContainingOrRoomNoContaining(String area, String lodgeName, String roomNo, Pageable pageable);
}
