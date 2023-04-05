package ddd.caffeine.ratrip.module.memo.application.dto;

import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Category;
import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateMemoDto {
	private final Long dayPlanId;
	private final String name;
	private final Address address;
	private final Location location;
	private final Category category;
	private final int sequence;
	private final String content;

	public Memo toEntity(DayPlan dayPlan, User user) {
		return Memo.of(dayPlan, sequence, name, address, location, category, content, user);
	}

	public static CreateMemoDto of(Long dayPlanId, String name, Address address, Location location, Category category,
		int sequence, String content) {
		return new CreateMemoDto(dayPlanId, name, address, location, category, sequence, content);
	}

}
