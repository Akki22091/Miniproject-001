package com.npst.miniproject.controller;

import com.npst.miniproject.configuration.ApplicationProperties;
import com.npst.miniproject.constants.AppConstants;
import com.npst.miniproject.dto.PlanResponse;
import com.npst.miniproject.entity.PlanEntity;
import com.npst.miniproject.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/plans")
public class PlanRestController {

    @Autowired
    private PlanService planService;

    Map<String, String> messages;

    @Autowired
    PlanRestController(ApplicationProperties properties) {
        this.messages = properties.getMessages();
    }

    @PostMapping("/save")
    public ResponseEntity<PlanResponse> savePlan(@RequestBody PlanEntity plan) {
        log.info("Ready to save the plan -> {} ", plan);
        boolean isSaved = planService.savePlan(plan);
        if (isSaved) {
            log.info("Plan Saved Successfully -> {} ", plan);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new PlanResponse(true, AppConstants.PLAN_SAVE_SUCCESS, plan)
            );
        } else {
            log.info("Failed to save the plan due to some error  ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PlanResponse(false, AppConstants.PLAN_FAILED, null)
            );
        }
    }

    @GetMapping("/getAllPlans")
    public ResponseEntity<List<PlanEntity>> getAllPlans() {
        log.info("Fetching All plans ");
        List<PlanEntity> plans = planService.getAllPlans();
        if (plans.isEmpty()) {
            log.warn("No plans found.");
        } else {
            log.info("Fetched {} plans", plans.size());
        }
        return ResponseEntity.status(HttpStatus.OK).body(plans);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Integer planId) {
        if (planId == 0 || planId <= 0) {
            log.warn("Invalid Plan Id received -> {} ", planId);
            return ResponseEntity.badRequest().body(
                    new PlanResponse(false, AppConstants.INVALID_ID, null)
            );
        }
        try {
            PlanEntity plan = planService.getPlanById(planId);
            if (plan == null) {
                log.info("No plan found for ID: {}", planId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new PlanResponse(false, AppConstants.PLAN_NOT_FOUND, null)
                );
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new PlanResponse(true, AppConstants.PLAN_SUCCESS, plan)
                );
            }
        } catch (Exception e) {
            log.error("Error fetching plan with ID {}: {}", planId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PlanResponse(false, AppConstants.PLAN_NOT_FOUND, null)
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<PlanResponse> updatePlan(@RequestBody PlanEntity plan) {

        if (plan.getPlanId() == null || plan.getPlanId() <= 0) {
            log.warn("Invalid Plan input: {}", plan);
            return ResponseEntity.badRequest().body(
                    new PlanResponse(false, AppConstants.INVALID_ID, null)
            );
        }
        try {
            boolean isUpdated = planService.updatePlan(plan);
            if (isUpdated) {
                log.info("Plan updated successfully: {}", plan);
                return ResponseEntity.ok(
                        new PlanResponse(true, AppConstants.PLAN_UPDATE_SUCCESS, plan)
                );
            } else {
                log.warn("Plan update failed - Plan not found: {}", plan.getPlanId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new PlanResponse(false, AppConstants.PLAN_NOT_FOUND, null)
                );
            }
        } catch (Exception e) {
            log.error("Exception while updating plan: {}", plan, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PlanResponse(false, AppConstants.PLAN_UPDATE_FAILED, null)
            );
        }
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<PlanResponse> deletePlanById(@PathVariable Integer planId) {
        if (planId == null || planId <= 0) {
            log.warn("Invalid planId for deletion: {}", planId);
            return ResponseEntity.badRequest().body(
                    new PlanResponse(false, AppConstants.INVALID_ID, null)
            );
        }
        try {
            boolean deleted = planService.deletePlanById(planId);
            if (deleted) {
                log.info("Plan deleted successfully. planId: {}", planId);
                return ResponseEntity.ok(
                        new PlanResponse(true, AppConstants.PLAN_DELETE_SUCCESS, null)
                );
            } else {
                log.warn("Plan not found or already deleted. planId: {}", planId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new PlanResponse(false, AppConstants.PLAN_DELETE_FAILED, null)
                );
            }
        } catch (Exception e) {
            log.error("Exception while deleting planId {}: {}", planId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PlanResponse(false, AppConstants.INTERNAL_SERVER_ERROR, null)
            );
        }
    }

    @PutMapping("/plan/status")
    public ResponseEntity<PlanResponse> changeStatus(
            @RequestParam Integer planId,
            @RequestParam String activeSwitch) {

        if (planId == null || planId <= 0 || activeSwitch == null || activeSwitch.isBlank()) {
            log.warn("Invalid input for status change: planId={}, activeSwitch={}", planId, activeSwitch);
            return ResponseEntity.badRequest().body(
                    new PlanResponse(false, AppConstants.INVALID_ID, null)
            );
        }
        try {
            boolean changed = planService.softDelete(planId, activeSwitch);
            if (changed) {
                log.info("Plan status changed successfully: planId={}, status={}", planId, activeSwitch);
                return ResponseEntity.ok(
                        new PlanResponse(true, AppConstants.PLAN_STATUS_CHANGE, null)
                );
            } else {
                log.warn("Plan status unchanged or plan not found: planId={}", planId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new PlanResponse(false, AppConstants.PLAN_STATUS_NO_CHANGE, null)
                );
            }
        } catch (Exception e) {
            log.error("Exception during status change for planId={}: {}", planId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PlanResponse(false, AppConstants.INTERNAL_SERVER_ERROR, null)
            );
        }
    }
}
