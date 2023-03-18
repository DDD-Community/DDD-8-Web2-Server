package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import lombok.Getter;

@Getter
public class MyTravelPlanResponse {
	private UUID id;
	private String title;
	private Region region;
	private LocalDate startDate;
	private int travelDays;
	private boolean isEnd;
	private TravelPlanRepresentativePhotoResponse photo;

	public MyTravelPlanResponse(TravelPlan travelPlan) {
		this.id = travelPlan.readUUID();
		this.title = travelPlan.getTitle();
		this.region = travelPlan.getRegion();
		this.startDate = travelPlan.getStartDate();
		this.travelDays = travelPlan.getTravelDays();
		this.isEnd = travelPlan.isEnd();
	}

	public UUID readTravelPlanId() {
		return this.id;
	}

	public LocalDate readStartDate() {
		return this.startDate;
	}

	public void setRepresentativePhoto(String link) {
		this.photo = new TravelPlanRepresentativePhotoResponse(link);
	}
}
