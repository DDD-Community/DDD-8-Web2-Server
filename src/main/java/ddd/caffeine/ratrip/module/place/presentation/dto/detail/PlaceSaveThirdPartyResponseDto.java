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
public class PlaceSaveThirdPartyResponseDto {
	private UUID id;
	private String name;
	private String category;
	private String address;
	private Location location;
	private String telephone;
	private boolean isUpdated;
	private BookmarkResponseDto bookmark;

	public PlaceSaveThirdPartyResponseDto(Place place, BookmarkResponseDto bookmark) {
		this.id = place.getId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.isUpdated();
		this.telephone = place.getTelephone();
		this.bookmark = bookmark;
	}
}
