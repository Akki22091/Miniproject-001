package com.npst.miniproject.service.impl;

import com.npst.miniproject.dao.PlanCategoryRepository;
import com.npst.miniproject.dao.PlanRepository;
import com.npst.miniproject.entity.PlanCategory;
import com.npst.miniproject.entity.PlanEntity;
import com.npst.miniproject.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanCategoryRepository planCategoryRepository;
    @Autowired
    private PlanRepository planRepository;


    @Override
    public Map<Integer, String> getPlanCategories() {

        List<PlanCategory> categories = planCategoryRepository.findAll();
        Map<Integer, String> categoryMap = new HashMap<>();
        categories.forEach(
                category -> {
                    categoryMap.put(category.getCategoryId(), category.getCategoryName());
                }
        );
        return categoryMap;
    }

    @Override
    public boolean savePlan(PlanEntity planEntity) {
        PlanEntity save = planRepository.save(planEntity);
        return save.getPlanId() != null;
    }

    @Override
    public List<PlanEntity> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public PlanEntity getPlanById(Integer planId) {
        Optional<PlanEntity> findById = planRepository.findById(planId);
        return findById.orElse(null);
    }

    @Override
    public boolean updatePlan(PlanEntity plan) {
        planRepository.save(plan);
        return plan.getPlanId() != null;
    }

    @Override
    public boolean softDelete(Integer planId, String activeSwitch) {

        Optional<PlanEntity> findById = planRepository.findById(planId);

        if (findById.isPresent()) {
            PlanEntity plan = findById.get();
            plan.setActiveSwitch(activeSwitch);
            planRepository.save(plan);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePlanById(Integer planId) {

        boolean status = false;
        try {
            planRepository.deleteById(planId);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
