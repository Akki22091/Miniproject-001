package com.npst.miniproject.constants;

public interface ExceptionsConstants {

    String PLAN_ID_POSITIVE_VALIDATION_MESSAGE = "Plan ID must be a positive integer";
    String NO_PLAN_FOUND_WITH_ID = "No plan found with ID";
    String PLAN_ENTITY_MUST_NOT_BE_NULL = "PlanEntity must not be null";
    String PLAN_ENTITY_NOT_SAVED = "PlanEntity was not saved correctly";
    String FAILED_TO_SAVE_PLAN = "Failed to save plan. Please try again later";
    String DATABASE_EXCEPTION = "Failed to save the data to database due to some technical issue";
    String ACTIVE_SWITCH = "Active switch must be 'Y' or 'N'";


}
