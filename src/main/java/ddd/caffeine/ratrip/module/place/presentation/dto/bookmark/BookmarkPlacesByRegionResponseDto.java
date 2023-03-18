package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookmarkPlaceByRegionDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookmarkPlacesByRegionResponseDto {
	private List<BookmarkPlaceByRegionDao> places;
	private boolean hasNext;

	public static BookmarkPlacesByRegionResponseDto of(List<BookmarkPlaceByRegionDao> places, boolean hasNext) {
		return new BookmarkPlacesByRegionResponseDto(places, hasNext);
	}
}
