package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import lombok.Getter;

@Getter
public class TravelPlanRepresentativePhotoResponse {
	private String link;

	public TravelPlanRepresentativePhotoResponse(String link) {
		this.link = link;
	}
}
