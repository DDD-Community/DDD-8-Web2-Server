package ddd.caffeine.ratrip.module.place.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
	private String placeLink;

	@Column(columnDefinition = "VARCHAR(100)")
	private String telephone;

	@NotNull
	@Column(columnDefinition = "TINYINT(1)")
	private Boolean isDeleted;

	@ElementCollection
	private List<Blog> blogs = new ArrayList<>();

	@Builder
	public Place(String kakaoId, String name, String placeLink, String telephone, Category category,
		Location location, Address address, String imageLink, List<Blog> blogs) {
		this.kakaoId = kakaoId;
		this.name = name;
		this.placeLink = placeLink;
		this.telephone = telephone;
		this.category = category;
		this.location = location;
		this.address = address;
		this.imageLink = imageLink;
		this.blogs = blogs;
		this.isDeleted = false;
		this.tripCount = 0L;
		this.bookmarkCount = 0L;
		this.viewCount = 1L;
		this.totalScore = 0L;
	}

	public static Place of(String kakaoId, String name, String placeLink, String telephone, Category category,
		Location location, Address address, String imageLink, List<Blog> blogs) {
		return Place.builder()
			.kakaoId(kakaoId)
			.name(name)
			.placeLink(placeLink)
			.telephone(telephone)
			.category(category)
			.location(location)
			.address(address)
			.imageLink(imageLink)
			.blogs(blogs)
			.build();
	}

	public void increaseViewCount() {
		System.out.println("호출됨");
		this.viewCount++;
	}

	public void increaseBookmarkCount() {
		this.bookmarkCount++;
	}

	public void updateToNewData(Place place) {
		this.kakaoId = place.getKakaoId();
		this.name = place.getName();
		this.category = place.getCategory();
		this.address = place.getAddress();
		this.location = place.getLocation();
		this.imageLink = place.getImageLink();
		this.placeLink = place.getPlaceLink();
		this.telephone = place.getTelephone();
		this.blogs = place.getBlogs();
		this.bookmarkCount = place.getBookmarkCount();
		this.tripCount = place.getTripCount();
		this.viewCount = place.getViewCount();
		this.totalScore = place.getTotalScore();
	}
}