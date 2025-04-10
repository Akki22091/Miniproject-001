package com.npst.miniproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "PLAN")
public class PlanEntity {

    @Id
    @GeneratedValue
    @Column(name = "PLAN_ID")
    private Integer planId;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_START_DATE")
    private LocalDate planStartDate;

    @Column(name = "PLAN_END_DATE")
    private LocalDate planEndDate;

    @Column(name = "PLAN_CATEGORY_ID")
    private Integer planCategoryId;

    @Column(name = "ACTIVE_SWITCH")
    private String activeSwitch;

}
