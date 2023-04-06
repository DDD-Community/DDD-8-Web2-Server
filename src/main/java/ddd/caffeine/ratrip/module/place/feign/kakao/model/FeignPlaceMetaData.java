package ddd.caffeine.ratrip.module.place.feign.kakao.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FeignPlaceMetaData {
	private Boolean isEnd;
	private Integer pageableCount;
	private SameName sameName;
	private Integer totalCount;

	@NoArgsConstructor
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	class SameName {
		private String keyword;
		private String[] region;
		private String selectedRegion;
	}
}
