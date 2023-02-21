package ddd.caffeine.ratrip.common.model;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ddd.caffeine.ratrip.common.model.Region;

class RegionTest {
	@Test
	@DisplayName("keyword 가 Region 에 정의되지 않은 단어일때, Empty 를 리턴 되는지 확인 테스트")
	void createRegionsReturnEmptyTest() {
		//given
		String keyword = "EMPTY 반환합니다.";
		//when
		List<Region> regions = Region.createRegions(List.of(keyword));
		//then
		assertThat(regions).isEmpty();
	}

	@Test
	@DisplayName("keywords 가 빈 리스트 일때, 정상 작동 확인 테스트")
	void createRegionsNotNPETest() {
		//given
		List<String> keywords = new ArrayList<>();
		//when
		List<Region> regions = Region.createRegions(keywords);
		//then
		assertThat(regions).isEmpty();
	}
}