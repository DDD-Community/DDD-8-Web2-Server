package ddd.caffeine.ratrip.module.place.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ddd.caffeine.ratrip.module.place.domain.Place;

class PlaceTest {

	/**
	 * place 엔티티 생성 메서드 (NotNull 조건 걸린 필드만 생성)
	 */
	private Place createPlaceExceptNotNullField(String thirdPartyID, String name, String address, String categoryCode,
		double x, double y) {

		Place place = Place.builder()
			.kakaoId(thirdPartyID)
			.name(name)
			.build();

		place.setCategoryByCode(categoryCode);
		place.setAddress(address);
		place.setLocation(y, x);
		return place;
	}
}