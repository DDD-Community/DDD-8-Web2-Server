package ddd.caffeine.ratrip.module.place.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThirdPartySearchOption {
	private String keyword;

	private String latitude;

	private String longitude;

	private int page;
}
