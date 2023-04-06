package ddd.caffeine.ratrip.module.place.feign.kakao.model;

import ddd.caffeine.ratrip.module.place.domain.Region;
import lombok.Getter;

@Getter
public class KakaoRegionDocument {
	private String region_type;
	private String address_name;
	private Region region_1depth_name;
	private String region_2depth_name;
	private String region_3depth_name;
	private String region_4depth_name;
	private String code;
	private String x;
	private String y;
}
