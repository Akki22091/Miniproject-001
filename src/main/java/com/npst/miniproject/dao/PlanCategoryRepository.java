package com.npst.miniproject.dao;

import com.npst.miniproject.entity.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {
}
