package ddd.caffeine.ratrip.module.bookmark.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Category;
import lombok.Getter;

@Getter
public class BookmarkByCategoryDao {
	private final Long id;
	private final Long placeId;
	private final String name;
	private final String detailAddress;
	private final Category category;

	@QueryProjection
	public BookmarkByCategoryDao(Long id, Long placeId, String name, String detailAddress, Category category) {
		this.id = id;
		this.placeId = placeId;
		this.name = name;
		this.detailAddress = detailAddress;
		this.category = category;
	}
}
