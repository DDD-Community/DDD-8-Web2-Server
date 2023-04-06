package ddd.caffeine.ratrip.module.travel_plan.place.domain;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum Region {
	서울특별시("서울"),
	부산광역시("부산"),
	대구광역시("대구"),
	인천광역시("인천"),
	대전광역시("대전"),
	울산광역시("울산"),
	세종특별자치시("세종"),
	경기도("경기"),
	강원도("강원"),
	충청북도("충북"),
	충청남도("충남"),
	전라북도("전북"),
	전라남도("전남"),
	경상북도("경북"),
	경상남도("경남"),
	제주특별자치도("제주"),
	기타("기타");

	private String keyword;

	Region(String keyword) {
		this.keyword = keyword;
	}

	public static Region of(String keyword) {
		Optional<Region> region = Arrays.stream(values()).filter(r -> r.name().contains(keyword))
			.findFirst();

		return region.orElse(Region.기타);
	}
}
