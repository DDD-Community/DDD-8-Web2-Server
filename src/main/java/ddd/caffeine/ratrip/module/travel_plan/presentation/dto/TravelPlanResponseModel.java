package ddd.caffeine.ratrip.module.travel_plan.presentation.dto;

import java.time.LocalDate;
import java.util.UUID;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.Getter;

@Getter
public class TravelPlanResponseModel {

	private UUID id;

	private String title;

	private Region region;

	private LocalDate startDate;

	private int travelDays;

	private boolean isEnd;

	public TravelPlanResponseModel(TravelPlan travelPlan) {
		this.id = travelPlan.readUUID();
		this.title = travelPlan.getTitle();
		this.region = travelPlan.getRegion();
		this.startDate = travelPlan.getStartDate();
		this.travelDays = travelPlan.getTravelDays();
		this.isEnd = travelPlan.isEnd();
	}
}
