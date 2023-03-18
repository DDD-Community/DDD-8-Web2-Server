package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceBookmarkDao;
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

	public PlaceSaveThirdPartyResponseDto(PlaceBookmarkDao place) {
		this.id = place.getId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.getIsUpdated();
		this.telephone = place.getTelephone();
		this.bookmark = createBookmarkContent(place.getIsActivated());
	}

	public PlaceSaveThirdPartyResponseDto(Place place, BookmarkResponseDto bookmarkContent) {
		this.id = place.getId();
		this.name = place.getName();
		this.category = place.getCategory().name();
		this.address = place.getAddress().toString();
		this.location = place.getLocation();
		this.isUpdated = place.isUpdated();
		this.telephone = place.getTelephone();
		this.bookmark = bookmarkContent;
	}

	private BookmarkResponseDto createBookmarkContent(Boolean isActivated) {
		if (isActivated == null) {
			return new BookmarkResponseDto();
		}
		return new BookmarkResponseDto(isActivated);
	}
}
