package ddd.caffeine.ratrip.module.day_plan.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
public class DayPlanController {
	private final DayPlanService dayPlanService;
}
