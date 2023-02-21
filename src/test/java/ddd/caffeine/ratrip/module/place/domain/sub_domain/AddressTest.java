package ddd.caffeine.ratrip.module.place.domain.sub_domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Address;

class AddressTest {

	@Test
	@DisplayName("Address 정상 동작 테스트")
	void normalCreateAddressTest() {
		//given
		String 제주Address = "제주특별자치도 제주시 외도일동 640-2";

		//when
		Address address = new Address(제주Address);

		//then
		Assertions.assertThat(address.getRegion()).isEqualTo(Region.제주특별자치도);
		Assertions.assertThat(address.getDetailed()).isEqualTo("제주시 외도일동 640-2");
	}

	@Test
	@DisplayName("setAddress 관리하지 않는 Region 일 경우 동작 테스트")
	void 기타SetAddressTest() {
		//given
		String 기타Address = "XX도 제주시 외도일동 640-2";

		//when
		Address address = new Address(기타Address);

		//then
		Assertions.assertThat(address.getRegion()).isEqualTo(Region.기타);
		Assertions.assertThat(address.getDetailed()).isEqualTo("제주시 외도일동 640-2");
	}
}