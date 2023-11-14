package com.clicks.crimereportapp.repository;

import com.clicks.crimereportapp.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    @Query(value = "SELECT session FROM UserSession session WHERE session.phone = ?1 AND session.pageType = ?2")
    Optional<UserSession> findByPhoneAndPageType(String phone, String pageType);
}
