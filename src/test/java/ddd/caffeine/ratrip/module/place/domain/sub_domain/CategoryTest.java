package ddd.caffeine.ratrip.module.place.domain.sub_domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategoryTest {

	@Test
	@DisplayName("setPlaceCategory 메서드 정상 동작 테스트")
	void NormalSetPlaceCategoryTest() {
		//given
		String cafeCategoryCode = "CE7";

		//when
		Category category = Category.createByCode(cafeCategoryCode);

		//then
		Assertions.assertThat(category).isEqualTo(Category.CAFE);
	}

	@ParameterizedTest
	@DisplayName("setPlaceCategory 관리하지 않는 카테고리일 경우 동작 테스트")
	@ValueSource(strings = {"CE", "정지원", "AT1C", ""})
	void 기타SetPlaceCategoryTest() {
		//given
		String 기타CategoryCode = "관리 하지 않는 카테고리 코드";

		//when
		Category category = Category.createByCode(기타CategoryCode);

		//then
		Assertions.assertThat(category).isEqualTo(Category.ETC);
	}
}