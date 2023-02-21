package ddd.caffeine.ratrip.module.place.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Place;
import lombok.Getter;

@Getter
public class PlaceDetailBookmarkDao {

	private Place place;
	private Boolean isActivated;

	@QueryProjection
	public PlaceDetailBookmarkDao(Place place, Boolean isActivated) {
		this.place = place;
		this.isActivated = isActivated;
	}
}
