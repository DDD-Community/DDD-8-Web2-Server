package ddd.caffeine.ratrip.module.place.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends AuditingTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private String kakaoId;

	@NotNull
	@Column(columnDefinition = "VARCHAR(100)")
	private String name;

	@Enumerated(EnumType.STRING)
	private Category category;

	@NotNull
	@Embedded
	private Location location;

	@NotNull
	@Embedded
	private Address address;

	@NotNull
	@Column
	private long viewCount;

	@NotNull
	@Column
	private long tripCount;

	@NotNull
	@Column
	private long bookmarkCount;

	@NotNull
	@Column
	private long totalScore;

	@Column(columnDefinition = "VARCHAR(255)")
	private String imageLink;

	@Column(columnDefinition = "VARCHAR(255)")
	private String additionalInfoLink;

	@Column(columnDefinition = "VARCHAR(100)")
	private String telephone;

	@NotNull
	@Column(columnDefinition = "TINYINT(1)")
	private boolean isDeleted;

	@Builder
	public Place(String kakaoId, String name, String additionalInfoLink, String telephone) {
		this.kakaoId = kakaoId;
		this.name = name;
		this.additionalInfoLink = additionalInfoLink;
		this.telephone = telephone;
		this.isDeleted = false;
		this.tripCount = 0L;
		this.bookmarkCount = 0L;
		this.viewCount = 1L;
		this.totalScore = 0L;
	}

	public void setLocation(double latitude, double longitude) {
		this.location = new Location(latitude, longitude);
	}

	public void setAddress(String address) {
		this.address = new Address(address);
	}

	public void setCategoryByCode(String categoryCode) {
		this.category = Category.createByCode(categoryCode);
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void increaseBookmarkCount() {
		this.bookmarkCount++;
	}

	public void update(Place place) {
		this.kakaoId = place.getKakaoId();
		this.name = place.getName();
		this.category = place.getCategory();
		this.address = place.getAddress();
		this.location = place.getLocation();
		this.imageLink = place.getImageLink();
		this.additionalInfoLink = place.getAdditionalInfoLink();
		this.telephone = place.getTelephone();
		this.bookmarkCount = place.getBookmarkCount();
		this.tripCount = place.getTripCount();
		this.viewCount = place.getViewCount();
		this.totalScore = place.getTotalScore();
		// this.blogs = place.getBlogs();
	}
}