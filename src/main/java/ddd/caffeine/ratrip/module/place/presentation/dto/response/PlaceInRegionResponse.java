package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Location;
import ddd.caffeine.ratrip.module.place.presentation.dto.bookmark.BookmarkResponseDto;
import lombok.Builder;
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
	private Address address;
	private Location location;
	private String imageLink;
	private String telephone;
	private BookmarkResponseDto bookmark;

	@Builder
	public PlaceInRegionResponse(UUID id, String name, String category, Address address, Location location,
		String imageLink, String telephone, BookmarkResponseDto bookmark) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.address = address;
		this.location = location;
		this.imageLink = imageLink;
		this.telephone = telephone;
		this.bookmark = bookmark;
	}
}
