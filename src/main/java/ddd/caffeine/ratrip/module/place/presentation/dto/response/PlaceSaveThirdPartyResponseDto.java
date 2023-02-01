package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Location;
import lombok.Getter;

/**
 * @Todo : 프론트 및 디자인분들과 해당 필드 상의
 */
@Getter
public class PlaceSaveThirdPartyResponseDto {
	private UUID id;
	private String name;
	private String category;
	private String address;
	private Location location;
	private String telephone;
	private boolean isUpdated;

	public PlaceSaveThirdPartyResponseDto(Place place) {
		this.id = place.getId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.isUpdated();
		this.telephone = place.getTelephone();
	}
}
