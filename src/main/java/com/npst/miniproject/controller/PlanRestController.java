package com.npst.miniproject.controller;

import com.npst.miniproject.entity.PlanEntity;
import com.npst.miniproject.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlanRestController {

    private final PlanService planService;

    public PlanRestController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<Integer, String>> planCategories() {
        Map<Integer, String> planCategories = planService.getPlanCategories();
        return new ResponseEntity<>(planCategories, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePlan(@RequestBody PlanEntity plan) {
        boolean save = planService.savePlan(plan);
        String result = save ? "Plan Saved" : "Plan not saved";
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<PlanEntity>> getAllPlans() {
        List<PlanEntity> plans = planService.getAllPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<PlanEntity> getPlanById(@PathVariable Integer planId) {
        PlanEntity plan = planService.getPlanById(planId);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<String> deletePlanById(@PathVariable Integer planId) {
        boolean isDeleted = planService.deletePlanById(planId);
        String result = isDeleted ? "Plan Is Deleted" : "Plan Is Not Deleted";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/updatePlan")
    public ResponseEntity<String> updatePlan(@RequestBody PlanEntity plan) {
        boolean isupdated = planService.updatePlan(plan);
        String result = isupdated ? "Plan Is Updated" : "Plan Is Not Updated";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/StatusChange")
    public ResponseEntity<String> softDelete(Integer planId, String activeSwitch) {
        boolean isChanged = planService.softDelete(planId, activeSwitch);
        String result = isChanged ? "Plan Status Is Changed" : "Plan Status Is Not Changed";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
