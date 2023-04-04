package ddd.caffeine.ratrip.module.blog.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends AuditingTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(columnDefinition = "VARCHAR(255)")
	private String title;

	@Column(columnDefinition = "VARCHAR(255)")
	private String link;
}
