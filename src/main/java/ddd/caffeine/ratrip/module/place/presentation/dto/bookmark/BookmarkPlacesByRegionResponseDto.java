package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.BookmarkPlaceByRegionDao;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkPlacesByRegionResponseDto {
	private List<BookmarkPlaceByRegionDao> places;
	private boolean hasNext;
}
