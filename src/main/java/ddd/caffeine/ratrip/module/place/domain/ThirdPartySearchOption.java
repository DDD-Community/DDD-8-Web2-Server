package ddd.caffeine.ratrip.module.place.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThirdPartySearchOption {
	private String keyword;

	private double latitude;

	private double longitude;

	private int page;

	public String readLatitude() {
		return String.valueOf(latitude);
	}

	public String readLongitude() {
		return String.valueOf(longitude);
	}
}
