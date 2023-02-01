package ddd.caffeine.ratrip.module.place.domain.sub_domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Blog {
	@Column(name = "blog_title", columnDefinition = "VARCHAR(255)")
	private String title;
	@Column(name = "blog_link", columnDefinition = "VARCHAR(255)")
	private String link;

	public Blog(String title, String link) {
		this.title = title;
		this.link = link;
	}
}
