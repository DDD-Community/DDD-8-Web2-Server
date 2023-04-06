package ddd.caffeine.ratrip.module.travel_plan.place.feign.kakao.model;

import java.util.List;

import lombok.Getter;

@Getter
public class KakaoRegionResponse {
	private KakaoRegionMeta meta;
	private List<KakaoRegionDocument> documents;
}
