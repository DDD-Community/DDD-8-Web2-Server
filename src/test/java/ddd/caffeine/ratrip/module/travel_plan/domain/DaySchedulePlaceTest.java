package ddd.caffeine.ratrip.module.travel_plan.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.DaySchedulePlace;

class DaySchedulePlaceTest {

	@Test
	@DisplayName("exchangeOrder 메서드 정상 동작 테스트")
	void exchangeOrderTest() {
		//given
		DaySchedulePlace baseDaySchedulePlace = DaySchedulePlace.builder()
			.sequence(1)
			.build();
		DaySchedulePlace exchangeDaySchedulePlace = DaySchedulePlace.builder()
			.sequence(2)
			.build();

		//when
		baseDaySchedulePlace.exchangeOrder(exchangeDaySchedulePlace);

		//then
		assertThat(baseDaySchedulePlace.getSequence()).isEqualTo(2);
		assertThat(exchangeDaySchedulePlace.getSequence()).isEqualTo(1);
	}
}