package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.bookmark.dao.BookMarkPlaceDao;
import lombok.Getter;

@Getter
public class BookmarkPlaceResponseDto {
	private List<BookmarkPlaceModel> places;
	private boolean hasNext;

	public BookmarkPlaceResponseDto(List<BookMarkPlaceDao> places, boolean hasNext) {
		mapBookMarkResponseModels(places);
		this.hasNext = hasNext;
	}

	private void mapBookMarkResponseModels(List<BookMarkPlaceDao> places) {
		this.places = new ArrayList<>();
		for (BookMarkPlaceDao place : places) {
			this.places.add(new BookmarkPlaceModel(place));
		}
	}
}
