package ddd.caffeine.ratrip.module.place.presentation.dto.detail;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.model.Location;
import ddd.caffeine.ratrip.module.place.model.Place;
import lombok.Getter;

/**
 * @Todo : 프론트 및 디자인분들과 해당 필드 상의
 */
@Getter
public class PlaceDetailsResponseDto {

	private UUID id;

	private String kakaoId;

	private String name;

	private String category;

	private String address;

	private Location location;

	private boolean isUpdated;

	private String imageLink;

	private String telephone;

	public PlaceDetailsResponseDto(Place place) {
		this.id = place.getId();
		this.kakaoId = place.getKakaoId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.isUpdated();
		this.imageLink = place.getImageLink();
		this.telephone = place.getTelephone();
	}
}
