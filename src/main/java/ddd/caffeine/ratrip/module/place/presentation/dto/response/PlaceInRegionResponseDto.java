package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceBookmarkDao;
import lombok.Getter;

@Getter
public class PlaceInRegionResponseDto {
	private List<PlaceInRegionResponse> places;
	private boolean hasNext;

	public PlaceInRegionResponseDto(List<PlaceBookmarkDao> contents, boolean hasNext) {
		mapToPlaceInRegionResponses(contents);
		this.hasNext = hasNext;
	}

	public void mapToPlaceInRegionResponses(List<PlaceBookmarkDao> contents) {
		this.places = new ArrayList<>();
		for (PlaceBookmarkDao content : contents) {
			this.places.add(mapPlaceInRegionResponse(content));
		}
	}

	private PlaceInRegionResponse mapPlaceInRegionResponse(PlaceBookmarkDao content) {
		return PlaceInRegionResponse.builder()
			.id(content.getId())
			.name(content.getName())
			.category(content.getCategory().name())
			.address(content.getAddress())
			.location(content.getLocation())
			.imageLink(content.getImageLink())
			.telephone(content.getTelephone())
			.bookmark(createBookmarkContent(content.getIsActivated()))
			.build();
	}

	private BookmarkResponseDto createBookmarkContent(Boolean isActivated) {
		if (isActivated == null) {
			return new BookmarkResponseDto();
		}
		return new BookmarkResponseDto(isActivated);
	}
}
