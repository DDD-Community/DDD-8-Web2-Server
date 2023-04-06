package ddd.caffeine.ratrip.module.bookmark.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.bookmark.domain.repository.dao.RecommendByBookmarkDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecommendByBookmarkAndLocationResponseDto {
	private List<RecommendByBookmarkDao> places;
	private boolean hasNext;

	public static RecommendByBookmarkAndLocationResponseDto of(List<RecommendByBookmarkDao> places, boolean hasNext) {
		return new RecommendByBookmarkAndLocationResponseDto(places, hasNext);
	}
}
