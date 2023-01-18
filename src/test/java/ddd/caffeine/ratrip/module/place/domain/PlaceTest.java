package ddd.caffeine.ratrip.module.place.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ddd.caffeine.ratrip.common.model.Region;

class PlaceTest {

	@BeforeEach
	void init() {
	}

	@Test
	@DisplayName("setPlaceCategory 메서드 정상 동작 테스트")
	void NormalSetPlaceCategoryTest() {
		//given
		String cafeCategoryCode = "CE7";
		Place placeCafe = new Place();

		//when
		placeCafe.setPlaceCategory(cafeCategoryCode);

		//then
		Assertions.assertThat(placeCafe.getCategory()).isEqualTo(Category.CAFE);
	}

	@Test
	@DisplayName("setPlaceCategory 관리하지 않는 카테고리일 경우 동작 테스트")
	void 기타SetPlaceCategoryTest() {
		//given
		String 기타CategoryCode = "관리 하지 않는 카테고리 코드";
		Place placeNoCategory = new Place();

		//when
		placeNoCategory.setPlaceCategory(기타CategoryCode);

		//then
		Assertions.assertThat(placeNoCategory.getCategory()).isEqualTo(Category.ETC);
	}

	@Test
	@DisplayName("setAddress 메서드 정상 동작 테스트")
	void normalSetAddressTest() {
		//given
		String 제주Address = "제주특별자치도 제주시 외도일동 640-2";
		Place 제주Place = new Place();

		//when
		제주Place.setAddress(제주Address);

		//then
		Address 제주 = 제주Place.getAddress();
		Assertions.assertThat(제주.getRegion()).isEqualTo(Region.제주특별자치도);
		Assertions.assertThat(제주.getDetailed()).isEqualTo("제주시 외도일동 640-2");
	}

	@Test
	@DisplayName("setAddress 관리하지 않는 Region 일 경우 동작 테스트")
	void 기타SetAddressTest() {
		//given
		String 기타Address = "XX도 제주시 외도일동 640-2";
		Place 기타Place = new Place();

		//when
		기타Place.setAddress(기타Address);

		//then
		Address 기타 = 기타Place.getAddress();
		Assertions.assertThat(기타.getRegion()).isEqualTo(Region.기타);
		Assertions.assertThat(기타.getDetailed()).isEqualTo("제주시 외도일동 640-2");
	}
}