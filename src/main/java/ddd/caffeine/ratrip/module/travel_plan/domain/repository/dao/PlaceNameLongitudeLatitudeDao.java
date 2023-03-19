package ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PlaceNameLongitudeLatitudeDao {
	private final UUID id;
	private final String name;
	private final double longitude;
	private final double latitude;

	@QueryProjection
	public PlaceNameLongitudeLatitudeDao(final UUID id, final String name, final double longitude,
		final double latitude) {
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
