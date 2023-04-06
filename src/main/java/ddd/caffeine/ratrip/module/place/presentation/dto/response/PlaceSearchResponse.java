package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceSearchResponse {
	private String id;
	private String name;
	private String address;
	private String latitude;
	private String longitude;
}
