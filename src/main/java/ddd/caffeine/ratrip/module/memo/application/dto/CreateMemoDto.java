package ddd.caffeine.ratrip.module.memo.application.dto;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateMemoDto {
	private final Long dayPlanId;
	private final String kakaoId;
	private final int sequence;
	private final String content;

	public Memo toEntity(DayPlan dayPlan, User user, Place place) {
		return Memo.of(dayPlan, sequence, content, place, user);
	}

	public static CreateMemoDto of(Long dayPlanId, String kakaoId, int sequence, String content) {
		return new CreateMemoDto(dayPlanId, kakaoId, sequence, content);
	}

}
