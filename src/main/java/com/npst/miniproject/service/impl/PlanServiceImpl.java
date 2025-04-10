package com.npst.miniproject.service.impl;

import com.npst.miniproject.constants.ExceptionsConstants;
import com.npst.miniproject.dao.PlanCategoryRepository;
import com.npst.miniproject.dao.PlanRepository;
import com.npst.miniproject.entity.PlanEntity;
import com.npst.miniproject.exception.DatabaseOperationException;
import com.npst.miniproject.exception.InvalidRequestException;
import com.npst.miniproject.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanCategoryRepository planCategoryRepository;
    @Autowired
    private PlanRepository planRepository;


    @Override
    public boolean savePlan(PlanEntity planEntity) {
        if (planEntity == null) {
            log.warn("Attempted to save a null PlanEntity");
            throw new InvalidRequestException(ExceptionsConstants.PLAN_ENTITY_MUST_NOT_BE_NULL);
        }
        try {
            PlanEntity savedPlan = planRepository.save(planEntity);

            if (savedPlan.getPlanId() != null) {
                log.info("Plan saved successfully with ID: {}", savedPlan.getPlanId());
                return true;
            } else {
                log.error("Failed to save PlanEntity: {}", planEntity);
                return false;
            }
        } catch (Exception e) {
            log.error("Exception occurred while saving plan: {}", e.getMessage(), e);
            throw new DatabaseOperationException(ExceptionsConstants.DATABASE_EXCEPTION);
        }
    }

    @Override
    public List<PlanEntity> getAllPlans() {
        log.info("Fetching all plans from the database");
        List<PlanEntity> plans = planRepository.findAll();
        if (plans.isEmpty()) {
            log.warn("No plans found in the database");
            return Collections.emptyList();
        }
        log.info("Fetched {} plans", plans.size());
        return plans;
    }


    @Override
    public PlanEntity getPlanById(Integer planId) {
        if (planId == null || planId <= 0) {
            log.warn("Invalid plan ID provided: {}", planId);
            throw new InvalidRequestException(ExceptionsConstants.PLAN_ID_POSITIVE_VALIDATION_MESSAGE);
        }
        return planRepository.findById(planId)
                .map(plan -> {
                    log.info("Plan found with ID: {}", planId);
                    return plan;
                })
                .orElseGet(() -> {
                    log.warn("No plan found with ID: {}", planId);
                    return null;
                });
    }

    @Override
    public boolean updatePlan(PlanEntity updatedPlan) {
        if (updatedPlan == null || updatedPlan.getPlanId() == null) {
            log.warn("PlanEntity or Plan ID is null. Update cannot proceed.");
            throw new InvalidRequestException(ExceptionsConstants.PLAN_ENTITY_MUST_NOT_BE_NULL);
        }
        Integer planId = updatedPlan.getPlanId();
        Optional<PlanEntity> plan = planRepository.findById(planId);

        if (plan.isEmpty()) {
            log.warn("No existing plan found with ID: {}. Update aborted.", planId);
            return false;
        }

        PlanEntity existingPlan = plan.get();
        existingPlan.setPlanName(updatedPlan.getPlanName());
        existingPlan.setPlanStartDate(updatedPlan.getPlanStartDate());
        existingPlan.setPlanEndDate(updatedPlan.getPlanEndDate());
        existingPlan.setActiveSwitch(updatedPlan.getActiveSwitch());

        try {
            planRepository.save(existingPlan);
            log.info("Plan with ID {} updated successfully.", planId);
            return true;
        } catch (Exception e) {
            log.error("Error updating plan with ID {}: {}", planId, e.getMessage(), e);
            throw new DatabaseOperationException(ExceptionsConstants.DATABASE_EXCEPTION);
        }
    }

    @Override
    public boolean softDelete(Integer planId, String activeSwitch) {
        if (planId == null || planId <= 0) {
            log.warn("Invalid plan ID provided for soft delete: {}", planId);
            throw new InvalidRequestException(ExceptionsConstants.PLAN_ID_POSITIVE_VALIDATION_MESSAGE);
        }
        if (activeSwitch == null || (!activeSwitch.equalsIgnoreCase("Y") && !activeSwitch.equalsIgnoreCase("N"))) {
            log.warn("Invalid activeSwitch value provided: {}", activeSwitch);
            throw new InvalidRequestException(ExceptionsConstants.ACTIVE_SWITCH);
        }
        Optional<PlanEntity> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isEmpty()) {
            log.warn("No plan found with ID {} for soft delete", planId);
            return false;
        }
        PlanEntity plan = optionalPlan.get();
        plan.setActiveSwitch(activeSwitch.toUpperCase());
        try {
            planRepository.save(plan);
            log.info("Soft deleted plan with ID {}. Set activeSwitch to '{}'", planId, activeSwitch);
            return true;
        } catch (Exception e) {
            log.error("Error during soft delete of plan with ID {}: {}", planId, e.getMessage(), e);
            throw new DatabaseOperationException(ExceptionsConstants.DATABASE_EXCEPTION);
        }
    }


    @Override
    public boolean deletePlanById(Integer planId) {
        if (planId == null || planId <= 0) {
            log.warn("Invalid plan ID provided for deletion: {}", planId);
            throw new InvalidRequestException(ExceptionsConstants.PLAN_ID_POSITIVE_VALIDATION_MESSAGE);
        }
        if (!planRepository.existsById(planId)) {
            log.warn("No plan found with ID {} to delete", planId);
            return false;
        }
        try {
            planRepository.deleteById(planId);
            log.info("Plan with ID {} deleted successfully", planId);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while deleting plan with ID {}: {}", planId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete the plan. Please try again later.");
        }
    }

}
