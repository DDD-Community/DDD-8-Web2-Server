package ddd.caffeine.ratrip.module.travel_plan.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository.DayScheduleRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TravelPlanRepositoryTest {
	@Autowired
	TravelPlanRepository travelPlanRepository;

	@Test
	@DisplayName("진행중인 모든 여행 계획 불러오기 정상 작동 테스트")
	void findAllOngoingTravelPlanTest() {
		//given
		TravelPlan travelPlan01 = createTravelPlan("title01", Region.서울특별시, 1, LocalDate.now());
		TravelPlan travelPlan02 = createTravelPlan("title02", Region.강원도, 1, LocalDate.now());
		travelPlan02.endTheTrip();
		travelPlanRepository.save(travelPlan01);
		travelPlanRepository.save(travelPlan02);
		//when
		List<TravelPlan> travelPlans = travelPlanRepository.findAllOngoingTravelPlan();
		//then
		assertThat(travelPlans.size()).isEqualTo(1);
		assertThat(travelPlans.contains(travelPlan01)).isTrue();
		assertThat(travelPlans.contains(travelPlan02)).isFalse();
	}

	private TravelPlan createTravelPlan(String title, Region region, int travelDays, LocalDate startDate) {
		return TravelPlan.builder()
			.title(title)
			.region(region)
			.travelDays(travelDays)
			.startDate(startDate)
			.build();
	}
}