package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan_user.domain.TravelPlanUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Todo : 이미지 ?
@Getter
@NoArgsConstructor
public class MyTravelPlanResponseDto {

	List<MyTravelPlanResponse> contents;
	private boolean hasNext;

	@Builder
	public MyTravelPlanResponseDto(List<TravelPlanUser> contents, boolean hasNext) {
		createContents(contents);
		this.hasNext = hasNext;
	}

	public void createContents(List<TravelPlanUser> travelPlanUsers) {
		List<MyTravelPlanResponse> contents = new ArrayList<>();
		for (TravelPlanUser travelPlanUser : travelPlanUsers) {
			contents.add(new MyTravelPlanResponse(travelPlanUser.getTravelPlan()));
		}
		this.contents = contents;
	}

	public List<MyTravelPlanResponse> readContents() {
		return this.contents;
	}
}
