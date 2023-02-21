package ddd.caffeine.ratrip.module.place.domain.repository.dao;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.sub_domain.Address;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Location;
import lombok.Getter;

@Getter
public class PlaceBookmarkDao {
	private UUID id;
	private String name;
	private Category category;
	private Address address;
	private Location location;
	private String imageLink;
	private String telephone;
	private Boolean isUpdated;

	private Boolean isActivated;

	@QueryProjection
	public PlaceBookmarkDao(UUID id, String name, Category category, Address address, Location location,
		String imageLink,
		String telephone, Boolean isUpdated, Boolean isActivated) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.address = address;
		this.location = location;
		this.imageLink = imageLink;
		this.telephone = telephone;
		this.isUpdated = isUpdated;
		this.isActivated = isActivated;
	}
}
