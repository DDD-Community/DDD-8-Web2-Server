package ddd.caffeine.ratrip.module.place.presentation.dto.detail;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
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
	private String imageLink;
	private String additionalInfoLink;
	private String telephone;
	private boolean isUpdated;

	private BookmarkResponseDto bookmark;

	public PlaceDetailsResponseDto(Place place, BookmarkResponseDto bookmark) {
		this.id = place.getId();
		this.kakaoId = place.getKakaoId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.isUpdated();
		this.imageLink = place.getImageLink();
		this.additionalInfoLink = place.getAdditionalInfoLink();
		this.telephone = place.getTelephone();
		this.bookmark = bookmark;
	}
}
