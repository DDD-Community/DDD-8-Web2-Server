package ddd.caffeine.ratrip.module.travel_plan.application.day_schedule;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import ddd.caffeine.ratrip.module.travel_plan.application.DaySchedulePlaceService;
import ddd.caffeine.ratrip.module.travel_plan.application.validator.DaySchedulePlaceValidator;
import ddd.caffeine.ratrip.module.travel_plan.domain.DaySchedulePlace;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.DaySchedulePlaceRepository;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaySchedulePlaceServiceTest {

	@Mock
	DaySchedulePlaceRepository daySchedulePlaceRepository;
	DaySchedulePlaceService daySchedulePlaceService;

	@BeforeAll
	void init() {
		daySchedulePlaceService = new DaySchedulePlaceService(
			new DaySchedulePlaceValidator(), daySchedulePlaceRepository
		);
	}

	@Test
	@DisplayName("일정 내의 장소 순서 업데이트 정상 동작 테스트")
	void updatePlacesSequenceTest() {
		//given
		UUID dayScheduleUUID = UUID.fromString("11111111-1111-1111-1111-111111111111");
		List<UUID> daySchedulePlaceUUIDs = List.of(
			UUID.fromString("22222222-2222-2222-2222-222222222222"),
			UUID.fromString("11111111-1111-1111-1111-111111111111")
		);
		List<DaySchedulePlace> daySchedulePlaces = readStubDaySchedulePlaces();
		DaySchedulePlace sequence1 = daySchedulePlaces.get(0);
		DaySchedulePlace sequence2 = daySchedulePlaces.get(1);

		//when
		when(daySchedulePlaceRepository.findDaySchedulePlacesByDayScheduleUUID(dayScheduleUUID))
			.thenReturn(daySchedulePlaces);
		daySchedulePlaceService.updatePlacesSequence(dayScheduleUUID, daySchedulePlaceUUIDs);

		//then
		assertThat(sequence1.getId()).isEqualTo(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		assertThat(sequence1.getSequence()).isEqualTo(2);
		assertThat(sequence2.getId()).isEqualTo(UUID.fromString("22222222-2222-2222-2222-222222222222"));
		assertThat(sequence2.getSequence()).isEqualTo(1);
	}

	private List<DaySchedulePlace> readStubDaySchedulePlaces() {
		DaySchedulePlace sequence1 = DaySchedulePlace.builder().sequence(1).build();
		DaySchedulePlace sequence2 = DaySchedulePlace.builder().sequence(2).build();

		ReflectionTestUtils.setField(sequence1, "id",
			UUID.fromString("11111111-1111-1111-1111-111111111111"));
		ReflectionTestUtils.setField(sequence2, "id",
			UUID.fromString("22222222-2222-2222-2222-222222222222"));

		List<DaySchedulePlace> daySchedulePlaces = List.of(
			sequence1, sequence2
		);

		return daySchedulePlaces;
	}
}