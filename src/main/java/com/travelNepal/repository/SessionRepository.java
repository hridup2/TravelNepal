package com.travelNepal.repository;

import com.travelNepal.entity.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<CurrentUserSession, Integer> {

    CurrentUserSession findBySessionId(String sessionId);


}