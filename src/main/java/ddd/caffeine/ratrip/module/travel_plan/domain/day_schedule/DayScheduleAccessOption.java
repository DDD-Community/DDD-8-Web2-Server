package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule;

import java.util.UUID;

import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.Getter;

@Getter
public class DayScheduleAccessOption {
	private User user;
	private UUID travelPlanUUID;
	private UUID dayScheduleUUID;

	public DayScheduleAccessOption(User user, String travelPlanUUID, String dayScheduleUUID) {
		this.user = user;
		this.travelPlanUUID = UUID.fromString(travelPlanUUID);
		this.dayScheduleUUID = UUID.fromString(dayScheduleUUID);
	}

	public TravelPlanAccessOption readTravelPlanAccessOption() {
		return new TravelPlanAccessOption(this.user, this.travelPlanUUID);
	}

	public UUID readDayScheduleUUID() {
		return this.dayScheduleUUID;
	}

	public UUID readTravelPlanUUID() {
		return this.travelPlanUUID;
	}
}
