package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Todo : 필드 변경 될 수 있음. (와이어프레임 참고)
 */
@Getter
@NoArgsConstructor
public class PlaceInRegionResponse {
	private UUID id;
	private String name;
	private String category;
	private String imageLink;
	private Location location;

	public PlaceInRegionResponse(Place place) {
		this.id = place.getId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.imageLink = place.getImageLink();
		this.location = place.getLocation();
	}
}
