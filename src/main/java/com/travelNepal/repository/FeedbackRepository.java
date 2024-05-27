package com.travelNepal.repository;

import com.travelNepal.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findByPack_PackageId(Integer packageId);
}