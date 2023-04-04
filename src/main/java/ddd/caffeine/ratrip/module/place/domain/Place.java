package ddd.caffeine.ratrip.module.place.domain;

import java.util.UUID;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Place extends AuditingTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
}