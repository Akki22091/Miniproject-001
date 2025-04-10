package com.npst.miniproject.dto;

import com.npst.miniproject.entity.PlanEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanResponse {

    private boolean success;
    private String message;
    private PlanEntity data;
}
