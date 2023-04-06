package ddd.caffeine.ratrip.module.bookmark.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.travel_plan.place.domain.Category;
import lombok.Getter;

@Getter
public class BookmarkByCategoryDao {
	private final Long id;
	private final String placeKakaoId;
	private final String name;
	private final String detailAddress;
	private final Category category;

	@QueryProjection
	public BookmarkByCategoryDao(Long id, String placeKakaoId, String name, String detailAddress, Category category) {
		this.id = id;
		this.placeKakaoId = placeKakaoId;
		this.name = name;
		this.detailAddress = detailAddress;
		this.category = category;
	}
}
