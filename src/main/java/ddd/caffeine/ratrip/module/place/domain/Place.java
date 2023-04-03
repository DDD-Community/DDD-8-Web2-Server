package ddd.caffeine.ratrip.module.place.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import ddd.caffeine.ratrip.common.util.SequentialUUIDGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Place extends AuditingTimeEntity {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

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
	private Address address;

	@ElementCollection
	private List<Blog> blogs = new ArrayList<>();

	@NotNull
	@Embedded
	private Location location;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
	private boolean isDeleted;

	@NotNull
	@Column(name = "is_updated", columnDefinition = "TINYINT(1)")
	private boolean isUpdated;

	@Column(columnDefinition = "VARCHAR(255)")
	private String imageLink;

	@Column(columnDefinition = "VARCHAR(255)")
	private String additionalInfoLink;

	@Column(columnDefinition = "VARCHAR(100)")
	private String telephone;

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

	@OneToMany(mappedBy = "place")
	private List<Bookmark> bookmarks = new ArrayList<>();

	public void travelCome() {
		this.tripCount++;
	}

	@PrePersist
	public void createPrimaryKey() {
		//sequential uuid 생성
		this.id = SequentialUUIDGenerator.generate();
	}

	@Builder
	public Place(String kakaoId, String name, String additionalInfoLink, String telephone) {
		this.kakaoId = kakaoId;
		this.name = name;
		this.additionalInfoLink = additionalInfoLink;
		this.telephone = telephone;
		this.isDeleted = false;
		this.isUpdated = false;
		this.tripCount = 0L;
		this.bookmarkCount = 0L;
		this.viewCount = 1L;
		this.totalScore = 0L;
	}

	public static Place of(String kakaoId, String name, String additionalInfoLink, String telephone) {
		return Place.builder()
			.kakaoId(kakaoId)
			.name(name)
			.additionalInfoLink(additionalInfoLink)
			.telephone(telephone)
			.build();
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
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

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void update(Place place) {
		this.isUpdated = Boolean.TRUE;
		this.kakaoId = place.getKakaoId();
		this.name = place.getName();
		this.category = place.getCategory();
		this.address = place.getAddress();
		this.location = place.getLocation();
		this.imageLink = place.getImageLink();
		this.additionalInfoLink = place.getAdditionalInfoLink();
		this.telephone = place.getTelephone();
		this.blogs = place.getBlogs();
		this.bookmarkCount = place.getBookmarkCount();
		this.tripCount = place.getTripCount();
		this.viewCount = place.getViewCount();
		this.totalScore = place.getTotalScore();
	}

	public List<Blog> readBlogs() {
		return blogs;
	}

	public void updateBookmark(Bookmark bookmark) {
		removeIfExistBookmark(bookmark);
		this.getBookmarks().add(bookmark);
	}

	private void removeIfExistBookmark(Bookmark bookmark) {
		boolean isPresent = this.getBookmarks().stream().filter(
			b -> b.compareToBookmark(bookmark)).findFirst().isPresent();
		if (isPresent) {
			this.getBookmarks().remove(bookmark);
		}
	}
}
