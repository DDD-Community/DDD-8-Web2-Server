package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookMarkPlaceDao;
import lombok.Getter;

@Getter
public class BookmarkPlaceResponseDto {
	private List<BookmarkPlaceResponse> places;
	private boolean hasNext;

	public BookmarkPlaceResponseDto(List<BookMarkPlaceDao> places, boolean hasNext) {
		mapBookMarkResponseModels(places);
		this.hasNext = hasNext;
	}

	private void mapBookMarkResponseModels(List<BookMarkPlaceDao> places) {
		this.places = new ArrayList<>();
		for (BookMarkPlaceDao place : places) {
			this.places.add(new BookmarkPlaceResponse(place));
		}
	}
}
