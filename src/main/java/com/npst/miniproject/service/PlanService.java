package com.npst.miniproject.service;

import com.npst.miniproject.entity.PlanEntity;

import java.util.List;

public interface PlanService {


    public boolean savePlan(PlanEntity planEntity);

    public List<PlanEntity> getAllPlans();

    public PlanEntity getPlanById(Integer planId);

    public boolean updatePlan(PlanEntity plan);

    public boolean softDelete(Integer planId, String activeSwitch);

    public boolean deletePlanById(Integer planId);

}
