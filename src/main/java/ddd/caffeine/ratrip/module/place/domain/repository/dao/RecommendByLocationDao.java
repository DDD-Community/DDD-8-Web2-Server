package ddd.caffeine.ratrip.module.place.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Category;
import lombok.Getter;

@Getter
public class RecommendByLocationDao {
	private final Long id;
	private final String placeKakaoId;
	private final String name;
	private final String detailAddress;
	private final Category category;
	private final String imageLink;

	@QueryProjection
	public RecommendByLocationDao(Long id, String placeKakaoId, String name, String detailAddress, Category category,
		String imageLink) {
		this.id = id;
		this.placeKakaoId = placeKakaoId;
		this.name = name;
		this.detailAddress = detailAddress;
		this.category = category;
		this.imageLink = imageLink;
	}
}
