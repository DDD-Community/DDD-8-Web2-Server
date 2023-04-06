package ddd.caffeine.ratrip.module.memo.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Location;
import lombok.Getter;

@Getter
public class ShortestPathDao {
	private final Long id;
	private final String name;
	private final Location location;

	@QueryProjection
	public ShortestPathDao(Long id, String name, Location location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}
}
