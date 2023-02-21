package ddd.caffeine.ratrip.module.place.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThirdPartyDetailSearchOption {
	private String id;

	private String placeName;

	private String address;

	public String readThirdPartyId() {
		return this.id;
	}
}
