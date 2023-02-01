package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.request;

import javax.validation.constraints.Size;

import ddd.caffeine.ratrip.common.validator.annotation.UUIDFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DayScheduleAddPlaceRequestDto {
	@UUIDFormat
	private String id;
	@Size(max = 255, message = "최대 글자수는 255자 입니다.")
	private String memo;

	public DayScheduleAddPlaceRequestDto(String id, String memo) {
		this.id = id;
		this.memo = memo;
	}
}
