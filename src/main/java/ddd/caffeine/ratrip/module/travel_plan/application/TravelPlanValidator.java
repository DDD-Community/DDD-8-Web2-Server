package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;

@Component
public class TravelPlanValidator {
	public TravelPlan validateExistTravelPlan(Optional<TravelPlan> travelPlan) {
		return travelPlan.orElseThrow(() -> new TravelPlanException(NOT_FOUND_TRAVEL_PLAN_EXCEPTION));
	}
}
