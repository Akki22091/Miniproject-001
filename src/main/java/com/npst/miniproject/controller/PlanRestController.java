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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PlanRestController {


    @Autowired
    private PlanService planService;

    Map<String, String> messages = new HashMap<>();

    @Autowired
    PlanRestController(ApplicationProperties properties) {
        this.messages = properties.getMessages();
    }


    @PostMapping("/save")
    public ResponseEntity<String> savePlan(@RequestBody PlanEntity plan) {
        boolean saved = planService.savePlan(plan);
        String message = saved ? messages.get(AppConstants.PLAN_SAVE_SUCCESS) : messages.get(AppConstants.PLAN_FAILED);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<PlanEntity>> getAllPlans() {
        List<PlanEntity> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Integer planId) {
        if (planId == null || planId <= 0) {
            return ResponseEntity.badRequest().body(new PlanResponse(false, AppConstants.INVALID_ID, null));
        }

        PlanEntity plan = planService.getPlanById(planId);
        return ResponseEntity.ok(new PlanResponse(true, AppConstants.PLAN_SUCCESS, plan));
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<String> deletePlanById(@PathVariable Integer planId) {
        boolean deleted = planService.deletePlanById(planId);
        String message = deleted ? messages.get(AppConstants.PLAN_DELETE_SUCCESS) : messages.get(AppConstants.PLAN_DELETE_FAILED);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/updatePlan")
    public ResponseEntity<String> updatePlan(@RequestBody PlanEntity plan) {
        boolean updated = planService.updatePlan(plan);
        String message = updated ? messages.get(AppConstants.PLAN_UPDATE_SUCCESS) : messages.get(AppConstants.PLAN_UPDATE_FAILED);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/StatusChange")
    public ResponseEntity<String> changeStatus(@RequestParam Integer planId, @RequestParam String activeSwitch) {
        boolean changed = planService.softDelete(planId, activeSwitch);
        String message = changed ? messages.get(AppConstants.PLAN_STATUS_CHANGE) : messages.get(AppConstants.PLAN_STATUS_NO_CHANGE);
        return ResponseEntity.ok(message);
    }
}
