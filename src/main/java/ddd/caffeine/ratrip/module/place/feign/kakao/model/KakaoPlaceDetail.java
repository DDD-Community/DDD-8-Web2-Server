package ddd.caffeine.ratrip.module.place.feign.kakao.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPlaceDetail {
	private String id;
	private String categoryGroupCode;
	private String categoryGroupName;
	private String categoryName;
	private String phone;
	private String placeName;
	private String placeUrl;
	private String roadAddressName;
	private String addressName;
	private String x;
	private String y;
}

