package ddd.caffeine.ratrip.module.travel_plan.domain.day_schedule.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedule;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.DayScheduleRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DayScheduleRepositoryTest {

	@Autowired
	DayScheduleRepository dayScheduleRepository;

	@Test
	@DisplayName("saveAll DB connection 1번 정상 작동 확인")
	public void saveAllTest() {
		//given
		List<DaySchedule> daySchedules = List.of(
			DaySchedule.builder().date(LocalDate.of(2023, 1, 16)).build(),
			DaySchedule.builder().date(LocalDate.of(2023, 1, 17)).build(),
			DaySchedule.builder().date(LocalDate.of(2023, 1, 18)).build()
		);

		//when
		dayScheduleRepository.saveAll(daySchedules);
		List<DaySchedule> responses = dayScheduleRepository.findAll();

		//then
		assertThat(responses.size()).isEqualTo(3);
	}
}