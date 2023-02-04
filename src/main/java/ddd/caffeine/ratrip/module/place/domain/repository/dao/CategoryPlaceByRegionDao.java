package ddd.caffeine.ratrip.module.place.domain.repository.dao;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class CategoryPlaceByRegionDao {
	private final UUID id;
	private final String name;

	@QueryProjection
	public CategoryPlaceByRegionDao(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
}
