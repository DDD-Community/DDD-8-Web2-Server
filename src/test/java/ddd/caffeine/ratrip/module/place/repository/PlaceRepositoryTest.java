package ddd.caffeine.ratrip.module.place.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.module.Region;
import ddd.caffeine.ratrip.module.place.model.Place;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceRepositoryTest {

	@Autowired
	PlaceRepository placeRepository;

	@Test
	@DisplayName("findByKakaoId 정상 동작 테스트")
	void findByKakaoIdTest() {
		//given
		Place testPlace = createPlace("testId", "testName", "testAddress", "testCategoryCode", 120.365, 34.678,
			"testLink",
			"testPhoneNumber");
		placeRepository.save(testPlace);

		//when
		Optional<Place> place = placeRepository.findByKakaoId("testId");

		//then
		assertThat(place).isPresent();
		assertThat(place.get().getId()).isNotNull();
		assertThat(place.get().getName()).isEqualTo("testName");
	}

	@Test
	@DisplayName("여러 지역의 장소 찾기 정상 동작 테스트")
	void findPlacesInRegionsTest() {
		//given
		Place seoulFamousPlace = createPlace("testId", "서면 스타벅스", "부산 서면 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");
		Place seoulPlace = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");
		Place incheonPlace = createPlace("testId", "부평 스타벅스", "인천 부평 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");

		placeRepository.save(seoulFamousPlace);
		placeRepository.save(seoulPlace);
		placeRepository.save(incheonPlace);

		//when
		List<Region> 특정지역 = List.of(Region.서울특별시, Region.부산광역시, Region.인천광역시);
		List<Place> places = placeRepository.findPopularPlacesInRegions(특정지역, 5);

		//then
		assertThat(places.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("특정 지역이 빈 리스트 일때 모든 지역 불러오는지 정상 작동 테스트")
	void findPopularPlacesIfEmptyListTest() {
		//given
		Place testPlace기타 = createPlace("testId", "testName", "testAddress", "testCategoryCode", 120.365, 34.678,
			"testLink",
			"testPhoneNumber");
		Place testPlace양재 = createPlace("testId", "testName", "서울 양재동 스타벅스", "testCategoryCode", 120.365, 34.678,
			"testLink",
			"testPhoneNumber");

		placeRepository.save(testPlace기타);
		placeRepository.save(testPlace양재);

		//when
		List<Region> regions = new ArrayList<>();
		List<Place> places = placeRepository.findPopularPlacesInRegions(regions, 10);

		//then
		assertThat(places.size()).isEqualTo(2);
		assertThat(places.contains(testPlace기타));
		assertThat(places.contains(testPlace양재));
	}

	@Test
	@DisplayName("특정 지역의 인기 많은 장소 찾기 정상 동작 테스트")
	void findPopularPlacesInRegionsTest() {
		//given
		Place seoulFamousPlace = createPlace("testId", "강남 스타벅스", "서울 강남 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");
		Place seoulPlace = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");
		Place incheonPlace = createPlace("testId", "부평 스타벅스", "인천 부평 스타벅스 까페", "CF7", 1, 1,
			"testLink",
			"testPhoneNumber");

		seoulFamousPlace.travelCome();
		placeRepository.save(seoulFamousPlace);
		placeRepository.save(seoulPlace);
		placeRepository.save(incheonPlace);

		//when
		List<Region> 특정지역 = List.of(Region.서울특별시);
		List<Place> places = placeRepository.findPopularPlacesInRegions(특정지역, 2);
		boolean isOkApplyOrderOption = places.get(0).getNumberOfTrips() > places.get(1).getNumberOfTrips();

		//then
		assertThat(places.size()).isEqualTo(2);
		assertThat(places.contains(incheonPlace)).isFalse();

		assertThat(isOkApplyOrderOption).isTrue();
		assertThat(places.get(0).getName()).isEqualTo("강남 스타벅스");

		assertThat(places.get(1).getName()).isEqualTo("양재 스타벅스");
	}

	/**
	 * place 엔티티 생성 메서드.
	 */
	private Place createPlace(String kakaoId, String name, String address, String categoryCode,
		double x, double y, String imageLink, String telephone) {
		Place place = Place.builder()
			.kakaoId(kakaoId)
			.name(name)
			.telephone(telephone)
			.build();

		place.injectImageLink(imageLink);
		place.setPlaceCategory(categoryCode);
		place.setAddress(address);
		place.setLocation(y, x);

		return place;
	}
}