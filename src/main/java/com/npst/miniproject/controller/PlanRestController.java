package com.npst.miniproject.controller;

import com.npst.miniproject.entity.PlanEntity;
import com.npst.miniproject.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
