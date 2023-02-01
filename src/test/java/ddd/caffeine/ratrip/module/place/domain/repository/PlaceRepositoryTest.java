package ddd.caffeine.ratrip.module.place.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.Place;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlaceRepositoryTest {

	@Autowired
	PlaceRepository placeRepository;

	@Test
	@DisplayName("findByThirdPartyID 정상 동작 테스트")
	void findByThirdPartyIDTest() {
		//given
		Place testPlace = createPlace("testId", "testName", "testAddress", "testCategoryCode", 120.365, 34.678,
			"testLink", "testLink", "testPhoneNumber");

		placeRepository.save(testPlace);

		//when
		Place place = placeRepository.findByThirdPartyID("testId");

		//then
		assertThat(place).isNotNull();
		assertThat(place.getId()).isNotNull();
		assertThat(place.getName()).isEqualTo("testName");
	}

	@Test
	@DisplayName("여러 지역의 장소 찾기 정상 동작 테스트")
	void findPlacesInRegionsTest() {
		//given
		Place busanPlace = createPlace("testId", "서면 스타벅스", "부산 서면 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");
		Place seoulPlace = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");
		Place incheonPlace = createPlace("testId", "부평 스타벅스", "인천 부평 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");

		placeRepository.save(busanPlace);
		placeRepository.save(seoulPlace);
		placeRepository.save(incheonPlace);

		Pageable pageRequest = PageRequest.of(0, 3);

		//when
		List<Region> 특정지역 = List.of(Region.서울특별시, Region.부산광역시, Region.인천광역시);
		Slice<Place> places = placeRepository.findPlacesInRegions(특정지역, pageRequest);

		//then
		assertThat(places.getContent().size()).isEqualTo(3);
	}

	@Test
	@DisplayName("특정 지역이 빈 리스트 일때 모든 지역 불러오는지 정상 작동 테스트")
	void findPlacesInRegionsIfEmptyListTest() {
		//given
		Place testPlace기타 = createPlace("testId", "testName", "testAddress", "testCategoryCode", 120.365, 34.678,
			"testLink", "testLink", "testPhoneNumber");

		Place testPlace양재 = createPlace("testId", "testName", "서울 양재동 스타벅스", "testCategoryCode", 120.365, 34.678,
			"testLink", "testLink", "testPhoneNumber");

		placeRepository.save(testPlace기타);
		placeRepository.save(testPlace양재);

		Pageable pageRequest = PageRequest.ofSize(2);

		//when
		List<Region> regions = new ArrayList<>();
		Slice<Place> places = placeRepository.findPlacesInRegions(regions, pageRequest);

		//then
		assertThat(places.getContent().size()).isEqualTo(2);
		assertThat(places.getContent().contains(testPlace기타));
		assertThat(places.getContent().contains(testPlace양재));
	}

	@Test
	@DisplayName("특정 지역의 인기 많은 장소 찾기 정상 동작 테스트")
	void findPlacesInRegionsSortPopularTest() {
		//given
		Place seoulFamousPlace = createPlace("testId", "강남 스타벅스", "서울 강남 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");

		Place seoulPlace = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");
		Place incheonPlace = createPlace("testId", "부평 스타벅스", "인천 부평 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");

		seoulFamousPlace.travelCome();
		placeRepository.save(seoulFamousPlace);
		placeRepository.save(seoulPlace);

		List<Region> 특정지역 = List.of(Region.서울특별시);
		Pageable pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "popular");

		//when
		Slice<Place> places = placeRepository.findPlacesInRegions(특정지역, pageRequest);

		//then
		assertThat(places.getContent().size()).isEqualTo(1);

		assertThat(places.getContent().contains(seoulPlace)).isFalse();
		assertThat(places.hasNext()).isTrue();
	}

	/**
	 * place 엔티티 생성 메서드.
	 */
	private Place createPlace(String kakaoId, String name, String address, String categoryCode,
		double x, double y, String imageLink, String additionalInfoLink, String telephone) {
		Place place = Place.builder()
			.kakaoId(kakaoId)
			.name(name)
			.additionalInfoLink(additionalInfoLink)
			.telephone(telephone)
			.build();

		place.setImageLink(imageLink);
		place.setCategoryByCode(categoryCode);
		place.setAddress(address);
		place.setLocation(y, x);

		return place;
	}
}